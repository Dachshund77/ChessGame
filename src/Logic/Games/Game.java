package Logic.Games;

import Logic.Boards.ChessBoard;
import Logic.Boards.Square;
import Logic.Coordinate;
import Logic.Pieces.*;
import controllers.MainWindowController;

import java.util.ArrayList;

public abstract class Game implements Runnable {

    private volatile boolean terminated = false;

    private MainWindowController controller;
    private ChessBoard chessBoard;
    private Faction turnOrder = Faction.WHITE;
    private boolean hasGameEnded = false;
    private Faction winner = null;
    private Square currentSelection = null;

    public Game(MainWindowController controller){
        this.controller = controller;
        this.chessBoard = new ChessBoard(controller.getBoardLayer());
    }

    void setUpBoard(){
        if (!terminated){
            //Black pieces
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
            //White pieces
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

    void checkForGameEnd(){
        if (!terminated){
            Square[][] squares = chessBoard.getSquares();
            boolean blackKingAlive = false;
            boolean whiteKingAlive = false;
            for (Square[] square : squares) {
                for (Square s : square) {
                    GamePiece gamePiece = s.getGamePiece();
                    if (gamePiece!= null){
                        if (gamePiece.getUnitType() == UnitType.KING){
                            if (gamePiece.getFaction() == Faction.WHITE){
                                whiteKingAlive = true;
                            }
                            else if (gamePiece.getFaction() == Faction.BLACK){
                                blackKingAlive = true;
                            }
                        }
                    }
                }
            }
            if (!blackKingAlive){
                hasGameEnded = true;
                winner = Faction.WHITE;
            }
            else if (!whiteKingAlive){
                hasGameEnded = true;
                winner = Faction.BLACK;
            }
        }
    }

     void updateBoardState(Coordinate origin, Coordinate destination){
        if(!terminated){
            Square[][] squares = chessBoard.getSquares();
            GamePiece pieceMoved = squares[origin.getCoordinateX()][origin.getCoordinateY()].getGamePiece();
            squares[origin.getCoordinateY()][origin.getCoordinateY()].removeGamePiece();
            squares[destination.getCoordinateX()][destination.getCoordinateY()].setGamePiece(pieceMoved);
        }
    }

    public abstract void processUserInput(Square newSelection);

    boolean isValidMove(Square newSelection){
        boolean returnBoolean = false;
        ArrayList<Coordinate> listOfValidMoves = currentSelection.getGamePiece().getValidMoves(chessBoard,currentSelection.getCoordinate());
        for (Coordinate coordinate : listOfValidMoves) {
            if (coordinate.equals(newSelection.getCoordinate())){
                returnBoolean = true;
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
