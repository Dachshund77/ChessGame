package Logic;

import Logic.Boards.ChessBoard;
import Logic.Boards.Square;

/**
 * Thees object will be used to store a x and y coordinate on a given Square.
 * Note that Squares[x][y] should have a 0,0 value in the top left corner
 * @see Square
 */
public class Coordinate {


    private int xCoordinate;
    private int yCoordinate;

    /**
     * Creates a Coordinate object that keeps two ints as x/y coordinates.
     * @param xCoordinate int that stores x coordinate.
     * @param yCoordinate int that stores y coordinate.
     */
    public Coordinate(int xCoordinate, int yCoordinate){
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
    }

    /**
     * Gets the x coordinate.
     * @return int with the x coordinate.
     */
    public int getCoordinateX() {
        return xCoordinate;
    }

    /**
     * Gets the y coordinate.
     * @return int with the y coordinate.
     */
    public int getCoordinateY() {
        return yCoordinate;
    }

    /**
     * Compares this Coordinate with an object.
     * @param obj the object we want to compare with
     * @return true if the x and y fields have the same value
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null){
            return false;
        }
        else if (!Coordinate.class.isAssignableFrom(obj.getClass())){ //If the object can be a Coordinate
            return false;
        }
        Coordinate other = (Coordinate) obj;
        if (this.getCoordinateY() == other.getCoordinateY() && this.getCoordinateX() == other.getCoordinateX()){
            return true;
        }
        return false;

    }

    /**
     * Checks if the coordinates exist on the {@link Logic.Boards.ChessBoard}.
     * @param board chessboard we will check the length of
     * @return true if the coordinate is valid
     */
    public boolean isValidCoordinate(ChessBoard board){
        boolean returnBoolean = false;
        Square[][] squares = board.getSquares();
        int xLength = squares.length-1;
        if (xCoordinate >= 0 && xCoordinate <= xLength){ //If x is valid
            int yLength = squares[xCoordinate].length-1;
            if (yCoordinate >= 0 && yCoordinate <= yLength){ //If y is valid
                returnBoolean = true;
            }
        }
        return returnBoolean;
    }
}
