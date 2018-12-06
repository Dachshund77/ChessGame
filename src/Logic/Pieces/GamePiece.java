package Logic.Pieces;

import Logic.Boards.ChessBoard;
import Logic.Coordinate;

import java.net.URL;

public abstract class GamePiece {

    private Faction faction;
    private UnitType unitType;
    private boolean hasMoved; //TODO Only King, Pawn and Rock cares about that actually
    //TODO What about enPassante?
    private URL imageURL;

    public GamePiece(Faction faction, UnitType unitType){
        this.faction = faction;
        this.unitType = unitType;
        hasMoved = false;
        this.imageURL = getClass().getResource("../../resources/"+faction.getName()+"_"+unitType.getName()+".png");

    }

    public void setHasMoved(boolean hasMoved){
        this.hasMoved = hasMoved;
    }

    public URL getImageURL() {
        return imageURL;
    }

    public boolean isHasMoved() {
        return hasMoved;
    }

    abstract Coordinate[] validMoves(ChessBoard board);
}
