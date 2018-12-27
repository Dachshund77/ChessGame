package Logic.Games;

import Logic.Boards.Square;
import Logic.Pieces.Faction;
import Controllers.MainWindowController;
import javafx.application.Platform;

/**
 * ChessGame between to Human players the same machine.
 */
public class HotSeatGame extends Game {

    public HotSeatGame(MainWindowController controller) {
        super(controller);
    }

    /**
     * The run method will keep track of the basic gameLoop.
     */
    @Override
    public void run() {
        getController().getContinueLock().lock();
        try {
            setUpBoard();
            while (!isTerminated() && !hasGameEnded()) {
                Platform.runLater(()-> getController().setInfoLabel(getTurnOrder().getNormalName()+" players turn!"));
                Platform.runLater(()-> getController().updateGUI());
                getController().getContinueCondition().await();
                checkForGameEnd();
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }finally {
            getController().getContinueLock().unlock();
        }
    }

    public void processUserInput(Square newSelection){
        if (!isTerminated() && !hasGameEnded()){
            //While having a selection checks if clicked valid square
            if (getCurrentSelection() != null && isValidMove(newSelection)){
                processTurn(getCurrentSelection().getCoordinate(),newSelection.getCoordinate());
                setCurrentSelection(null);
                //Change who's turn it is
                if (getTurnOrder() == Faction.WHITE){
                    setTurnOrder(Faction.BLACK);
                }
                else {
                    setTurnOrder(Faction.WHITE);
                }
            }
            // Same square selected
            else if (getCurrentSelection() != null && getCurrentSelection().equals(newSelection)){
                setCurrentSelection(null);
            }
            //Selected an friendly Piece that is not the same
            else if(newSelection.getGamePiece() != null && newSelection.getGamePiece().getFaction().equals(getTurnOrder())){
                setCurrentSelection(newSelection);
            }
            //All other cases
            else {
                setCurrentSelection(null);
            }
        }
        getController().updateGUI();
    }
}
