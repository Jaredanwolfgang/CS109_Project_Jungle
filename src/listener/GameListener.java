package listener;

import model.ChessBoard.ChessboardPoint;
import view.CellComponent;
import view.ChessComponent.ElephantChessComponent;

public interface GameListener {

    void onPlayerClickCell(ChessboardPoint point, CellComponent component);

    void onPlayerClickChessPiece(ChessboardPoint point, ElephantChessComponent component);

    void onPlayerClickUndoButton();
    void onPlayerClickResetButton();
    void onPlayerClickSaveButton();
    void onPlayerClickLoadButton();
}
