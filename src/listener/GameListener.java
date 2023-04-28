package listener;

import model.ChessBoard.ChessboardPoint;
import view.CellComponent;
import view.ChessComponent.ChessComponent;
import view.ChessComponent.ElephantChessComponent;

public interface GameListener {

    void onPlayerClickCell(ChessboardPoint point, CellComponent component);

    void onPlayerClickChessPiece(ChessboardPoint point, ChessComponent component);

}
