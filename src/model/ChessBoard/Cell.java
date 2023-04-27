package model.ChessBoard;

import model.ChessPieces.ChessPiece;
import model.Enum.PlayerColor;

import java.io.Serializable;
import java.util.Arrays;

/**
 * This class describe the slot for Chess in Chessboard
 * */
public class Cell {
    // the position for chess
    private ChessPiece piece = null;
    private PlayerColor owner = null;

    private boolean isTrap = false;
    private boolean isRiver = false;
    private boolean isDen = false;

    public Cell() {
    }

    public PlayerColor getOwner() {
        return owner;
    }

    public void setOwner(PlayerColor owner) {
        this.owner = owner;
    }

    public boolean isTrap() {
        return isTrap;
    }

    public boolean isRiver() {
        return isRiver;
    }

    public boolean isDen() {
        return isDen;
    }

    public void setTrap(boolean trap) {
        isTrap = trap;
    }

    public void setRiver(boolean river) {
        isRiver = river;
    }

    public void setDen(boolean den) {
        isDen = den;
    }

    public ChessPiece getPiece() {
        return piece;
    }

    public void setPiece(ChessPiece piece) {
        this.piece = piece;
    }

    public void removePiece() {
        this.piece = null;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Arrays.deepHashCode(new Object[] { piece });
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Cell other = (Cell) obj;
        if(piece.getOwner() == other.piece.getOwner() && piece.getCategory() == other.piece.getCategory()){
            return true;
        }
        return false;
    }
}
