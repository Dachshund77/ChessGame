package controllers;


import Logic.Board;
import Logic.Square;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;


public class MainWindowController {

    @FXML
    private Canvas boardLayer;

    private Board board;

    private void drawBoard() {
        Square[][] squares = board.getSquares();
        GraphicsContext gc = boardLayer.getGraphicsContext2D();

        for (Square[] square : squares) {
            for (Square s : square) {
                gc.setFill(s.getColor());
                double xPosition = s.getxPosition();
                double yPosition = s.getyPosition();
                double width = s.getWidth();
                double height = s.getHeight();
                gc.fillRect(xPosition,yPosition,width,height);
            }
        }

    }

    @FXML
    public void testRun(ActionEvent event) {
        board = new Board(boardLayer);
        drawBoard();
    }
}

