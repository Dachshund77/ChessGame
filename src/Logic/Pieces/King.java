package Logic.Pieces;

import Logic.Boards.ChessBoard;
import Logic.Coordinate;

import java.util.ArrayList;

public class King extends GamePiece{

    private static final UnitType UNIT_TYPE = UnitType.KING;
    private boolean hasMoved = false;

    public King(Faction faction){
        super(faction,UNIT_TYPE);
    }

    @Override
    public ArrayList<Coordinate> getValidMoves(ChessBoard board, Coordinate currentCoordinate) { //TODO implement Castle
        ArrayList<Coordinate> returnArrayList = new ArrayList<>();
        int x = currentCoordinate.getCoordinateX();
        int y = currentCoordinate.getCoordinateY();
        //North
        Coordinate north = new Coordinate(x, y - 1);
        if (north.isValidCoordinate(board)) {
            if (containsFriendly(board, north) == null || !containsFriendly(board, north)) {
                returnArrayList.add(north);
            }
        }
        //North East
        Coordinate northEast = new Coordinate(x+1, y - 1);
        if (northEast.isValidCoordinate(board)) {
            if (containsFriendly(board, northEast) == null || !containsFriendly(board, northEast)) {
                returnArrayList.add(northEast);
            }
        }
        //East
        Coordinate east = new Coordinate(x+1, y );
        if (east.isValidCoordinate(board)) {
            if (containsFriendly(board, east) == null || !containsFriendly(board, east)) {
                returnArrayList.add(east);
            }
        }
        //South East
        Coordinate southEast = new Coordinate(x+1, y+1 );
        if (southEast.isValidCoordinate(board)) {
            if (containsFriendly(board, southEast) == null || !containsFriendly(board, southEast)) {
                returnArrayList.add(southEast);
            }
        }
        //South
        Coordinate south = new Coordinate(x, y+1 );
        if (south.isValidCoordinate(board)) {
            if (containsFriendly(board, south) == null || !containsFriendly(board, south)) {
                returnArrayList.add(south);
            }
        }
        //South West
        Coordinate southWest = new Coordinate(x-1, y+1 );
        if (southWest.isValidCoordinate(board)) {
            if (containsFriendly(board, southWest) == null || !containsFriendly(board, southWest)) {
                returnArrayList.add(southWest);
            }
        }
        // West
        Coordinate west = new Coordinate(x-1, y );
        if (west.isValidCoordinate(board)) {
            if (containsFriendly(board, west) == null || !containsFriendly(board, west)) {
                returnArrayList.add(west);
            }
        }
        // North west
        Coordinate northWest = new Coordinate(x-1, y-1 );
        if (northWest.isValidCoordinate(board)) {
            if (containsFriendly(board, northWest) == null || !containsFriendly(board, northWest)) {
                returnArrayList.add(northWest);
            }
        }
        return returnArrayList;
    }

    public boolean hasMoved() {
        return hasMoved;
    }
    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }
}
