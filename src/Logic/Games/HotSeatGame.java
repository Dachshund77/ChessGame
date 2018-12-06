package Logic.Games;

import Logic.Boards.Square;
import Logic.Pieces.Faction;
import controllers.MainWindowController;

public class HotSeatGame extends Game {

    public HotSeatGame(MainWindowController controller) {
        super(controller);
    }

    @Override
    public void run() {
        getController().getContinueLock().lock();
        try {
            setUpBoard();
            while (!super.isTerminated() && !hasGameEnded()) {
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

        if (!super.isTerminated()){
            if (super.getCurrentSelection() != null && isValidMove(newSelection)){ //While haveing a selection cheks if cliked valid square
                System.out.println("Process: selected valid move ");
                updateBoardState(super.getCurrentSelection().getCoordinate(),newSelection.getCoordinate());
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
                System.out.println("Proces: set selection to null");
                super.setCurrentSelection(null);
            }
        }
        super.getController().updateGUI();
    }
}
