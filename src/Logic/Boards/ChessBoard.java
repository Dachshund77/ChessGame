package Logic.Boards;

import Logic.Coordinate;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;

/**
 * ChessBoard Object that the game will played on.
 * The ChessBoard is the binding link between the the {@link Square} and the {@link Logic.Games.Game}
 * ad contains a number of helpful methods.
 * @see Square
 * @see Logic.Games.Game
 */
public class ChessBoard {
    private Canvas displayCanvas;
    private Square[][] squares;

    /**
     * Creates a new Chessboard.
     * The references for the Canvas i needed so we know the height/width of the display Canvas.
     * The job of drawing is the controller job.
     * @param displayCanvas The canvas we want to display the board on.
     */
    public ChessBoard(Canvas displayCanvas) {
        this.displayCanvas = displayCanvas;
        squares = initializeSquares();
    }

    /**
     * Method that will change the width and height of all Squares.
     * This should and will be used when the canvas i resized.
     */
    public void resizeSquares() {
        double height = displayCanvas.getHeight() / 8;
        double width = displayCanvas.getWidth() / 8;
        for (int i = 0; i < squares.length; i++) {
            for (int j = 0; j < squares[i].length; j++) {
                squares[i][j].setWidth(width);
                squares[i][j].setHeight(height);
            }
        }
    }

    /**
     * Constructs the squares for the Board.
     * The top Left corner of the board will have coordinate (0,0) while the bottom right will have (7,7)
     * @return 2D Array of Squares
     */
    private Square[][] initializeSquares() {
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

    /**
     * Return the Array of Squares
     * @return 2D Array of Squares
     */
    public Square[][] getSquares() {
        return squares;
    }

    /**
     * We will get a specific Square by pixel clicked on the Canvas.
     * This method should be used to identify the correct Square.
     * @param xPosition The x position of the pixel clicked on the canvas.
     * @param yPosition The y position of the pixel clicked on the canvas.
     * @return
     */
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

    /**
     * Gets a specific Square by providing a {@link Coordinate} object.
     * The Coordinate object contains a x and y value and note that the top left corner will be coordinate (0,0)
     * @param coordinate Coordinate on the board
     * @return The Square on the given x/y Coordinate
     */
    public Square getSquare(Coordinate coordinate){
        int x = coordinate.getCoordinateX();
        int y = coordinate.getCoordinateY();
        return squares[x][y];
    }
}
