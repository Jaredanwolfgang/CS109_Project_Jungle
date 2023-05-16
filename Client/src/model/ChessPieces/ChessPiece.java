package model.ChessPieces;


import model.ChessBoard.Cell;
import model.ChessBoard.ChessboardPoint;
import model.ChessBoard.Move;
import model.Enum.Category;
import model.Enum.PlayerColor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

public class ChessPiece implements Serializable {
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
    //CAUTION: This method judge by rank and other special rules, and it checks whether two pieces have the same owner.
    //         But it does not check the chessboard.
    public boolean canCapture(ChessPiece target) {
        //This method should be overridden in each subclass
        return false;
    }

    //Input a point on the chessboard, return all available moves of this chess piece.
    //CAUTION: This method assumes that there is a piece at the given point.
    public ArrayList<Move> getAvailableMoves(ChessboardPoint point, Cell[][] board) {
        //This method should be overridden in each subclass
        return null;
    }

    //Input fromPoint and toPoint on the chessboard, return a move object.
    //If the move is invalid, return null.
    public Move moveTo(ChessboardPoint fromPoint, ChessboardPoint toPoint, Cell[][] board) {
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

    @Override
    public int hashCode() {
        return Objects.hash(owner, category);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ChessPiece other = (ChessPiece) obj;
        if (owner.equals(other.owner)) {
            return false;
        }
        if (category.equals(other.category)) {
            return false;
        }
        return true;
    }
}
