package controllers;


import Logic.Board.Board;
import Logic.Board.Square;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;


public class MainWindowController {

    @FXML
    private Canvas boardLayer;

    private Board board;


    public void drawBoard() {
        Square[][] squares = board.getSquares();
        GraphicsContext gc = boardLayer.getGraphicsContext2D();

        for (Square[] square : squares) {
            for (Square s : square) {
                gc.setFill(s.getColor());
                double xPosition = s.getxPosition();
                double yPosition = s.getyPosition();
                double width = s.getWidth();
                double height = s.getHeight();
                gc.fillRect(xPosition, yPosition, width, height);
            }
        }
    }

    @FXML
    public void testRun(ActionEvent event) {
        board = new Board(boardLayer);
        drawBoard();
    }

    @FXML
    public void handleMouseClick(MouseEvent mouseEvent) {
        double x = mouseEvent.getX();
        double y = mouseEvent.getY();
        Square[][] squares = board.getSquares();
        for (Square[] square : squares) {
            for (Square s : square) {
                if (s.getxPosition() <= x && s.getxPosition()+s.getWidth() >= x &&
                        s.getyPosition() <= y && s.getyPosition()+s.getHeight() >= y) {
                    System.out.println("x: "+s.getxCoordinate());
                    System.out.println("y: "+s.getyCoordinate());
                }
            }
        }
    }
}


