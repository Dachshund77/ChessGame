package Logic.Games;

import Logic.Boards.ChessBoard;
import Logic.Boards.Square;
import Logic.Coordinate;
import Logic.Pieces.*;
import Logic.Pieces.Piece.*;
import Controllers.MainWindowController;
import javafx.application.Platform;

import java.util.ArrayList;

/**
 * Abstract class that contains standard methods that will most likely used for all Game children.
 * It is here where we move piece, check for game end or if a move is valid.
 */
public abstract class Game implements Runnable, Games {

    private volatile boolean terminated = false;

    private MainWindowController controller;
    private ChessBoard chessBoard;
    private Faction turnOrder = Faction.WHITE;
    private boolean hasGameEnded = false;
    private Faction winner = null;
    private Square currentSelection = null;

    public Game(MainWindowController controller) {
        this.controller = controller;
        this.chessBoard = new ChessBoard(controller.getBoardLayer());
    }

    /**
     * Will create the starting setup for all gamePieces.
     */
    void setUpBoard() {
        if (!terminated) {
            //Black Pieces
            Square[][] squares = chessBoard.getSquares();
            squares[0][0].setGamePiece(new Rock(Faction.BLACK));
            squares[1][0].setGamePiece(new Knight(Faction.BLACK));
            squares[2][0].setGamePiece(new Bishop(Faction.BLACK));
            squares[3][0].setGamePiece(new Queen(Faction.BLACK));
            squares[4][0].setGamePiece(new King(Faction.BLACK));
            squares[5][0].setGamePiece(new Bishop(Faction.BLACK));
            squares[6][0].setGamePiece(new Knight(Faction.BLACK));
            squares[7][0].setGamePiece(new Rock(Faction.BLACK));
            for (int i = 0; i < squares[1].length; i++) {
                squares[i][1].setGamePiece(new Pawn(Faction.BLACK));
            }
            //White Pieces
            squares[0][7].setGamePiece(new Rock(Faction.WHITE));
            squares[1][7].setGamePiece(new Knight(Faction.WHITE));
            squares[2][7].setGamePiece(new Bishop(Faction.WHITE));
            squares[3][7].setGamePiece(new Queen(Faction.WHITE));
            squares[4][7].setGamePiece(new King(Faction.WHITE));
            squares[5][7].setGamePiece(new Bishop(Faction.WHITE));
            squares[6][7].setGamePiece(new Knight(Faction.WHITE));
            squares[7][7].setGamePiece(new Rock(Faction.WHITE));
            for (int i = 0; i < squares[6].length; i++) {
                squares[i][6].setGamePiece(new Pawn(Faction.WHITE));
            }
        }
    }

    /**
     * Inspects the board to find out if the game has ended.
     * The games has ended if a black or white king can not be found.
     * In case of a game ending this method will set the winner field and
     * also prompt the user about the game end.
     * Note that this method does not detect a check mate situation
     */
    void checkForGameEnd() {
        if (!terminated) {
            Square[][] squares = chessBoard.getSquares();
            boolean blackKingAlive = false;
            boolean whiteKingAlive = false;
            for (Square[] square : squares) {
                for (Square s : square) {
                    GamePieces gamePiece = s.getGamePiece();
                    if (gamePiece != null) {
                        if (gamePiece.getUnitType() == UnitType.KING) {
                            if (gamePiece.getFaction() == Faction.WHITE) {
                                whiteKingAlive = true;
                            } else if (gamePiece.getFaction() == Faction.BLACK) {
                                blackKingAlive = true;
                            }
                        }
                    }
                }
            }
            if (!blackKingAlive) {
                hasGameEnded = true;
                winner = Faction.WHITE;
                Platform.runLater(() -> controller.setInfoLabel("Winner is " + winner.getNormalName()));
            } else if (!whiteKingAlive) {
                hasGameEnded = true;
                winner = Faction.BLACK;
                Platform.runLater(() -> controller.setInfoLabel("Winner is " + winner.getNormalName()));
            }
        }
    }

