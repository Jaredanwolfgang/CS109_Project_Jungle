package model.ChessPieces;


import model.ChessBoard.Cell;
import model.ChessBoard.ChessboardPoint;
import model.ChessBoard.Move;
import model.Enum.Category;
import model.Enum.PlayerColor;

import java.util.ArrayList;

public class ChessPiece {
    //Owner of the chess
    private PlayerColor owner;

    //Category of the chess
    //Also defines rank and name of the chess piece.
    private Category category;

    //Whether this piece go into the trap
    private boolean isTrapped = false;

    public ChessPiece(PlayerColor owner, Category category) {
        this.owner = owner;
        this.category = category;
    }

    //Input a target chess piece, return whether this chess piece can capture the target.
    //CAUTION: This method judge only by rank, not by position or owner.
    public boolean canCapture(ChessPiece target) {
        //This method should be overridden in each subclass
        return false;
    }

    public ArrayList<Move> getAvailableMoves(ChessboardPoint point, Cell[][] board) {
        //This method should be overridden in each subclass
        return null;
    }

    public boolean isTrapped() {
        return isTrapped;
    }

    public String getName() {
        return category.getName();
    }

    public PlayerColor getOwner() {
        return owner;
    }

    public int getRank() {
        return category.getRank();
    }

    public Category getCategory() {
        return category;
    }

    public void setTrapped(boolean trapped) {
        isTrapped = trapped;
    }
    //Setter of this class below should never be used,
    //Because every chess piece should be constructed only once, at the beginning of each game.
    //Double check this part if setter is ever used.
    public void setOwner(PlayerColor owner) {
        this.owner = owner;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
