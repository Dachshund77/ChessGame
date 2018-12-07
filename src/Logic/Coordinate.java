package Logic;

import Logic.Boards.ChessBoard;

public class Coordinate {


    private int xCoordinate;
    private int yCoordinate;

    public Coordinate(int xCoordinate, int yCoordinate){
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
    }

    public int getCoordinateX() {
        return xCoordinate;
    }

    public void setCoordinateX(int xCoordinate) {
        this.xCoordinate = xCoordinate;
    }

    public int getCoordinateY() {
        return yCoordinate;
    }

    public void setCoordinateY(int yCoordinate) {
        this.yCoordinate = yCoordinate;
    }

    public boolean equals(Coordinate c){
        boolean returnBoolean = false;
        if (this.xCoordinate == c.getCoordinateX() && this.yCoordinate == c.getCoordinateY()){
            returnBoolean = true;
        }
        return returnBoolean;
    }

    public boolean isValidCoordinate(ChessBoard board){ //TODO need to be more accurate
        boolean returnBoolean = true;
        int boardLength = board.getSquares().length-1; //That should be 7, length it 8
        if (xCoordinate < 0 || xCoordinate > boardLength){
            returnBoolean = false;
        }
        if (yCoordinate < 0 || yCoordinate > boardLength){
            returnBoolean = false;
        }
        return returnBoolean;
    }
}