    /**
     * Method that manages the logistics of removing a adding gamePiece according to the gameRules.
     * Note that this method does not check if the destination is a valid destination for the gamePiece.
     * This will be handled by {@link Games#processUserInput(Square)}
     * @param origin Coordinate where the current selection is
     * @param destination Coordinate where the user clicked
     */
    void processTurn(Coordinate origin, Coordinate destination) {
        if (!terminated) {
            // Getting needed values
            GamePieces pieceMoved = currentSelection.getGamePiece();
            UnitType typeMoved = pieceMoved.getUnitType();
            Faction factionMoved = pieceMoved.getFaction();
            // Castle move
            if (typeMoved == UnitType.KING && Math.abs(origin.getCoordinateX() - destination.getCoordinateX()) == 2) {
                castleMove(origin, destination);
            } // Promotion
            else if (typeMoved == UnitType.PAWN && (destination.getCoordinateY() == 0 || destination.getCoordinateY() == 7)) {
                promote(origin, destination, factionMoved);
            } // handle enPasante
            else if (isEnPassanteMove(pieceMoved, destination)) {
                doEnPassante(origin, destination, factionMoved);
            } else {
                movePiece(origin, destination);
            }
            // Manage enPassante flags
            resetEnPassanteFlag();
            // Is pawn and moved to spaces
            if (typeMoved == UnitType.PAWN && Math.abs(origin.getCoordinateY() - destination.getCoordinateY()) == 2) {
                raiseEnPassantFlag(destination);
            }
        }
    }

    /**
     * Tests if the move was a EnPassante move.
     *
     * @param pieceMoved  The gamePiece that was moved
     * @param destination The Coordinate the GamePiece moved to
     * @return True if the Move was a enPassante move
     */
    private boolean isEnPassanteMove(GamePieces pieceMoved, Coordinate destination) {

        boolean returnBoolean = false;
        if (!terminated) {
            if (pieceMoved.getUnitType() == UnitType.PAWN) {
                Pawn pawn = (Pawn) pieceMoved;
                if (pawn.getEnPassanteCordinate() != null && destination.getCoordinateX() == pawn.getEnPassanteCordinate().getCoordinateX()) {
                    returnBoolean = true;
                }
            }
        }
        return returnBoolean;
    }

    /**
     * Does the movement and removal in a enPassante Maneuver.
     * Note that this method assumes that the EnPassante is a valid move
     * and that the BoardState is viable.
     *
     * @param origin       Coordinate where the pawn moved from
     * @param destination  Coordinate where the pawn moved to
     * @param factionMoved Color of the pawn moved
     */
    private void doEnPassante(Coordinate origin, Coordinate destination, Faction factionMoved) {
        if (!terminated) {
            Square[][] squares = chessBoard.getSquares();
            if (factionMoved == Faction.WHITE) {
                squares[destination.getCoordinateX()][destination.getCoordinateY() + 1].removeGamePiece();
            } else {
                squares[destination.getCoordinateX()][destination.getCoordinateY() - 1].removeGamePiece();
            }
            movePiece(origin, destination);
        }
    }

    /**
     * Promotes a pawn to a chosen GamePiece.
     * Note that this method assumes that the pawn is eligible for a promotion.
     *
     * @param destination      Coordinate where the Pawn will be promoted
     * @param promotionFaction The Faction the GamePiece will belong to
     */
    private void promote(Coordinate origin, Coordinate destination, Faction promotionFaction) {
        if (!terminated) {
            // Removing the pawn
            Square originSquare = chessBoard.getSquare(origin);
            originSquare.removeGamePiece();
            // Promoting
            UnitType promotionType = controller.promotePawnDialog();
            Square[][] squares = chessBoard.getSquares();
            GamePieces promotionPiece = null;
            switch (promotionType) {
                case QUEEN:
                    promotionPiece = new Queen(promotionFaction);
                    break;
                case ROCK:
                    promotionPiece = new Rock(promotionFaction);
                    break;
                case BISHOP:
                    promotionPiece = new Bishop(promotionFaction);
                    break;
                case KNIGHT:
                    promotionPiece = new Knight(promotionFaction);
                    break;
            }
            squares[destination.getCoordinateX()][destination.getCoordinateY()].setGamePiece(promotionPiece);
            raiseMovedFlag(promotionPiece);
        }
    }

    /**
     * Moves a GamePiece from origins to destination.
     * Note that this method will possible overwrite a GamePiece at the destination.
     *
     * @param origin      Coordinate of the GamePiece to be moved
     * @param destination Coordinate where the GamePiece should be moved to
     */
    private void movePiece(Coordinate origin, Coordinate destination) {
        if (!terminated) {
            Square[][] squares = chessBoard.getSquares();
            //Take an remove
            GamePieces pieceMoved = squares[origin.getCoordinateX()][origin.getCoordinateY()].getGamePiece();
            squares[origin.getCoordinateX()][origin.getCoordinateY()].removeGamePiece();
            //Place on new position
            squares[destination.getCoordinateX()][destination.getCoordinateY()].setGamePiece(pieceMoved);
            //Raise moved flag
            raiseMovedFlag(pieceMoved);
        }
    }

