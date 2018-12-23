package Logic.Pieces;

/**
 * Enum type for what kind of GamePiece we work with.
 */
public enum UnitType {
    PAWN ("Pawn"),
    ROCK ("Rock"),
    KNIGHT ("Knight"),
    BISHOP ("Bishop"),
    QUEEN ("Queen"),
    KING ("King");

    private final String name;

    UnitType(String name){
        this.name = name;
    }

    public String getNormalName(){
        return this.name;
    }
}
