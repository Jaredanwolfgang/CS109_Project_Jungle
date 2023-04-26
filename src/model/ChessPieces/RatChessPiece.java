package model.ChessPieces;
import model.ChessBoard.Cell;
import model.ChessBoard.ChessboardPoint;
import model.ChessBoard.Move;
import model.Enum.*;

import java.util.ArrayList;

public class RatChessPiece extends ChessPiece{
    public RatChessPiece(PlayerColor owner) {
        super(owner, Category.RAT);
    }

    @Override
    public boolean canCapture(ChessPiece target) {
        if(target.getCategory() == Category.RAT || target.getCategory() == Category.ELEPHANT) {
            return true;
        }
        return false;
    }

    @Override
    public ArrayList<Move> getAvailableMoves(ChessboardPoint point, Cell[][] board) {

        ArrayList<Move> possibleMoves = new ArrayList<>();
        int[] moveX = {0, 1, 0, -1};
        int[] moveY = {1, 0, -1, 0};
        int currentRow = point.getRow();
        int currentCol = point.getCol();
        Cell currentCell = board[currentRow][currentCol];

        for (int i = 0; i < 4; i++) {
            if(currentRow + moveX[i] >= 0 && currentRow + moveX[i] < Constant.CHESSBOARD_ROW_SIZE.getNum() &&
                    currentCol + moveY[i] >= 0 && currentCol + moveY[i] < Constant.CHESSBOARD_COL_SIZE.getNum()){

                ChessboardPoint targetPoint = new ChessboardPoint(currentRow + moveX[i], currentCol + moveY[i]);
                int targetRow = currentRow + moveX[i];
                int targetCol = currentCol + moveY[i];
                Cell targetCell = board[targetRow][targetCol];
                ChessPiece targetPiece = targetCell.getPiece();

                if(targetPiece == null) {
                    possibleMoves.add(new Move(this, point, targetPoint, false ,null));
                } else if (targetPiece.getOwner() != this.getOwner() && this.canCapture(targetPiece)) {
                    possibleMoves.add(new Move(this, point, targetPoint, true, targetPiece));
                }
            }
        }
        return possibleMoves;
    }
}
