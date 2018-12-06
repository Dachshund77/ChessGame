package Logic.Pieces;

import Logic.Boards.ChessBoard;
import Logic.Coordinate;

public class King extends GamePiece{

    private static final UnitType UNIT_TYPE = UnitType.KING;

    public King(Faction faction){
        super(faction,UNIT_TYPE);
    }

    @Override
    Coordinate[] validMoves(ChessBoard board) {
        return new Coordinate[0];
    }
}
