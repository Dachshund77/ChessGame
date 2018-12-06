package Logic.Board;

import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;

public class Board {
    private Square[][] squares;

    public Board(Canvas boardLayer) {
        squares = initializeSquares(boardLayer);
    }

    private Square[][] initializeSquares(Canvas boardLayer) { //TODO I wonder where the draw code should be placed, controller or Board?
        Square[][] returnArray = new Square[8][8]; //We define [x][y] axis
        int height = (int)boardLayer.getHeight()/8;
        int width = (int)boardLayer.getWidth()/8;
        int counter;
        for (int i = 0; i < returnArray.length; i++) {
            counter = i%2;
            for (int j = 0; j < returnArray[i].length; j++) {
                switch (counter) {
                    case 0:
                        returnArray[i][j] = new Square(Color.WHITESMOKE,width,height,width*i,height*j,i,j);
                        counter++;
                        break;
                    case 1:
                        returnArray[i][j] = new Square(Color.BLACK,width,height,width*i,height*j,i,j);
                        counter--;
                        break;
                }
            }
        }
        return returnArray;
    }

    public Square[][] getSquares(){
        return squares;
    }
}
