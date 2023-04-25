package model.Enum;

public enum Category {
    ELEPHANT(8,"Elephant"),LION(7, "Lion"),TIGER(6,"Tiger"),LEOPARD(5, "Leopard"),WOLF(4,"Wolf"),DOG(3, "Dog"),CAT(2,"Cat"),RAT(1,"Rat");
    private final int rank;
    private final String name;
    private Category(int rank, String name){
        this.rank = rank;
        this.name = name;
    }

    public int getRank() {
        return rank;
    }

    public String getName() {
        return name;
    }
}
