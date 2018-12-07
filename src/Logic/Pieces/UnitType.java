package Logic.Pieces;

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

    public String getName(){
        return this.name;
    }
}
