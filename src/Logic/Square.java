package Logic;

import javafx.scene.paint.Color;

public class Square {

    private Color color;
    private int width;
    private int height;
    private int xPosition;
    private int yPosition;
    private int xCoordinate;
    private int yCoordinate;
    //private GamePiece gamePiece;

    public Square(Color color, int width, int height, int xPosition, int yPosition,int xCoordinate, int yCoordinate) {
        this.color = color;
        this.width = width;
        this.height = height;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
    }

    public Color getColor() {
        return color;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getxPosition() {
        return xPosition;
    }

    public int getyPosition() {
        return yPosition;
    }
}
