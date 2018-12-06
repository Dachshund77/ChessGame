package Logic.Board;

import Logic.Coordinate;
import javafx.scene.paint.Color;

public class Square {

    private Color color;
    private double width;
    private double height;
    private double PositionX; //TODO might be more sensible to use doubles
    private double PositionY;
    private Coordinate coordinate;
    //private GamePiece gamePiece;

    public Square(Color color, double width, double height, double xPosition, double yPosition,int xCoordinate, int yCoordinate) {
        this.color = color;
        this.width = width;
        this.height = height;
        this.PositionX = xPosition;
        this.PositionY = yPosition;
        this.coordinate = new Coordinate(xCoordinate,yCoordinate);
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

    public double getPositionX() {
        return PositionX;
    }

    public double getPositionY() {
        return PositionY;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setPositionX(int PositionX) {
        this.PositionX = PositionX;
    }

    public void setPositionY(int PositionY) {
        this.PositionY = PositionY;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }
}
