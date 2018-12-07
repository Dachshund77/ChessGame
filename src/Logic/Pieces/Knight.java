package Logic.Pieces;

import Logic.Boards.ChessBoard;
import Logic.Coordinate;

import java.util.ArrayList;

public class Knight extends GamePiece {

    private static final UnitType UNIT_TYPE = UnitType.KNIGHT;

    public Knight(Faction faction) {
        super(faction, UNIT_TYPE);
    }

    @Override
    public ArrayList<Coordinate> getValidMoves(ChessBoard board, Coordinate currentCoordinate) {
        ArrayList<Coordinate> returnArrayList = new ArrayList<>();
        int x = currentCoordinate.getCoordinateX();
        int y = currentCoordinate.getCoordinateY();
        //North moves
        Coordinate topLeft = new Coordinate(x - 1, y - 2);
        if (topLeft.isValidCoordinate(board)) {
            if (containsFriendly(board, topLeft) == null || !containsFriendly(board, topLeft)) {
                returnArrayList.add(topLeft);
            }
        }
        Coordinate topRight = new Coordinate(x + 1, y - 2);
        if (topRight.isValidCoordinate(board)) {
            if (containsFriendly(board, topRight) == null || !containsFriendly(board, topRight)) {
                returnArrayList.add(topRight);
            }
        }
        //East moves
        Coordinate rightTop = new Coordinate(x + 2, y - 1);
        if (rightTop.isValidCoordinate(board)) {
            if (containsFriendly(board, rightTop) == null || !containsFriendly(board, rightTop)) {
                returnArrayList.add(rightTop);
            }
        }
        Coordinate rightDown = new Coordinate(x + 2, y + 1);
        if (rightDown.isValidCoordinate(board)) {
            if (containsFriendly(board, rightDown) == null || !containsFriendly(board, rightDown)) {
                returnArrayList.add(rightDown);
            }
        }
        //South moves
        Coordinate downLeft = new Coordinate(x - 1, y + 2);
        if (downLeft.isValidCoordinate(board)) {
            if (containsFriendly(board, downLeft) == null || !containsFriendly(board, downLeft)) {
                returnArrayList.add(downLeft);
            }
        }
        Coordinate downRight = new Coordinate(x + 1, y + 2);
        if (downRight.isValidCoordinate(board)) {
            if (containsFriendly(board, downRight) == null || !containsFriendly(board, downRight)) {
                returnArrayList.add(downRight);
            }
        }
        //west moves
        Coordinate leftTop = new Coordinate(x - 2, y - 1);
        if (leftTop.isValidCoordinate(board)) {
            if (containsFriendly(board, leftTop) == null || !containsFriendly(board, leftTop)) {
                returnArrayList.add(leftTop);
            }
        }
        Coordinate leftDown = new Coordinate(x - 2, y + 1);
        if (leftDown.isValidCoordinate(board)) {
            if (containsFriendly(board, leftDown) == null || !containsFriendly(board, leftDown)) {
                returnArrayList.add(leftDown);
            }
        }
        return returnArrayList;
    }
}
