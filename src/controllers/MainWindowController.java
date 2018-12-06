package controllers;


import Logic.Boards.ChessBoard;
import Logic.Boards.Square;
import Logic.Pieces.Faction;
import Logic.Pieces.GamePiece;
import Logic.Pieces.King;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;


public class MainWindowController {

    @FXML
    private Canvas boardLayer;
    @FXML
    private Canvas helpLayer;
    @FXML
    private Canvas piecesLayer;
    @FXML
    private StackPane stackPaneGameArea;

    private ChessBoard board; //TODO change to Game

    public void initialize() { //TODO Research who is invoking this
        stackPaneGameArea.widthProperty().addListener(event -> updateGUI());
        stackPaneGameArea.heightProperty().addListener(event -> updateGUI());
    }

    public void updateGUI() {
        if (board != null) {
            board.resizeSquares();
            drawBoard();
            drawPieces();
        }
    }

    private void drawBoard() {
        if (board != null) {

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
    }

    private void drawPieces() {
        if (board != null) {
            Square[][] squares = board.getSquares();
            GraphicsContext gc = piecesLayer.getGraphicsContext2D();
            gc.clearRect(0, 0, piecesLayer.getWidth(), piecesLayer.getHeight());
            for (Square[] square : squares) {
                for (Square s : square) {
                    GamePiece gamePiece = s.getGamePiece();
                    if (gamePiece != null) {
                        double xPosition = s.getPositionX();
                        double yPosition = s.getPositionY();
                        double width = s.getWidth();
                        double height = s.getHeight();

                        Image image = new Image(String.valueOf(gamePiece.getImageURL()));
                        gc.drawImage(image, xPosition, yPosition, width, height);
                    }
                }
            }
        }
    }

    @FXML
    public void testRun(ActionEvent event) {
        board = new ChessBoard(boardLayer);
        drawBoard();
        Square[][] squares = board.getSquares();
        GamePiece testPiece = new King(Faction.WHITE);
        squares[1][1].setGamePiece(testPiece);
        drawPieces();
    }

    @FXML
    public void handleMouseClick(MouseEvent mouseEvent) {
        if (board == null) {
            return;
        }
        System.out.println("Mouseclick detected");
        double x = mouseEvent.getX();
        double y = mouseEvent.getY();
        Square[][] squares = board.getSquares();
        for (Square[] square : squares) {
            for (Square s : square) {
                if (s.getPositionX() <= x && s.getPositionX() + s.getWidth() >= x &&
                        s.getPositionY() <= y && s.getPositionY() + s.getHeight() >= y) {
                    System.out.println(s.getCoordinate().getCoordinateX());
                    System.out.println(s.getCoordinate().getCoordinateY());
                }
            }
        }
    }
}


