package Logic;

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
}
