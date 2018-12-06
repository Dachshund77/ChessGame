package Logic.Pieces;

import Logic.Boards.ChessBoard;
import Logic.Coordinate;

import java.util.ArrayList;

public class Queen extends GamePiece {

    private static final UnitType UNIT_TYPE = UnitType.QUEEN;

    public Queen(Faction faction) {
        super(faction, UNIT_TYPE);
    }

    @Override
    public ArrayList<Coordinate> getValidMoves(ChessBoard board, Coordinate currentCoordinate) {
        return new ArrayList<>();
    }
}
