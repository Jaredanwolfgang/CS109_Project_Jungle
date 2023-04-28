package model.Enum;

public enum Seasons {
    SUMMER("Summer.gif",0),
    FALL("Autumn.gif",1),
    WINTER("Winter.gif",2),
    SPRING("Spring.gif",3);
    private final String name;
    private int i;
    Seasons(String name,int i){
        this.name = name;
        this.i = i;
    }
    public String getName(){
        return name;
    }
    public int getI(){
        return i;
    }
    public Seasons getSeason(int i){
        return Seasons.values()[i];
    }

}
