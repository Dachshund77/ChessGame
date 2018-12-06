package controllers;


import Logic.Board.Board;
import Logic.Board.Square;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;


public class MainWindowController {

    @FXML
    private Canvas boardLayer;
    @FXML
    private StackPane stackPaneGameArea;

    private Board board;

    public void initialize(){ //TODO who is invoking this
        stackPaneGameArea.widthProperty().addListener(event -> drawBoard());
        stackPaneGameArea.heightProperty().addListener(event -> drawBoard());
    }

    public void drawBoard() {
        System.out.println("Drawing board");
        if (board == null){ //TODO make this smarter?
            return;
        }
        board.resizeSquares();
        Square[][] squares = board.getSquares();
        GraphicsContext gc = boardLayer.getGraphicsContext2D();

        for (Square[] square : squares) {
            for (Square s : square) {
                gc.setFill(s.getColor());
                double xPosition = s.getPositionX();
                double yPosition = s.getPositionY();
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
        System.out.println("Detected mouseclick");
        double x = mouseEvent.getX();
        double y = mouseEvent.getY();
        Square[][] squares = board.getSquares();
        for (Square[] square : squares) {
            for (Square s : square) {
                if (s.getPositionX() <= x && s.getPositionX()+s.getWidth() >= x &&
                        s.getPositionY() <= y && s.getPositionY()+s.getHeight() >= y) {
                    System.out.println(s.getCoordinate().getCoordinateX());
                    System.out.println(s.getCoordinate().getCoordinateY());
                }
            }
        }
    }
}


