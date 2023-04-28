package model.Enum;

public enum Mode {
    PVP("PVP"),
    NET("NET"),
    AI("AI");
    private final String name;
    Mode(String name){
        this.name = name;
    }
}
