package Logic.Boards;

import Logic.Coordinate;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;

public class ChessBoard {
    private Canvas displayCanvas;
    private Square[][] squares;

    public ChessBoard(Canvas displayCanvas) {
        this.displayCanvas = displayCanvas;
        squares = initializeSquares();
    }

    public void resizeSquares() { //TODO can be slightly rewritten
        double height = displayCanvas.getHeight() / 8;
        double width = displayCanvas.getWidth() / 8;
        for (int i = 0; i < squares.length; i++) {
            for (int j = 0; j < squares[i].length; j++) {
                squares[i][j].setWidth(width);
                squares[i][j].setHeight(height);
            }
        }
    }

    private Square[][] initializeSquares() { //TODO refactor
        Square[][] returnArray = new Square[8][8]; //We define [x][y] axis
        double height = displayCanvas.getHeight() / 8;
        double width = displayCanvas.getWidth() / 8;
        int counter;
        for (int i = 0; i < returnArray.length; i++) {
            counter = i % 2;
            for (int j = 0; j < returnArray[i].length; j++) {
                switch (counter) {
                    case 0:
                        //White square
                        returnArray[i][j] = new Square(Color.rgb(221, 220, 201), width, height, i, j);
                        counter++;
                        break;
                    case 1:
                        //Black square
                        returnArray[i][j] = new Square(Color.rgb(102, 93, 0), width, height, i, j);
                        counter--;
                        break;
                }
            }
        }
        return returnArray;
    }

    public Square[][] getSquares() {
        return squares;
    }

    public Square getSquare(double xPosition, double yPosition) {
        Square returnSquare = null;

        for (Square[] square : squares) {
            for (Square s : square) {
                double width = s.getWidth();
                double height = s.getHeight();
                int xCoordinate = s.getCoordinate().getCoordinateX();
                int yCoordinate = s.getCoordinate().getCoordinateY();
                double xAnchor = xCoordinate * width; //The upper left corner of the square
                double yAnchor = yCoordinate * height;
                //Now we check we if the x/y clicked is in the bounds of the square
                if (xPosition >= xAnchor && xPosition <= xAnchor + width &&
                    yPosition >= yAnchor && yPosition <= yAnchor + height){
                    returnSquare = s;
                }
            }
        }
        return returnSquare;
    }

    public Square getSquare(Coordinate coordinate){
        int x = coordinate.getCoordinateX();
        int y = coordinate.getCoordinateY();
        return squares[x][y];
    }


}
