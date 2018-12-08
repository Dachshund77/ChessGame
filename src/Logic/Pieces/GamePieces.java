package Logic.Pieces;

import Logic.Boards.ChessBoard;
import Logic.Coordinate;

import java.net.URL;
import java.util.ArrayList;

public interface GamePieces {

    URL getImageURL();
    ArrayList<Coordinate> getValidMoves(ChessBoard board, Coordinate currentCoordinates);
    Boolean containsFriendly(ChessBoard board, Coordinate coordinate);
    Faction getFaction();
    UnitType getUnitType();

}
