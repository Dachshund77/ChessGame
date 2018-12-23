package Logic.Pieces;

import Logic.Boards.ChessBoard;
import Logic.Coordinate;

import java.net.URL;
import java.util.ArrayList;

/**
 * Interface for the GamePieces. As we need to make sure that a number of method exist we will make a
 * "contract" that those methods need to implemented somewhere in Child classes.
 */
public interface GamePieces {

    /**
     * Get the the relative position for the image of the gamePiece
     * @return String with relative path
     */
    URL getImageURL();

    /**
     * Will return a list of {@link Coordinate Coordinates} that a specific GamePiece can move to.
     * Those Coordinates will be valid and inside the ChessBoard.
     * @param board The ChessBoard the game is played on
     * @param currentCoordinates The Coordinate of the GamePiece
     * @return ArrayList of Coordinates with all possible moves
     */
    ArrayList<Coordinate> getValidMoves(ChessBoard board, Coordinate currentCoordinates);

    /**
     * Checks if another Square contains a friendly GamePiece.
     * @param board The ChessBoard the game is played on
     * @param coordinate Coordinate of the other GamePiece we want to compare with
     * @return True if same Faction, False if different faction, null if empty
     */
    Boolean containsFriendly(ChessBoard board, Coordinate coordinate);

    /**
     * Gets the Faction of the GamePiece
     * @return Faction of the GamePiece
     */
    Faction getFaction();

    /**
     * Gets the UnitType if the GamePiece
     * @return UnitType of the GamePiece
     */
    UnitType getUnitType();

}
