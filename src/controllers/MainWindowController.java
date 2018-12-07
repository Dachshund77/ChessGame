package controllers;


import Logic.Boards.ChessBoard;
import Logic.Boards.Square;
import Logic.Coordinate;
import Logic.Games.Game;
import Logic.Games.HotseatGame;
import Logic.Pieces.GamePiece;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class MainWindowController {

    @FXML
    private Canvas boardLayer;
    @FXML
    private Canvas helpLayer;
    @FXML
    private Canvas piecesLayer;
    @FXML
    private StackPane stackPaneGameArea;
    @FXML
    private Label infoLabel;

    private Lock continueLock = new ReentrantLock();
    private Condition continueCondition = continueLock.newCondition();
    private Game game;


    public void initialize() { //TODO Research who is invoking this
        stackPaneGameArea.widthProperty().addListener(event -> updateGUI());
        stackPaneGameArea.heightProperty().addListener(event -> updateGUI());
    }

    public void updateGUI() {
        if (game != null) {
            game.getChessBoard().resizeSquares();
            drawHelp();
            drawBoard();
            drawPieces();

        }
    }

    private void drawBoard() {
        if (game != null) {

            Square[][] squares = game.getChessBoard().getSquares();
            GraphicsContext gc = boardLayer.getGraphicsContext2D();
            gc.clearRect(0, 0, boardLayer.getWidth(), piecesLayer.getHeight());
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
        if (game != null) {
            Square[][] squares = game.getChessBoard().getSquares();
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

    private void drawHelp() { //Todo eventual refactor
        if (game != null) {
            GraphicsContext gc = helpLayer.getGraphicsContext2D();
            gc.clearRect(0, 0, helpLayer.getWidth(), helpLayer.getHeight());
            if (game.getCurrentSelection() != null) {
                GamePiece currentGamePiece = game.getCurrentSelection().getGamePiece();
                Coordinate currentCoordinate = game.getCurrentSelection().getCoordinate();

                ArrayList<Coordinate> validMoves = currentGamePiece.getValidMoves(game.getChessBoard(), currentCoordinate);
                double width = game.getCurrentSelection().getWidth();
                double height = game.getCurrentSelection().getHeight();
                double xPadding = width * 0.05;
                double yPadding = height * 0.05;
                double lineWidth = Math.sqrt(height * width)*0.05;
                Square[][] squares = game.getChessBoard().getSquares();

                //Drawing red square round possible moves
                for (Square[] square : squares) {
                    for (Square s : square) {
                        Coordinate tempCoordinate = s.getCoordinate();
                        for (Coordinate validMove : validMoves) {
                            if (tempCoordinate.equals(validMove)) {
                                double x = s.getPositionX();
                                double y = s.getPositionY();
                                gc.setStroke(Color.RED);
                                gc.setLineWidth(lineWidth);
                                gc.strokeRect(x+xPadding, y+yPadding, width-xPadding*2, height-yPadding*2);

                            }
                        }
                    }
                }
                //Drawing blue square around own position
                double x = game.getCurrentSelection().getPositionX();
                double y = game.getCurrentSelection().getPositionY();
                gc.setStroke(Color.BLUE);
                gc.setLineWidth(lineWidth);
                gc.strokeRect(x+xPadding, y+yPadding, width-xPadding*2, height-yPadding*2);
            }
        }
    }


    @FXML
    public void testRun(ActionEvent event) {
        if (game != null) {
            game.setTerminated(true);
        }
        HotseatGame hotSeatGame = new HotseatGame(this);
        this.game = hotSeatGame;
        Thread th = new Thread(hotSeatGame);
        th.start();
        updateGUI();
    }

    @FXML
    public void handleMouseClick(MouseEvent mouseEvent) {
        if (game == null) {
            return;
        }
        double x = mouseEvent.getX();
        double y = mouseEvent.getY();
        Square clickedSquare = game.getChessBoard().getSquare(x, y);

        clickedSquare.logInfo();

        boolean workDone = false;
        while (!workDone) {
            if (continueLock.tryLock()) {
                try {
                    game.processUserInput(clickedSquare);
                    continueCondition.signalAll();
                } finally {
                    continueLock.unlock();
                    workDone = true;
                }
            }
        }

    }


    public Canvas getBoardLayer() {
        return boardLayer;
    }

    public Lock getContinueLock() {
        return continueLock;
    }

    public Condition getContinueCondition() {
        return continueCondition;
    }

    public void setInfoLabel(String text) {
        infoLabel.setText(text);
    }
}


