package Logic.Pieces;

/**
 * Enum type for what color we work with.
 */
public enum Faction {
    WHITE ("White"),
    BLACK ("Black");

    private final String name;

    Faction(String name){
        this.name = name;
    }

    public String getNormalName(){
        return this.name;
    }
}
