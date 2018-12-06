package Logic.Games;

import Logic.Boards.ChessBoard;
import controllers.MainWindowController;
import javafx.scene.canvas.Canvas;

public abstract class Game implements Runnable {

    private MainWindowController controller;
    private ChessBoard chessBoard;

    public Game(MainWindowController controller, Canvas boardLayer){
        this.controller = controller;
        this.chessBoard = new ChessBoard(boardLayer);
    }

    public void run(){

    }
}
