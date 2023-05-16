package listener;

import view.CellComponent;

public interface HoverListener {
    void onHovered(CellComponent cellComponent);
    void onExited(CellComponent cellComponent);

}
