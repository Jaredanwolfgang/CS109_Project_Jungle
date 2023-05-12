package model.AI;

import model.ChessBoard.Cell;
import model.ChessBoard.Chessboard;
import model.ChessBoard.Move;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class AI_Hard {
    static final int NUMBER_OF_ITERATIONS = 20000;

    public static Move findBestOneMove(Cell[][] board, Color player) {
        long current1 = System.currentTimeMillis();
        Cell[][] boardCopy = Chessboard.cloneBoard(board);
        MonteCarloTreeSearch mcts = new MonteCarloTreeSearch();
        mcts.root = new Node(player, null, boardCopy, null);
        mcts.findBestMove();
        long current2 = System.currentTimeMillis();
        System.out.printf("Time needed for %d iterations is %f seconds\n", NUMBER_OF_ITERATIONS, (current2 - current1) / 1000.0d);
        return mcts.bestMove.move;
    }

}

class MonteCarloTreeSearch {
    Node root;
    Node bestMove;

    Node findRolloutNode() {
        Node node = root;
        while (true) {
            if (node.winner != JungleSimulator.GAME_CONTINUES) {
                return node;
            }
            if (node.children.isEmpty()) {
                generateChildren(node);
                if(node.children.size() == 0) {
                    node.winner = JungleSimulator.flipColor(node.player);
                    return node;
                }
                return node.children.get(0);
            } else {
                for (Node child : node.children) {
                    child.setUCTValue();
                }
                Collections.sort(node.children);
                node = node.children.get(0);
                if (node.visits == 0) {
                    return node;
                }
            }
        }
    }

    void generateChildren(Node node) {
        ArrayList<Move> moves = JungleSimulator.getAvailableMoves(node.board, node.player);
        for (Move move : moves) {
            Cell[][] newBoard = Chessboard.cloneBoard(node.board);
            if (move.isDoesCapture()) {
                newBoard[move.getToPoint().getRow()][move.getToPoint().getCol()].removePiece();
            }
            newBoard[move.getToPoint().getRow()][move.getToPoint().getCol()].setPiece(newBoard[move.getFromPoint().getRow()][move.getFromPoint().getCol()].getPiece());
            newBoard[move.getFromPoint().getRow()][move.getFromPoint().getCol()].removePiece();
            if (newBoard[move.getToPoint().getRow()][move.getToPoint().getCol()].isTrap()) {
                newBoard[move.getToPoint().getRow()][move.getToPoint().getCol()].getPiece().setTrapped(true);
            }
            if (newBoard[move.getFromPoint().getRow()][move.getFromPoint().getCol()].isTrap()) {
                newBoard[move.getToPoint().getRow()][move.getToPoint().getCol()].getPiece().setTrapped(false);
            }
            Node child = new Node(JungleSimulator.flipColor(node.player), node, newBoard, move);
            child.winner = JungleSimulator.checkStatus(child.board, node.player);
            node.children.add(child);
        }
    }

    void backPropagate(Node node, Color winner) {
        Node currentNode = node;
        while (currentNode != null) {
            currentNode.visits++;
            if (JungleSimulator.flipColor(currentNode.player) == winner) {
                currentNode.wins++;
            } else {
                currentNode.losses++;
            }
            currentNode = currentNode.parent;
        }
    }

    void findBestMove() {
        for (int i = 0; i < AI_Hard.NUMBER_OF_ITERATIONS; i++) {
            Node rolloutNode = findRolloutNode();
            Color winner = JungleSimulator.rollout(rolloutNode);
            backPropagate(rolloutNode, winner);
        }
        //System.out.println(AI_Hard.historyMoves.size());
        double numVisits = 0;
        for (Node child : root.children) {
            if (child.visits > numVisits) {
                numVisits = child.visits;
                bestMove = child;
            }
        }
        for (Node child : root.children) {
            System.out.println(Arrays.toString(new String[]{child.visits + "  " + child.wins + "  " + child.losses + "  " + child.UCTValue + "  " + child.move}));
        }
        System.out.println();
    }
}

class Node implements Comparable<Node> {
    Node parent;
    ArrayList<Node> children;
    double wins, losses, UCTValue, visits;
    Cell[][] board;
    Move move;
    Color player;
    Color winner = JungleSimulator.GAME_CONTINUES;

    Node(Color player, Node parent, Cell[][] board, Move move) {
        this.player = player;
        this.parent = parent;
        this.board = board;
        this.move = move;
        this.children = new ArrayList<>();
    }

    void setUCTValue() {
        if (visits == 0) {
            UCTValue = Double.MAX_VALUE;
        } else {
            UCTValue = wins / visits + Math.sqrt(2 * Math.log(parent.visits) / visits);
        }
    }

    @Override
    public int compareTo(Node other) {
        return Double.compare(other.UCTValue, UCTValue);
    }
}

class JungleSimulator {
    static final Color GAME_CONTINUES = null;

