package Logic.Pieces;

import Logic.Board.Board;

public abstract class GamePiece {

    private Faction faction;
    private UnitType unitType;
    private boolean hasMoved;

    public GamePiece(Faction faction, UnitType unitType){
        this.faction = faction;
        this.unitType = unitType;
        hasMoved = false;
    }

    public void setHasMoved(boolean hasMoved){
        this.hasMoved = hasMoved;
    }

    abstract int[][] validMoves(Board board);
}
