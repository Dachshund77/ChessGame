package Controllers;

import Logic.Boards.ChessBoard;
import Logic.Boards.Square;
import Logic.Coordinate;
import Logic.Games.Game;
import Logic.Games.HotSeatGame;
import Logic.Pieces.GamePieces;
import Logic.Pieces.UnitType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * <p> Controller for {@link Views.MainWindow}.
 * <p> In this class we will keep a references of a {@link Logic.Games.Game} object.
 * Note that the Game Object is a runnable and therefor we need a lock.
 * This class will be the owner of said Lock to enable synchronisation between both classes.
 * @see Game
 * @see Views.MainWindow
 */
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


    /**
     * <p>Method that will called at creation.
     * <p>This particular method will enlist two action listener that will be called whenever the window is resized.
     * The Action listeners will call {@link MainWindowController#updateGUI}.
     */
    //The initialize will be called after constructor and @FXML fields
    public void initialize() {
        stackPaneGameArea.widthProperty().addListener(event -> updateGUI());
        stackPaneGameArea.heightProperty().addListener(event -> updateGUI());
    }

    /**
     * Method that will update the GUI. The GUI entails the board, help for the player and the game pieces.
     * <p>This method will only be executed if a Game object is running.
     * Also since the placement of the games pieces and help is dependant on the board squares,
     * we will make sure that the squares have the correct size with {@link ChessBoard#resizeSquares()}
     */
    public void updateGUI() {
        if (game != null) {
            game.getChessBoard().resizeSquares();
            drawHelp();
            drawPieces();
            drawBoard();
        }
    }

    /**
     * Will draw the squares of a given Board Object.
     * <p>This method will not be executed if there is no game object associated with this controller.
     * @see Square
     */
    private void drawBoard() {
        if (game != null) {
            Square[][] squares = game.getChessBoard().getSquares();
            GraphicsContext gc = boardLayer.getGraphicsContext2D();
            gc.clearRect(0, 0, boardLayer.getWidth(), boardLayer.getHeight());
            for (Square[] square : squares) {
                for (Square s : square) {
                    gc.setFill(s.getColor());
                    double width = s.getWidth();
                    double height = s.getHeight();
                    double xPosition = s.getCoordinate().getCoordinateX() * width;
                    double yPosition = s.getCoordinate().getCoordinateY() * height;

                    gc.fillRect(xPosition, yPosition, width, height);
                }
            }
        }
    }

    /**
     * Will draw the pieces of a given Board Object. Note that the position of the pieces is dependant on the squares.
     * <p>This method will not be executed if there is no game object associated with this controller.
     */
    private void drawPieces() {
        if (game != null) {
            Square[][] squares = game.getChessBoard().getSquares();
            GraphicsContext gc = piecesLayer.getGraphicsContext2D();
            gc.clearRect(0, 0, piecesLayer.getWidth(), piecesLayer.getHeight());
            for (Square[] square : squares) {
                for (Square s : square) {
                    GamePieces gamePiece = s.getGamePiece();
                    if (gamePiece != null) {
                        double width = s.getWidth();
                        double height = s.getHeight();
                        double xPosition = s.getCoordinate().getCoordinateX() * width;
                        double yPosition = s.getCoordinate().getCoordinateY() * height;

                        Image image = new Image(String.valueOf(gamePiece.getImageURL()));
                        gc.drawImage(image, xPosition, yPosition, width, height);

                    }
                }
            }
        }
    }

    /**
     * Will draw the player help for the user. Note that the position of the help is dependant on the squares.
     * The currently selected square will be displayed in Blue and Possible moves in Red.
     * The drawing of the Square will be done with {@link MainWindowController#highlightSquare(Square, Color)}.
     * <p>This method will not be executed if there is no game object associated with this controller.
     */
    private void drawHelp() {
        if (game != null) {
            //Resetting the canvas
            GraphicsContext gc = helpLayer.getGraphicsContext2D();
            gc.clearRect(0, 0, helpLayer.getWidth(), helpLayer.getHeight());

            if (game.getCurrentSelection() != null) {
                //getting needed values
                GamePieces currentGamePiece = game.getCurrentSelection().getGamePiece();
                Coordinate currentCoordinate = game.getCurrentSelection().getCoordinate();
                ChessBoard board = game.getChessBoard();
                ArrayList<Coordinate> validMoves = currentGamePiece.getValidMoves(game.getChessBoard(), currentCoordinate);

                //Drawing red square around possible moves
                for (Coordinate validMove : validMoves) {
                    Square tempSquare = board.getSquare(validMove);
                    highlightSquare(tempSquare, Color.RED);
                }
                //Drawing blue square around own position
                Square tempSquare = board.getSquare(currentCoordinate);
                highlightSquare(tempSquare,Color.BLUE);
            }
        }
    }

    /**
     * Draws a smaller rectangle in a given Square to help the user making his move
     * @param square The square we want to draw a help for
     * @param color What color the help rectangle should be
     */
    private void highlightSquare(Square square, Color color){
        if(game != null) {
            GraphicsContext gc = helpLayer.getGraphicsContext2D();
            // Getting values
            double width = square.getWidth();
            double height = square.getHeight();
            double xPadding = width * 0.05; //There will b a 5% offset from the edge
            double yPadding = height * 0.05;
            double lineWidth = Math.sqrt(height * width)*0.05;
            double xPosition = square.getCoordinate().getCoordinateX() * width;
            double yPosition = square.getCoordinate().getCoordinateY() * height;
            gc.setStroke(color);
            gc.setLineWidth(lineWidth);
            // Drawing the highlight
            gc.strokeRect(xPosition+xPadding, yPosition+yPadding, width-xPadding*2, height-yPadding*2);
        }
    }

    /**
     * Starts a new HotSeatGame. Note that this method will terminate the previous game object.
     * @param event  selected from menu Game - new HotSeatGame
     */
    @FXML
    public void startHotSeatGame(ActionEvent event) {
        //Termination of old thread
        if (game != null) {
            game.setTerminated(true);
        }
        //Start of new game
        HotSeatGame hotSeatGame = new HotSeatGame(this);
        this.game = hotSeatGame;
        Thread th = new Thread(hotSeatGame);
        th.start();
    }

    /**
     * Collects the user Input and parses it to the Game logic.
     * This method will {@link Logic.Boards.ChessBoard#getSquare(double, double) get} the {@link Logic.Boards.Square} object that was clicked on the GUI
     * and send it to the running {@link Logic.Games.Game} object.
     * <P> Note that this method will not be executed if there's it not a Game object running
     * @param mouseEvent click on canvas boardLayer
     */
    @FXML
    public void handleMouseClick(MouseEvent mouseEvent) {
        if (game == null) {
            return;
        }
        double x = mouseEvent.getX();
        double y = mouseEvent.getY();
        Square clickedSquare = game.getChessBoard().getSquare(x, y);

        //Sending information to the game Thread
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


    /**
     * Creates a Dialog window that prompts the user to choose a UnitType. This method will be used when a Pawn reaches the other side of the Board.
     * @return the UnitType the pawn will be promoted to
     */
    public UnitType promotePawnDialog(){ //TODO possible candidate for rewrite
        UnitType returnUnitType = null;

        //Setting up the list of options
        List<String> choices = new ArrayList<>();
        choices.add(UnitType.QUEEN.getNormalName());
        choices.add(UnitType.ROCK.getNormalName());
        choices.add(UnitType.KNIGHT.getNormalName());
        choices.add(UnitType.BISHOP.getNormalName());

        //Don't allow escape from the menu unless the user filled out the form
        Optional<String> result;
        do {
            ChoiceDialog<String> dialog = new ChoiceDialog<>("Queen", choices);
            dialog.setTitle("Promotion Dialog");
            dialog.setHeaderText("Your Pawn may be promoted!");
            dialog.setContentText("Choose a type: ");

            result = dialog.showAndWait();
        }while (!result.isPresent());

        //Returning result
        String resultString = result.get();
        switch (resultString) {
            case "Queen":
                returnUnitType = UnitType.QUEEN;
                return returnUnitType;
            case "Rock":
                returnUnitType = UnitType.ROCK;
                return returnUnitType;
            case "Knight":
                returnUnitType = UnitType.KNIGHT;
                return returnUnitType;
            case "Bishop":
                returnUnitType = UnitType.BISHOP;
                return returnUnitType;
        }
        return returnUnitType;
    }


    /**
     * Get the boardLayer canvas.
     * This Canvas will be used to draw the squares that make the Board.
     * @return the boardLayer Canvas
     */
    public Canvas getBoardLayer() {
        return boardLayer;
    }

    /**
     * gets the continueLock.
     * The lock will be used to enable communication between the {@link Logic.Games.Game} runnable.
     * @return the continueLock
     */
    public Lock getContinueLock() {
        return continueLock;
    }

    /**
     * gets the continueCondition.
     * The condition will be used to enable to wake up the {@link Logic.Games.Game} runnable.
     * @return the continueLock
     */
    public Condition getContinueCondition() {
        return continueCondition;
    }

    /**
     * Sets the info Label to a new String.
     * The info label will inform the user of basic information, who's turn it is and if the game ended.
     * @param text the String that wil lbe displayed in the Label
     */
    public void setInfoLabel(String text) {
        infoLabel.setText(text);
    }
}


