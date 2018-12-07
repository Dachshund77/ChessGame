package Logic.Pieces;

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
