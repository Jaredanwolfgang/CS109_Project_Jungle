package model.AI;

import model.ChessBoard.Cell;
import model.ChessBoard.Chessboard;
import model.ChessBoard.ChessboardPoint;
import model.ChessBoard.Move;
import model.ChessPieces.ChessPiece;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class AI_MCTS {
    static final int NUMBER_OF_ITERATIONS = 10000;
    public static Move findBestOneMove(Cell[][] board, Color player){
        Cell[][] boardCopy = Chessboard.cloneBoard(board);
        MonteCarloTreeSearch mcts = new MonteCarloTreeSearch();
        mcts.root = new Node(player, null, boardCopy, null);
        if(JungleSimulator.getAvailableMoves(mcts.root.board,player).isEmpty() ||
                JungleSimulator.getAvailableMoves(mcts.root.board,JungleSimulator.flipColor(player)).isEmpty() ||
                JungleSimulator.checkStatus(boardCopy,player) == player){
            System.out.println("ERROR: The game is already over. No further move.");
            return null;
        }
        mcts.findBestMove(NUMBER_OF_ITERATIONS);
        return mcts.bestMove.move;
    }
}

class MonteCarloTreeSearch {
    Node root;
    Node bestMove;
    Node findRolloutNode(){
        Node node = root;
        while(true){
            if(node.winner != JungleSimulator.GAME_CONTINUES){
                return node;
            }
            if(node.children.isEmpty()){
                generateChildren(node);
                return node.children.get(0);
            }else{
                for(Node child : node.children){
                    child.setUCTValue();
                }
                Collections.sort(node.children);
                node = node.children.get(0);
                if(node.visits == 0){
                    return node;
                }
            }
        }
    }
    void generateChildren(Node node){
        ArrayList<Move> moves = JungleSimulator.getAvailableMoves(node.board,node.player);
        for(Move move : moves){
            Cell[][] newBoard = Chessboard.cloneBoard(node.board);
            if(move.isDoesCapture()){
                newBoard[move.getToPoint().getRow()][move.getToPoint().getCol()].removePiece();
            }
            newBoard[move.getToPoint().getRow()][move.getToPoint().getCol()].setPiece(newBoard[move.getFromPoint().getRow()][move.getFromPoint().getCol()].getPiece());
            newBoard[move.getFromPoint().getRow()][move.getFromPoint().getCol()].removePiece();
            Node child = new Node(JungleSimulator.flipColor(node.player), node, newBoard, move);
            child.winner = JungleSimulator.checkStatus(child.board , child.player);
            node.children.add(child);
        }
    }
    void backPropagate(Node node, Color winner){
        Node currentNode = node;
        while(currentNode != null){
            currentNode.visits++;
            if(currentNode.player == winner){
                currentNode.wins++;
            }else{
                currentNode.losses++;
            }
            currentNode = currentNode.parent;
        }
    }
    void findBestMove (int numIteration){
        for(int i = 0; i < numIteration; i++){
            Node rolloutNode = findRolloutNode();
            Color winner = JungleSimulator.rollout(rolloutNode);
            backPropagate(rolloutNode, winner);
        }
        double numVisits = 0;
        for(Node child : root.children){
            if(child.visits > numVisits){
                numVisits = child.visits;
                bestMove = child;
            }
        }
        for (Node child : root.children){
            System.out.println(Arrays.toString(new String[]{child.visits + "  " + child.wins + "  " + child.losses + "  " + child.UCTValue + "  " + child.move}));
        }
        System.out.println();
        //simulator.printGame(bestMove.board);
        //System.out.println();
    }
}

class Node implements Comparable <Node>{
    Node parent;
    ArrayList<Node> children;
    double wins, losses, UCTValue, visits;
    Cell[][] board;
    Move move;
    Color player;
    Color winner = JungleSimulator.GAME_CONTINUES;
    Node (Color player, Node parent, Cell[][] board, Move move) {
        this.player = player;
        this.parent = parent;
        this.board = board;
        this.move = move;
        this.children = new ArrayList<>();
    }
    void setUCTValue(){
        if(visits == 0){
            UCTValue = Double.MAX_VALUE;
        }else{
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
        //int counter = 0;
        Color player = flipColor(node.player);
        Cell[][] currentBoard = Chessboard.cloneBoard(node.board);
        while(true){
            ArrayList<Move> moves = Chessboard.getAllPossibleMoveOnBoard(currentBoard, player);
            if(moves.isEmpty()){
                return player;
            }
            int randomMoveIndex = (int) (Math.random()*moves.size());
            Move moveToMake = moves.get(randomMoveIndex);
            if(moveToMake.isDoesCapture()){
                currentBoard[moveToMake.getToPoint().getRow()][moveToMake.getToPoint().getCol()].removePiece();
            }
            currentBoard[moveToMake.getToPoint().getRow()][moveToMake.getToPoint().getCol()].setPiece(currentBoard[moveToMake.getFromPoint().getRow()][moveToMake.getFromPoint().getCol()].getPiece());
            currentBoard[moveToMake.getFromPoint().getRow()][moveToMake.getFromPoint().getCol()].removePiece();
            Color winner = checkStatus(currentBoard , player);
            if (winner != GAME_CONTINUES) {
                //System.out.println("Rollout: " + counter);
                return winner;
            }
            player = flipColor(player);
            //counter++;
        }
    }

    static ArrayList<Move> getAvailableMoves(Cell[][] board,Color player) {
        return Chessboard.getAllPossibleMoveOnBoard(board, player);
    }
    static Color checkStatus(Cell[][] board, Color player) {
        if(player == Color.BLUE && board[8][3].getPiece() != null && board[8][3].getPiece().getOwner().getColor() == Color.BLUE){
            return player;
        }
        if(player == Color.RED && board[0][3].getPiece() != null && board[0][3].getPiece().getOwner().getColor() == Color.RED){
            return player;
        }
        /*boolean opponentHasNoPiece = true;
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
        }*/
        return GAME_CONTINUES;
    }

    public static Color flipColor(Color color){
        if(color == Color.BLUE) {
            return Color.RED;
        }
        return Color.BLUE;
    }
}
