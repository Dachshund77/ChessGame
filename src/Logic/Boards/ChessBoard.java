package Logic.Boards;

import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;

public class ChessBoard {
    private Canvas displayCanvas;
    private Square[][] squares;

    public ChessBoard(Canvas displayCanvas) { //TODO we dont actually need the canvas reference here
        this.displayCanvas = displayCanvas;
        squares = initializeSquares();
    }

    public void resizeSquares() {
        double height = displayCanvas.getHeight() / 8;
        double width =  displayCanvas.getWidth() / 8;
        for (int i = 0; i < squares.length; i++) {
            for (int j = 0; j < squares[i].length; j++) {
                squares[i][j].setWidth(width);
                squares[i][j].setHeight(height);
                squares[i][j].setPositionX(width * i);
                squares[i][j].setPositionY(height * j);
            }
        }
    }


    private Square[][] initializeSquares() { //TODO refactor
        Square[][] returnArray = new Square[8][8]; //We define [x][y] axis
        double height =  displayCanvas.getHeight() / 8;
        double width =  displayCanvas.getWidth() / 8;
        int counter;
        for (int i = 0; i < returnArray.length; i++) {
            counter = i % 2;
            for (int j = 0; j < returnArray[i].length; j++) {
                switch (counter) {
                    case 0:
                        returnArray[i][j] = new Square(Color.WHITESMOKE, width, height, width * i, height * j, i, j);
                        counter++;
                        break;
                    case 1:
                        returnArray[i][j] = new Square(Color.BLACK, width, height, width * i, height * j, i, j);
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
}
