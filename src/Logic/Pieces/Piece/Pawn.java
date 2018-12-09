package Logic.Pieces.Piece;

import Logic.Boards.ChessBoard;
import Logic.Coordinate;
import Logic.Pieces.Faction;
import Logic.Pieces.GamePiece;
import Logic.Pieces.UnitType;

import java.util.ArrayList;

public class Pawn extends GamePiece { //TODO what about promotion?

    private static final UnitType UNIT_TYPE = UnitType.PAWN;
    private boolean hasMoved = false;
    private Coordinate enPassanteCordinate = null;

    public Pawn(Faction faction) {
        super(faction, UNIT_TYPE);
    }

    @Override
    public ArrayList<Coordinate> getValidMoves(ChessBoard board, Coordinate currentCoordinate) {
        ArrayList<Coordinate> returnArrayList;
        if (getFaction() == Faction.WHITE) {
            returnArrayList = getValidWhiteMoves(board, currentCoordinate);
        } else {
            returnArrayList = getValidBlackMoves(board, currentCoordinate);
        }

        return returnArrayList;
    }

    private ArrayList<Coordinate> getValidWhiteMoves(ChessBoard board, Coordinate currentCoordinate) { //TODO removing en passable game Piece?
        ArrayList<Coordinate> returnArrayList = new ArrayList<>();
        int x = currentCoordinate.getCoordinateX();
        int y = currentCoordinate.getCoordinateY();
        //One space ahead
        Coordinate normalMove = new Coordinate(x, y - 1);
        if (normalMove.isValidCoordinate(board)) {
            if (containsFriendly(board, normalMove) == null) {
                returnArrayList.add(normalMove);
                //First move
                if (!hasMoved) {
                    Coordinate openingMove = new Coordinate(x, y - 2);
                    if (openingMove.isValidCoordinate(board)) {
                        if (openingMove.isValidCoordinate(board) && containsFriendly(board, openingMove) == null) {
                            returnArrayList.add(openingMove);
                        }
                    }
                }
            }
        }
        //Attack left
        Coordinate attackLeft = new Coordinate(x - 1, y - 1);
        if (attackLeft.isValidCoordinate(board)) {
            if (containsFriendly(board, attackLeft) != null && !containsFriendly(board, attackLeft)) {
                returnArrayList.add(attackLeft);
            }
        }
        //Attack right
        Coordinate attackRight = new Coordinate(x + 1, y - 1);
        if (attackRight.isValidCoordinate(board)) {
            if (attackRight.isValidCoordinate(board) && containsFriendly(board, attackRight) != null && !containsFriendly(board, attackRight)) {
                returnArrayList.add(attackRight);
            }
        }
        //En passante
        if (enPassanteCordinate != null) {
            Coordinate enPassant = new Coordinate(enPassanteCordinate.getCoordinateX(),enPassanteCordinate.getCoordinateY()-1);
            returnArrayList.add(enPassant);
        }

        return returnArrayList;
    }

    private ArrayList<Coordinate> getValidBlackMoves(ChessBoard board, Coordinate currentCoordinate) { //TODO removing en passable game Piece?
        ArrayList<Coordinate> returnArrayList = new ArrayList<>();
        int x = currentCoordinate.getCoordinateX();
        int y = currentCoordinate.getCoordinateY();
        //One space ahead
        Coordinate normalMove = new Coordinate(x, y + 1);
        if (normalMove.isValidCoordinate(board)) {
            if (containsFriendly(board, normalMove) == null) {
                returnArrayList.add(normalMove);
                //First move
                if (!hasMoved) {
                    Coordinate openingMove = new Coordinate(x, y + 2);
                    if (openingMove.isValidCoordinate(board)) {
                        if (openingMove.isValidCoordinate(board) && containsFriendly(board, openingMove) == null) {
                            returnArrayList.add(openingMove);
                        }
                    }
                }
            }
        }
        //Attack left
        Coordinate attackLeft = new Coordinate(x - 1, y + 1);
        if (attackLeft.isValidCoordinate(board)) {
            if (containsFriendly(board, attackLeft) != null && !containsFriendly(board, attackLeft)) {
                returnArrayList.add(attackLeft);
            }
        }
        //Attack right
        Coordinate attackRight = new Coordinate(x + 1, y + 1);
        if (attackRight.isValidCoordinate(board)) {
            if (containsFriendly(board, attackRight) != null && !containsFriendly(board, attackRight)) {
                returnArrayList.add(attackRight);
            }
        }
        //En passante
        if (enPassanteCordinate != null) {
            Coordinate enPassant = new Coordinate(enPassanteCordinate.getCoordinateX(),enPassanteCordinate.getCoordinateY()+1);
            returnArrayList.add(enPassant);
        }

        return returnArrayList;
    }

    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }

    public Coordinate getEnPassanteCordinate() {
        return enPassanteCordinate;
    }

    public void setEnPassanteCordinate(Coordinate enPassanteCordinate) {
        this.enPassanteCordinate = enPassanteCordinate;
    }
}
