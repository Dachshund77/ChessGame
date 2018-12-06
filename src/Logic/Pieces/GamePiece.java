package Logic.Pieces;

import Logic.Boards.ChessBoard;
import Logic.Coordinate;

import java.net.URL;
import java.util.ArrayList;

public abstract class GamePiece {

    private Faction faction;
    private UnitType unitType;
    private URL imageURL;

    public GamePiece(Faction faction, UnitType unitType){
        this.faction = faction;
        this.unitType = unitType;
        this.imageURL = getClass().getResource("../../resources/"+faction.getName()+"_"+unitType.getName()+".png");

    }

    public URL getImageURL() {
        return imageURL;
    }

    public abstract ArrayList<Coordinate> getValidMoves(ChessBoard board, Coordinate currentCoordinate);

    public Faction getFaction() {
        return faction;
    }

    public UnitType getUnitType() {
        return unitType;
    }
}
