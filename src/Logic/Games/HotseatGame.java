package Logic.Games;

import Logic.Boards.Square;
import Logic.Pieces.Faction;
import controllers.MainWindowController;
import javafx.application.Platform;

public class HotseatGame extends Game {

    public HotseatGame(MainWindowController controller) {
        super(controller);
    }

    @Override
    public void run() {
        getController().getContinueLock().lock();
        try {
            setUpBoard();
            while (!super.isTerminated() && !hasGameEnded()) {
                Platform.runLater(()-> getController().setInfoLabel(getTurnOrder().getNormalName()+" players turn!"));
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

        if (!super.isTerminated()){ //TODO increase readbility and remove unneeded statements
            if (super.getCurrentSelection() != null && isValidMove(newSelection)){ //While having a selection checks if clicked valid square
                System.out.println("Process: selected valid move ");
                movePiece(super.getCurrentSelection().getCoordinate(),newSelection.getCoordinate());
                super.setCurrentSelection(null);
                if (super.getTurnOrder() == Faction.WHITE){
                    super.setTurnOrder(Faction.BLACK);
                }
                else {
                    super.setTurnOrder(Faction.WHITE);
                }
            }
            else if(newSelection.getGamePiece() == null){ //Selected a empty space
                this.setCurrentSelection(null);
                System.out.println("Process: selected empty space");
            }
            else if(newSelection.equals(super.getCurrentSelection())){ //Selected same space
                super.setCurrentSelection(null);
                System.out.println("Process: selected same space");
            }
            else if (newSelection.getGamePiece().getFaction().equals(super.getTurnOrder())){ //Selected an friendly piece
                super.setCurrentSelection(newSelection);
                System.out.println("Process: selected friendly piece");
            }
            else {
                System.out.println("Process: set selection to null");
                super.setCurrentSelection(null);
            }
        }
        super.getController().updateGUI();
    }
}
