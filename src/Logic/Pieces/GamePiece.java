package Logic.Pieces;

import Logic.Boards.ChessBoard;
import Logic.Coordinate;

import java.net.URL;
import java.util.ArrayList;

public abstract class GamePiece {

    private Faction faction;
    private UnitType unitType;
    private URL imageURL;

    public GamePiece(Faction faction, UnitType unitType) {
        this.faction = faction;
        this.unitType = unitType;
        this.imageURL = getClass().getResource("../../resources/" + faction.getNormalName() + "_" + unitType.getName() + ".png");

    }

    public URL getImageURL() {
        return imageURL;
    }

    public abstract ArrayList<Coordinate> getValidMoves(ChessBoard board, Coordinate currentCoordinate);

    Boolean containsFriendly(ChessBoard board, Coordinate coordinate) {
        Boolean returnBoolean = null;
        GamePiece gamePiece = board.getSquare(coordinate).getGamePiece();
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
