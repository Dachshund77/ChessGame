package Logic.Boards;

import Logic.Coordinate;
import Logic.Pieces.GamePieces;
import javafx.scene.paint.Color;

/**
 * The Square class a ChessBoard object is constructed from.
 * <p>In the Square object we will keep track of what kind of game piece, if any, is placed on the square.
 * The position of Square will be stored in a Coordinate object.
 * @see ChessBoard
 * @see Coordinate
 * @see GamePieces
 */
public class Square {

    private Color color;
    private double width;
    private double height;
    private Coordinate coordinate;
    private GamePieces gamePiece;

    /**
     * Creates a Square object for the Chessboard.
     * Note that the (0,0) coordinate will be the the top left corner.
     * @param color The color the Square will be displayed with.
     * @param width The width in pixels.
     * @param height The height in pixels
     * @param xCoordinate the x coordinate the square will be on the Chessboard
     * @param yCoordinate the y coordinate the square will on the Chessboard
     */
    public Square(Color color, double width, double height,int xCoordinate, int yCoordinate) {
        this.color = color;
        this.width = width;
        this.height = height;
        this.coordinate = new Coordinate(xCoordinate,yCoordinate);
        this.gamePiece = null;
    }

    /**
     * Gets the Color of the Square object.
     * @return the color.
     */
    public Color getColor() {
        return color;
    }

    /**
     * Gets the Width of the Square object.
     * @return the width in pixels.
     */
    public double getWidth() {
        return width;
    }

    /**
     * Gets the height of the Square object.
     * @return the height in pixels.
     */
    public double getHeight() {
        return height;
    }

    /**
     * Sets the width of the Square object.
     * @param width how many pixel the Square should be wide
     */
    public void setWidth(double width) {
        this.width = width;
    }

    /**
     * Sets the height of the Square object.
     * @param height how many pixel the Square should be high.
     */
    public void setHeight(double height) {
        this.height = height;
    }

    /**
     * Gets the current gamePiece on this Square.
     * @return the GamePiece or null if none present.
     */
    public GamePieces getGamePiece() {
        return gamePiece;
    }

    /**
     * Sets the given GamePiece "on" this Square
     * @param gamePiece the GamePiece we want to place on this Square
     */
    public void setGamePiece(GamePieces gamePiece) {
        this.gamePiece = gamePiece;
    }

    /**
     * Sets the current GamePiece to null
     */
    public void removeGamePiece(){
        this.gamePiece = null;
    }

    /**
     * Gets the Coordinate object of the Square.
     * @return a Coordinate object.
     */
    public Coordinate getCoordinate() {
        return coordinate;
    }
}
