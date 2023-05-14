package model.Enum;

import java.awt.*;

public enum Seasons {
    /*To set the colors of each season:
        0. The normal chessboard color
        1. The color of the river
        2. The color of the trap
        3. The color of the den
        4. The color of the cell that the selected piece can move to
        5. The hovering color
        The order of the colors is the same as the order of the enum
    * */
    SPRING("Spring.gif",
            new Color(163, 220, 136),
            new Color(157, 245, 255),
            new Color(130, 157, 129),
            new Color(101, 126, 100),
            new Color(197, 151, 117),
            new Color(255, 244, 171)
            ),
    SUMMER("Summer.gif",
            new Color(255, 206, 100),
            new Color(133, 157, 253),
            new Color(239, 165, 165),
            new Color(203, 128, 128),
            new Color(255, 81, 0),
            new Color(255, 169, 129)
            ),
    FALL("Autumn.gif",
            new Color(203, 128, 128),
            new Color(130, 157, 129),
            new Color(189, 91, 91),
            new Color(145, 61, 61),
            new Color(255, 206, 100),
            new Color(255, 229, 100)
            ),
    WINTER("Winter.gif",
            new Color(241, 241, 245),
            new Color(224, 230, 250),
            new Color(150, 163, 217),
            new Color(104, 126, 190),
            new Color(255, 229, 100),
            new Color(255, 248, 200)
            );
    private final String name;
    private final Color[] colors;

    Seasons(String name, Color... colors){
        this.name = name;
        this.colors = colors;
    }
    public String getName(){
        return name;
    }
    public Color[] getColors() {
        return colors;
    }
}
