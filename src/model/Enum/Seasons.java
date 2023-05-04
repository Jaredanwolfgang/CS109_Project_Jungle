package model.Enum;

public enum Seasons {
    SUMMER("Summer.gif"),
    FALL("Autumn.gif"),
    WINTER("Winter.gif"),
    SPRING("Spring.gif");
    private final String name;

    Seasons(String name){
        this.name = name;
    }
    public String getName(int seasons){
        return name;
    }
}
