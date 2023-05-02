package model.AI;

import model.ChessBoard.Cell;
import model.ChessBoard.Chessboard;
import model.ChessBoard.Move;

import java.awt.*;
import java.util.ArrayList;

public class AI_Easy {
    public static Move findBestOneMove(Cell[][] board, Color player){
       ArrayList<Move> moves = Chessboard.getAllPossibleMoveOnBoard(board, player);
       int random = (int)(Math.random() * moves.size());
       return moves.get(random);
    }
}
