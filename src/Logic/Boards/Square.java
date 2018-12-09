package Logic.Boards;

import Logic.Coordinate;
import Logic.Pieces.GamePieces;
import javafx.scene.paint.Color;

public class Square {

    private Color color;
    private double width;
    private double height;
    private double PositionX;
    private double PositionY;
    private Coordinate coordinate;
    private GamePieces gamePiece;

    public Square(Color color, double width, double height, double xPosition, double yPosition,int xCoordinate, int yCoordinate) {
        this.color = color;
        this.width = width;
        this.height = height;
        this.PositionX = xPosition;
        this.PositionY = yPosition;
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

    public double getPositionX() {
        return PositionX;
    }

    public double getPositionY() {
        return PositionY;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public void setPositionX(double PositionX) {
        this.PositionX = PositionX;
    }

    public void setPositionY(double PositionY) {
        this.PositionY = PositionY;
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

    public void logInfo(){
        System.out.println("coordinate = x:" + coordinate.getCoordinateX()+" y:"+coordinate.getCoordinateY());
        if (gamePiece != null) {
            System.out.println("gamePiece = " + gamePiece.getFaction() + " " + getGamePiece().getUnitType());
        }
    }
}