    /**
     * Moves the King and Rock in a castle turn.
     * Note that this method assumes that the castle is a viable move
     * and that the board state is in a viable state for a castle maneuver.
     *
     * @param origin      Coordinate of the King
     * @param destination Coordinate after the Kind has made Castle
     */
    private void castleMove(Coordinate origin, Coordinate destination) {
        if (!terminated) {
            // moving the King
            movePiece(origin, destination);
            raiseMovedFlag(chessBoard.getSquare(destination).getGamePiece());
            //Moving Rock To the left
            if (destination.getCoordinateX() == 2) {
                Coordinate rockOrigin = new Coordinate(0, origin.getCoordinateY()); // Rock does not change y axis
                Coordinate rockDestination = new Coordinate(3, origin.getCoordinateY());
                movePiece(rockOrigin, rockDestination);
            }
            // Moving Rock To the right
            if (destination.getCoordinateX() == 6) {
                Coordinate rockOrigin = new Coordinate(7, origin.getCoordinateY()); // Rock does not change y axis
                Coordinate rockDestination = new Coordinate(5, origin.getCoordinateY());
                movePiece(rockOrigin, rockDestination);
            }
        }
    }

    /**
     * Sets the moved flag if possible.
     * Only some instances of GamePiece have a hasMoved field, so we need to check.
     *
     * @param pieceMoved The GamePiece moved
     */
    private void raiseMovedFlag(GamePieces pieceMoved) {
        if (!terminated) {
            switch (pieceMoved.getUnitType()) {
                case KING:
                    King king = (King) pieceMoved;
                    king.setHasMoved(true);
                    break;
                case ROCK:
                    Rock rock = (Rock) pieceMoved;
                    rock.setHasMoved(true);
                    break;
                case PAWN:
                    Pawn pawn = (Pawn) pieceMoved;
                    pawn.setHasMoved(true);
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * Sets the enPassante flag to null for all GamePiece on the Board.
     */
    private void resetEnPassanteFlag() {
        if (!terminated) {
            Square[][] squares = chessBoard.getSquares();
            for (Square[] square : squares) {
                for (Square s : square) {
                    if (s.getGamePiece() != null && s.getGamePiece().getUnitType() == UnitType.PAWN) {
                        Pawn pawn = (Pawn) s.getGamePiece();
                        pawn.setEnPassanteCordinate(null);
                    }
                }
            }
        }
    }

    /**
     * Sets the EnPassante Flag for adjacent Pawns after a Pawn moved.
     * Note that the method does not check if a Pawn moved or not, it only raised the flag at the destination.
     *
     * @param destination Coordinate where the Pawn moved to
     */
    private void raiseEnPassantFlag(Coordinate destination) {
        if (!terminated) {
            Coordinate left = new Coordinate(destination.getCoordinateX() - 1, destination.getCoordinateY());
            Coordinate right = new Coordinate(destination.getCoordinateX() + 1, destination.getCoordinateY());
            if (left.isValidCoordinate(chessBoard)) {
                GamePieces leftGamePiece = chessBoard.getSquare(left).getGamePiece();
                if (leftGamePiece != null && leftGamePiece.getUnitType() == UnitType.PAWN) {
                    Pawn pawn = (Pawn) leftGamePiece;
                    pawn.setEnPassanteCordinate(destination);
                }
            }
            if (right.isValidCoordinate(chessBoard)) {
                GamePieces rightGamePiece = chessBoard.getSquare(right).getGamePiece();
                if (rightGamePiece != null && rightGamePiece.getUnitType() == UnitType.PAWN) {
                    Pawn pawn = (Pawn) rightGamePiece;
                    pawn.setEnPassanteCordinate(destination);
                }
            }
        }
    }

    /**
     * Tests if the chosen Square is a valid move for the currently selected GamePiece.
     *
     * @param newSelection The Square the user clicked
     * @return True if the move is valid
     */
    boolean isValidMove(Square newSelection) {
        boolean returnBoolean = false;
        if (!terminated) {
            ArrayList<Coordinate> listOfValidMoves = currentSelection.getGamePiece().getValidMoves(chessBoard, currentSelection.getCoordinate());
            for (Coordinate coordinate : listOfValidMoves) {
                if (coordinate.equals(newSelection.getCoordinate())) {
                    returnBoolean = true;
                }
            }
        }
        return returnBoolean;
    }

    public ChessBoard getChessBoard() {
        return chessBoard;
    }

    public void setTerminated(boolean terminated) {
        this.terminated = terminated;
    }

    boolean hasGameEnded() {
        return hasGameEnded;
    }

    Faction getTurnOrder() {
        return turnOrder;
    }

    boolean isTerminated() {
        return terminated;
    }

    MainWindowController getController() {
        return controller;
    }

    public Square getCurrentSelection() {
        return currentSelection;
    }

    void setCurrentSelection(Square currentSelection) {
        this.currentSelection = currentSelection;
    }

    void setTurnOrder(Faction turnOrder) {
        this.turnOrder = turnOrder;
    }
}
