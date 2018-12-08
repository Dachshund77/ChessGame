package Logic.Pieces;

import Logic.Boards.ChessBoard;
import Logic.Coordinate;

import java.net.URL;

public abstract class GamePiece implements GamePieces{

    private Faction faction;
    private UnitType unitType;
    private URL imageURL;

    public GamePiece(Faction faction, UnitType unitType) {
        this.faction = faction;
        this.unitType = unitType;
        this.imageURL = getClass().getResource("../../resources/" + faction.getNormalName() + "_" + unitType.getNormalName() + ".png");

    }

    public URL getImageURL() {
        return imageURL;
    }

    public Boolean containsFriendly(ChessBoard board, Coordinate coordinate) {
        Boolean returnBoolean = null;
        GamePieces gamePiece = board.getSquare(coordinate).getGamePiece();
        if (gamePiece != null) {
            if (gamePiece.getFaction() == faction) {
                returnBoolean = true;
            } else if (gamePiece.getFaction() != faction) {
                returnBoolean = false;
            }
        }
        return returnBoolean;
    }

    public Faction getFaction() {
        return faction;
    }

    public UnitType getUnitType() {
        return unitType;
    }
}
