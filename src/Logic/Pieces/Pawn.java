package Logic.Pieces;

import Logic.Boards.ChessBoard;
import Logic.Coordinate;

import java.util.ArrayList;

public class Pawn extends GamePiece {

    private static final UnitType UNIT_TYPE = UnitType.PAWN;
    private boolean hasMoved = false;
    private boolean canEnPassant = false;

    public Pawn(Faction faction) {
        super(faction, UNIT_TYPE);
    }

    @Override
    public ArrayList<Coordinate> getValidMoves(ChessBoard board, Coordinate currentCoordinate) { //TODO just a test for white pawns
        ArrayList<Coordinate> returnArrayList = new ArrayList<>();
        returnArrayList.add(new Coordinate(currentCoordinate.getCoordinateX(),currentCoordinate.getCoordinateY()-1));
        return returnArrayList;
    }

    public boolean hasMoved() {
        return hasMoved;
    }

    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }

    public boolean canEnPassant() {
        return canEnPassant;
    }

    public void setCanEnPassant(boolean canEnPassant) {
        this.canEnPassant = canEnPassant;
    }
}
