package Logic.Games;

import Logic.Boards.ChessBoard;
import Logic.Boards.Square;

/**
 * The methods in this interface will be need for the MainWindowController
 */
public interface Games {

    /**
     * Get the ChessBoard associated with this game
     * @return a ChessBoard object
     */
    ChessBoard getChessBoard();

    /**
     * Get the current selection in the Game
     * @return Currently selected Square or Null if non are selected
     */
    Square getCurrentSelection();

    /**
     * Sets the terminated field.
     * This will be used to gracefully exit the runnable
     * @param terminated what state the isTerminated field should be
     */
    void setTerminated(boolean terminated);

    /**
     * Handel's how the game should react on a valid action on the Canvas.
     * The Game will either set a new Square as currently selected, reset the selection or will execute a valid move.
     * @param newSelection The Square that was clicked on by tge user
     */
    void processUserInput(Square newSelection);

}
