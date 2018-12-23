package Logic.Games;

import Logic.Boards.ChessBoard;
import Logic.Boards.Square;
import Logic.Coordinate;
import Logic.Pieces.*;
import Logic.Pieces.Piece.*;
import Controllers.MainWindowController;
import javafx.application.Platform;

import java.util.ArrayList;

public abstract class Game implements Runnable, Games{ //TODO might be sensible to make own interface

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
                Platform.runLater(()-> controller.setInfoLabel("Winner is " + winner.getNormalName()));
            } else if (!whiteKingAlive) {
                hasGameEnded = true;
                winner = Faction.BLACK;
                Platform.runLater(()-> controller.setInfoLabel("Winner is " + winner.getNormalName()));
            }
        }
    }

    void movePiece(Coordinate origin, Coordinate destination) { //TODO remove Piece from en passante, also possible candidate for refactoring
        if (!terminated && !hasGameEnded) { //Blocks any incoming moves
            //Handling normal move
            Square[][] squares = chessBoard.getSquares();
            GamePieces pieceMoved = squares[origin.getCoordinateX()][origin.getCoordinateY()].getGamePiece();
            squares[origin.getCoordinateX()][origin.getCoordinateY()].removeGamePiece();
            squares[destination.getCoordinateX()][destination.getCoordinateY()].setGamePiece(pieceMoved);
            //Special cases
            UnitType typeMoved = pieceMoved.getUnitType();
            //Castle
            if (typeMoved == UnitType.KING && Math.abs(origin.getCoordinateX() - destination.getCoordinateX()) == 2){
                if (destination.getCoordinateX() == 2){
                    GamePieces movingCastle = squares[0][destination.getCoordinateY()].getGamePiece();
                    squares[0][destination.getCoordinateY()].removeGamePiece();
                    squares[3][destination.getCoordinateY()].setGamePiece(movingCastle);
                }
            }
            if (typeMoved == UnitType.KING && Math.abs(origin.getCoordinateX() - destination.getCoordinateX()) == 2){
                if (destination.getCoordinateX() == 6){
                    GamePieces movingCastle = squares[7][destination.getCoordinateY()].getGamePiece();
                    squares[7][destination.getCoordinateY()].removeGamePiece();
                    squares[5][destination.getCoordinateY()].setGamePiece(movingCastle);
                }
            }
            //Promotion
            if (typeMoved == UnitType.PAWN && (destination.getCoordinateY() == 0 || destination.getCoordinateY() == 7)){
                UnitType promotionType = controller.promotePawnDialog();
                Faction promotionFaction = pieceMoved.getFaction();
                GamePieces promotionPiece = null;
                switch (promotionType){
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
            }
            //En Passante
            if (typeMoved == UnitType.PAWN ) {
                Pawn pawn = (Pawn) pieceMoved;
                if (pawn.getEnPassanteCordinate() != null && destination.getCoordinateX() == pawn.getEnPassanteCordinate().getCoordinateX()){
                    if (pieceMoved.getFaction() == Faction.WHITE){
                        squares[destination.getCoordinateX()][destination.getCoordinateY()+1].removeGamePiece();
                    }
                    else{
                        squares[destination.getCoordinateX()][destination.getCoordinateY()-1].removeGamePiece();
                    }
                }
            }
            //Updating flags
            resetEnPssanteFlag();
            if (typeMoved == UnitType.PAWN){
                raiseEnPassantFlag(destination);
            }
            raiseMovedFlag(pieceMoved);
        }
    }

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

    private void resetEnPssanteFlag() {
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

    public boolean hasGameEnded() {
        return hasGameEnded;
    }

    public Faction getWinner() {
        return winner;
    }

    public Faction getTurnOrder() {
        return turnOrder;
    }

    public boolean isTerminated() {
        return terminated;
    }

    public MainWindowController getController() {
        return controller;
    }

    public Square getCurrentSelection() {
        return currentSelection;
    }

    public void setCurrentSelection(Square currentSelection) {
        this.currentSelection = currentSelection;
    }

    public void setTurnOrder(Faction turnOrder) {
        this.turnOrder = turnOrder;
    }
}