    static Color rollout(Node node) {
        if (node.winner != GAME_CONTINUES) {
            return node.winner;
        }
        Color player = node.player;
        Cell[][] currentBoard = Chessboard.cloneBoard(node.board);
        while (true) {
            ArrayList<Move> moves = JungleSimulator.getAvailableMoves(currentBoard, player);
            if (moves.isEmpty()) {
                return JungleSimulator.flipColor(player);
            }
            int randomMoveIndex = (int) (Math.random() * moves.size());
            Move moveToMake = moves.get(randomMoveIndex);
            if (moveToMake.isDoesCapture()) {
                currentBoard[moveToMake.getToPoint().getRow()][moveToMake.getToPoint().getCol()].removePiece();
            }
            currentBoard[moveToMake.getToPoint().getRow()][moveToMake.getToPoint().getCol()].setPiece(currentBoard[moveToMake.getFromPoint().getRow()][moveToMake.getFromPoint().getCol()].getPiece());
            currentBoard[moveToMake.getFromPoint().getRow()][moveToMake.getFromPoint().getCol()].removePiece();
            if (currentBoard[moveToMake.getToPoint().getRow()][moveToMake.getToPoint().getCol()].isTrap()) {
                currentBoard[moveToMake.getToPoint().getRow()][moveToMake.getToPoint().getCol()].getPiece().setTrapped(true);
            }
            if (currentBoard[moveToMake.getFromPoint().getRow()][moveToMake.getFromPoint().getCol()].isTrap()) {
                currentBoard[moveToMake.getToPoint().getRow()][moveToMake.getToPoint().getCol()].getPiece().setTrapped(false);
            }
            Color winner = checkStatus(currentBoard, player);
            if (winner != GAME_CONTINUES) {
                return winner;
            }
            if (player == Color.BLUE) {
                if (currentBoard[8][4].getPiece() != null && currentBoard[8][4].getPiece().getOwner().getColor() == Color.BLUE &&
                        (currentBoard[8][5].getPiece() == null || currentBoard[8][4].getPiece().getOwner().getColor() == Color.BLUE) &&
                        (currentBoard[7][4].getPiece() == null || currentBoard[8][4].getPiece().getOwner().getColor() == Color.BLUE)) {
                    return player;
                }
                if (currentBoard[8][2].getPiece() != null && currentBoard[8][2].getPiece().getOwner().getColor() == Color.BLUE &&
                        (currentBoard[8][1].getPiece() == null || currentBoard[8][2].getPiece().getOwner().getColor() == Color.BLUE) &&
                        (currentBoard[7][2].getPiece() == null || currentBoard[8][2].getPiece().getOwner().getColor() == Color.BLUE)) {
                    return player;
                }
                if (currentBoard[7][3].getPiece() != null && currentBoard[7][3].getPiece().getOwner().getColor() == Color.BLUE &&
                        (currentBoard[6][3].getPiece() == null || currentBoard[7][3].getPiece().getOwner().getColor() == Color.BLUE) &&
                        (currentBoard[7][2].getPiece() == null || currentBoard[7][3].getOwner().getColor() == Color.BLUE) &&
                        (currentBoard[7][4].getPiece() == null || currentBoard[7][3].getOwner().getColor() == Color.BLUE)) {
                    return player;
                }
            }
            if (player == Color.RED) {
                if (currentBoard[0][4].getPiece() != null && currentBoard[0][4].getPiece().getOwner().getColor() == Color.RED &&
                        (currentBoard[0][5].getPiece() == null || currentBoard[0][4].getPiece().getOwner().getColor() == Color.RED) &&
                        (currentBoard[1][4].getPiece() == null || currentBoard[0][4].getPiece().getOwner().getColor() == Color.RED)) {
                    return player;
                }
                if (currentBoard[0][2].getPiece() != null && currentBoard[0][2].getPiece().getOwner().getColor() == Color.RED &&
                        (currentBoard[0][1].getPiece() == null || currentBoard[0][2].getPiece().getOwner().getColor() == Color.RED) &&
                        (currentBoard[1][2].getPiece() == null || currentBoard[0][2].getPiece().getOwner().getColor() == Color.RED)) {
                    return player;
                }
                if (currentBoard[1][3].getPiece() != null && currentBoard[1][3].getPiece().getOwner().getColor() == Color.RED &&
                        (currentBoard[2][3].getPiece() == null || currentBoard[1][3].getPiece().getOwner().getColor() == Color.RED) &&
                        (currentBoard[1][2].getPiece() == null || currentBoard[1][3].getPiece().getOwner().getColor() == Color.RED) &&
                        (currentBoard[1][4].getPiece() == null || currentBoard[1][3].getPiece().getOwner().getColor() == Color.RED)) {
                    return player;
                }
            }
            player = flipColor(player);
        }
    }

    static ArrayList<Move> getAvailableMoves(Cell[][] board, Color player) {
        return Chessboard.getAllPossibleMoveOnBoard(board, player);
    }

    static Color checkStatus(Cell[][] board, Color player) {
        if (player == Color.BLUE && board[8][3].getPiece() != null && board[8][3].getPiece().getOwner().getColor() == Color.BLUE) {
            return player;
        }
        if (player == Color.RED && board[0][3].getPiece() != null && board[0][3].getPiece().getOwner().getColor() == Color.RED) {
            return player;
        }
        boolean opponentHasNoPiece = true;
        Color opponent = flipColor(player);
        for(int i = 0; i < 9; i++){
            for(int j = 0; j < 7; j++){
                if(board[i][j].getPiece() != null && board[i][j].getPiece().getOwner().getColor() == opponent){
                    opponentHasNoPiece = false;
                    break;
                }
            }
        }
        if(opponentHasNoPiece){
            return player;
        }
        return GAME_CONTINUES;
    }

    public static Color flipColor(Color color) {
        if (color == Color.BLUE) {
            return Color.RED;
        }
        return Color.BLUE;
    }
}