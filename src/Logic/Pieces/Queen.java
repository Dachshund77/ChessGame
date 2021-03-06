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
        ArrayList<Coordinate> returnArrayList = new ArrayList<>();
        int x = currentCoordinate.getCoordinateX();
        int y = currentCoordinate.getCoordinateY();
        boolean notBlocked;
        int counter;
        //North moves
        notBlocked = true;
        counter = 1;
        while (notBlocked) {
            Coordinate nextCoordinate = new Coordinate(x, y - counter);
            if (!nextCoordinate.isValidCoordinate(board)) { //Break condition
                break;
            } else if (containsFriendly(board, nextCoordinate) == null) { //When empty
                returnArrayList.add(nextCoordinate);
                counter++;
            } else if (!containsFriendly(board, nextCoordinate)) { //When occupied by enemy
                returnArrayList.add(nextCoordinate);
                notBlocked = false;
            } else { //Occupied by friendly
                notBlocked = false;
            }
        }
        //East moves
        notBlocked = true;
        counter = 1;
        while (notBlocked) {
            Coordinate nextCoordinate = new Coordinate(x + counter, y);
            if (!nextCoordinate.isValidCoordinate(board)) {
                break;
            } else if (containsFriendly(board, nextCoordinate) == null) { //When empty
                returnArrayList.add(nextCoordinate);
                counter++;
            } else if (!containsFriendly(board, nextCoordinate)) { //When occupied by enemy
                returnArrayList.add(nextCoordinate);
                notBlocked = false;
            } else { //Occupied by friendly
                notBlocked = false;
            }
        }
        //South moves
        notBlocked = true;
        counter = 1;
        while (notBlocked) {
            Coordinate nextCoordinate = new Coordinate(x, y + counter);
            if (!nextCoordinate.isValidCoordinate(board)) {
                break;
            } else if (containsFriendly(board, nextCoordinate) == null) { //When empty
                returnArrayList.add(nextCoordinate);
                counter++;
            } else if (!containsFriendly(board, nextCoordinate)) { //When occupied by enemy
                returnArrayList.add(nextCoordinate);
                notBlocked = false;
            } else { //Occupied by friendly
                notBlocked = false;
            }
        }
        //West moves
        notBlocked = true;
        counter = 1;
        while (notBlocked) {
            Coordinate nextCoordinate = new Coordinate(x - counter, y);
            if (!nextCoordinate.isValidCoordinate(board)) {
                break;
            } else if (containsFriendly(board, nextCoordinate) == null) { //When empty
                returnArrayList.add(nextCoordinate);
                counter++;
            } else if (!containsFriendly(board, nextCoordinate)) { //When occupied by enemy
                returnArrayList.add(nextCoordinate);
                notBlocked = false;
            } else { //Occupied by friendly
                notBlocked = false;
            }
        }
        //Northe East moves
        notBlocked = true;
        counter = 1;
        while (notBlocked) {
            Coordinate nextCoordinate = new Coordinate(x + counter, y - counter);
            if (!nextCoordinate.isValidCoordinate(board)) { //Break condition
                break;
            } else if (containsFriendly(board, nextCoordinate) == null) { //When empty
                returnArrayList.add(nextCoordinate);
                counter++;
            } else if (!containsFriendly(board, nextCoordinate)) { //When occupied by enemy
                returnArrayList.add(nextCoordinate);
                notBlocked = false;
            } else { //Occupied by friendly
                notBlocked = false;
            }
        }
        //South East moves
        notBlocked = true;
        counter = 1;
        while (notBlocked) {
            Coordinate nextCoordinate = new Coordinate(x + counter, y + counter);
            if (!nextCoordinate.isValidCoordinate(board)) { //Break condition
                break;
            } else if (containsFriendly(board, nextCoordinate) == null) { //When empty
                returnArrayList.add(nextCoordinate);
                counter++;
            } else if (!containsFriendly(board, nextCoordinate)) { //When occupied by enemy
                returnArrayList.add(nextCoordinate);
                notBlocked = false;
            } else { //Occupied by friendly
                notBlocked = false;
            }
        }
        //South West moves
        notBlocked = true;
        counter = 1;
        while (notBlocked) {
            Coordinate nextCoordinate = new Coordinate(x - counter, y + counter);
            if (!nextCoordinate.isValidCoordinate(board)) { //Break condition
                break;
            } else if (containsFriendly(board, nextCoordinate) == null) { //When empty
                returnArrayList.add(nextCoordinate);
                counter++;
            } else if (!containsFriendly(board, nextCoordinate)) { //When occupied by enemy
                returnArrayList.add(nextCoordinate);
                notBlocked = false;
            } else { //Occupied by friendly
                notBlocked = false;
            }
        }
        //North West moves
        notBlocked = true;
        counter = 1;
        while (notBlocked) {
            Coordinate nextCoordinate = new Coordinate(x - counter, y - counter);
            if (!nextCoordinate.isValidCoordinate(board)) { //Break condition
                break;
            } else if (containsFriendly(board, nextCoordinate) == null) { //When empty
                returnArrayList.add(nextCoordinate);
                counter++;
            } else if (!containsFriendly(board, nextCoordinate)) { //When occupied by enemy
                returnArrayList.add(nextCoordinate);
                notBlocked = false;
            } else { //Occupied by friendly
                notBlocked = false;
            }
        }
        return returnArrayList;
    }
}
