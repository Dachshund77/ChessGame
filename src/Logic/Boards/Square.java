package Logic.Boards;

import Logic.Coordinate;
import Logic.Pieces.GamePieces;
import javafx.scene.paint.Color;

/**
 *
 */
public class Square {

    private Color color;
    private double width;
    private double height;
    private Coordinate coordinate;
    private GamePieces gamePiece;

    public Square(Color color, double width, double height,int xCoordinate, int yCoordinate) {
        this.color = color;
        this.width = width;
        this.height = height;
        this.coordinate = new Coordinate(xCoordinate,yCoordinate);
        this.gamePiece = null;
    }

    public Color getColor() {
        return color;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }


    public void setWidth(double width) {
        this.width = width;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public GamePieces getGamePiece() {
        return gamePiece;
    }

    public void setGamePiece(GamePieces gamePiece) {
        this.gamePiece = gamePiece;
    }

    public void removeGamePiece(){
        this.gamePiece = null;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }
}
