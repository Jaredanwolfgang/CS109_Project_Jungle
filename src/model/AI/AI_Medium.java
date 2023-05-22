package model.AI;

import model.ChessBoard.Cell;
import model.ChessBoard.Chessboard;
import model.ChessBoard.ChessboardPoint;
import model.ChessBoard.Move;
import model.Enum.Category;


import java.awt.*;
import java.util.*;


public class AI_Medium {
    public static Move findBestOneMove(Cell[][] board, Color player) {
        MoveGraph moveGraph = new MoveGraph(board, player);
        int value = alphaBeta(board, player, 4, moveGraph.startMoveNode, true, Integer.MIN_VALUE, Integer.MAX_VALUE);//从startMoveMode开始遍历，初始化了startMoveMode的children
        System.out.println("value:"+ value);
        Collections.sort(moveGraph.startMoveNode.children);
        for (int i = 0; i < moveGraph.startMoveNode.children.size(); i++) {
            System.out.println(moveGraph.startMoveNode.children.get(i).move.toString() + " " + moveGraph.startMoveNode.children.get(i).value);
        }
        return moveGraph.startMoveNode.children.get(0).move;
    }

    public static int alphaBeta(Cell[][] board, Color player, int depth, MoveNode moveNode, boolean maximizingPlayer, int alpha, int beta) {
        System.out.println("------------------------------------------------------");
        System.out.println("This is depth " + depth);
        //判断是不是到了底层，判断是不是棋局已经胜利
        if (depth == 0 || GameSimulator.checkStatus(board, player) != GameSimulator.GAME_CONTINUES) {
            moveNode.value = evaluateGameState(board, player, moveNode);
            System.out.println("At depth 0, the value is" + moveNode.value);
            // Evaluate the heuristic value of the game state
            return moveNode.value;
        }
        moveNode.alpha = alpha;
        moveNode.beta = beta;
        //判断是否是max节点
        if (maximizingPlayer) {
            System.out.println("Maximizer" + depth + ": MoveNode before now has value " + moveNode.value);
            //max节点
            int bestValue = moveNode.value;//获取到该节点本来的大小
            //遍历节点的每一个子节点
            for (MoveNode moveChildrenNode : moveNode.children) {
                //克隆棋盘
                Cell[][] cloneBoard = Chessboard.cloneBoard(board);
                Move moveToMake = moveChildrenNode.move;
                if (moveToMake.isDoesCapture()) {
                    cloneBoard[moveToMake.getToPoint().getRow()][moveToMake.getToPoint().getCol()].removePiece();
                }
                cloneBoard[moveToMake.getToPoint().getRow()][moveToMake.getToPoint().getCol()].setPiece(cloneBoard[moveToMake.getFromPoint().getRow()][moveToMake.getFromPoint().getCol()].getPiece());
                cloneBoard[moveToMake.getFromPoint().getRow()][moveToMake.getFromPoint().getCol()].removePiece();

                //基于克隆后的棋盘对子节点添加子节点moves
                MoveGraph.addAllPossibleMoves(cloneBoard, player, moveChildrenNode);

                //获得子节点的value信息
                int value = alphaBeta(cloneBoard, player, depth - 1, moveChildrenNode, false, moveNode.alpha, moveNode.beta);
                bestValue = Math.max(bestValue, value);//如果有更大的value就换掉bestvalue

                moveNode.alpha = Math.max(moveNode.alpha, bestValue);
                if (moveNode.beta <= moveNode.alpha) {
                    break; // Beta cutoff
                }
            }
            moveNode.value = bestValue;
            System.out.println("Maximizer" + depth + ": MoveNode after now has value " + moveNode.value);
            return bestValue;
        } else {
            System.out.println("Minimizer" + depth + ": MoveNode before now has value " + moveNode.value);
            int bestValue = Integer.MAX_VALUE;
            for (MoveNode moveChildrenNode : moveNode.children) {
                //克隆棋盘
                Cell[][] cloneBoard = Chessboard.cloneBoard(board);
                Move moveToMake = moveChildrenNode.move;
                if (moveToMake.isDoesCapture()) {
                    cloneBoard[moveToMake.getToPoint().getRow()][moveToMake.getToPoint().getCol()].removePiece();
                }
                cloneBoard[moveToMake.getToPoint().getRow()][moveToMake.getToPoint().getCol()].setPiece(cloneBoard[moveToMake.getFromPoint().getRow()][moveToMake.getFromPoint().getCol()].getPiece());
                cloneBoard[moveToMake.getFromPoint().getRow()][moveToMake.getFromPoint().getCol()].removePiece();

                //基于克隆后的棋盘对子节点添加子节点moves
                MoveGraph.addAllPossibleMoves(cloneBoard, player, moveChildrenNode);

                //获得子节点的value信息
                int value = alphaBeta(cloneBoard, player, depth - 1, moveChildrenNode, true, moveNode.alpha, moveNode.beta);
                bestValue = Math.min(bestValue, value);//如果有更大的value就换掉bestvalue

                moveNode.beta = Math.max(moveNode.beta, bestValue);
                if (moveNode.beta <= moveNode.alpha) {
                    break; // Beta cutoff
                }
            }
            moveNode.value = bestValue;
            System.out.println("Minimizer" + depth + ": MoveNode after now has value " + moveNode.value);
            return bestValue;
        }
    }

    public static int evaluateGameState(Cell[][] board, Color player, MoveNode moveNode) {
        //Evaluate the preys
        if (moveNode.move.isDoesCapture()) {
            moveNode.value += Math.pow(moveNode.move.getCapturedPiece().getRank(), 2);
        }
        //Evaluate win
        if (player == Color.BLUE) {
            moveNode.value += Chessboard.getDistance(moveNode.move.getFromPoint(), new ChessboardPoint(8, 3)) - Chessboard.getDistance(moveNode.move.getToPoint(), new ChessboardPoint(8, 3));
        } else {
            moveNode.value += Chessboard.getDistance(moveNode.move.getFromPoint(), new ChessboardPoint(0, 3)) - Chessboard.getDistance(moveNode.move.getToPoint(), new ChessboardPoint(0, 3));
        }
        //Evaluate the circumstance when you can get to den.(Caution: this neglects the circumstances where you may be eaten by others)
        if (board[moveNode.move.getToPoint().getRow()][moveNode.move.getToPoint().getCol()].isDen() && board[moveNode.move.getToPoint().getRow()][moveNode.move.getToPoint().getCol()].getOwner() != moveNode.move.getMovingPiece().getOwner()) {
            moveNode.value = Integer.MAX_VALUE;
        }
        //Evaluate when you tried to get to the trap
        if (board[moveNode.move.getToPoint().getRow()][moveNode.move.getToPoint().getCol()].isTrap() && board[moveNode.move.getToPoint().getRow()][moveNode.move.getToPoint().getCol()].getOwner() != moveNode.move.getMovingPiece().getOwner()) {
            boolean hasEnemyPiece = false;
            int[] moveX = {0, 0, 1, -1};
            int[] moveY = {1, -1, 0, 0};
            for (int i = 0; i < 4; i++) {
                int x = moveNode.move.getToPoint().getRow() + moveX[i];
                int y = moveNode.move.getToPoint().getCol() + moveY[i];
                if (x >= 0 && x < 9 && y >= 0 && y < 7) {
                    if (board[x][y].getPiece() != null && board[x][y].getPiece().getOwner() != moveNode.move.getMovingPiece().getOwner()) {
                        hasEnemyPiece = true;
                    }
                }
                if (hasEnemyPiece) {
                    moveNode.value = Integer.MIN_VALUE;
                } else {
                    moveNode.value = Integer.MAX_VALUE;
                }
            }
        }
        //Evaluate special circumstances
        switch (moveNode.move.getMovingPiece().getCategory()) {
            case LION:
            case TIGER:
                    moveNode.value += Chessboard.getDistance(moveNode.move.getFromPoint(), moveNode.move.getToPoint()) - 1;
                    break;
            case CAT:
            case DOG:
                if (moveNode.move.getMovingPiece().getOwner().getColor() == Color.BLUE) {
                    moveNode.value += (Chessboard.getDistance(moveNode.move.getFromPoint(), new ChessboardPoint(0, 2))
                            + Chessboard.getDistance(moveNode.move.getFromPoint(), new ChessboardPoint(0, 4))
                            + Chessboard.getDistance(moveNode.move.getFromPoint(), new ChessboardPoint(1, 2)))
                            - (Chessboard.getDistance(moveNode.move.getToPoint(), new ChessboardPoint(0, 2))
                            + Chessboard.getDistance(moveNode.move.getToPoint(), new ChessboardPoint(0, 4))
                            + Chessboard.getDistance(moveNode.move.getToPoint(), new ChessboardPoint(1, 2)));
                } else {
                    moveNode.value += (Chessboard.getDistance(moveNode.move.getFromPoint(), new ChessboardPoint(8, 2))
                            + Chessboard.getDistance(moveNode.move.getFromPoint(), new ChessboardPoint(8, 4))
                            + Chessboard.getDistance(moveNode.move.getFromPoint(), new ChessboardPoint(7, 2)))
                            - (Chessboard.getDistance(moveNode.move.getToPoint(), new ChessboardPoint(8, 2))
                            + Chessboard.getDistance(moveNode.move.getToPoint(), new ChessboardPoint(8, 4))
                            + Chessboard.getDistance(moveNode.move.getToPoint(), new ChessboardPoint(7, 2)));
                }
                break;
            case RAT:
                for (int i = 0; i < 9; i++) {
                    for (int j = 0; j < 7; j++) {
                        if (board[i][j].getPiece() != null && board[i][j].getPiece().getCategory() == Category.ELEPHANT) {
                            moveNode.value += 16 - Chessboard.getDistance(moveNode.move.getFromPoint(), new ChessboardPoint(i, j));
                        }
                    }
                }
        }
        return moveNode.value;
    }

}

class MoveGraph {
    MoveNode startMoveNode;//graphs是一个二维数组，每个元素是一个ArrayList<Integer>
    Cell[][] chessboard;
    Color player;

    public MoveGraph(Cell[][] board, Color player) {
        this.chessboard = board;
        this.player = player;
        startMoveNode = new MoveNode(player, null, null, board);
        addAllPossibleMoves(board, player, startMoveNode);
    }

    public static void addAllPossibleMoves(Cell[][] chessboard, Color player, MoveNode parent) {
        ArrayList<Move> possibleMoves = Chessboard.getAllPossibleMoveOnBoard(chessboard, player);
        ArrayList<MoveNode> newMoveModes = new ArrayList<>();
        for (Move possibleMove : possibleMoves) {
            newMoveModes.add(new MoveNode(player, parent, possibleMove, chessboard));
        }
        parent.children = newMoveModes;
    }
}

class MoveNode implements Comparable<MoveNode> {
    MoveNode parent;
    ArrayList<MoveNode> children;//parent 和 children 设置了链表结构，方便添加和删除步骤
    int alpha, beta, value;
    Cell[][] board;
    Move move;
    Color player;
    private static Random random = new Random();

    public MoveNode(Color player, MoveNode parent, Move move, Cell[][] board) {
        this.player = player;
        this.parent = parent;
        this.move = move;
        this.board = board;
        this.children = new ArrayList<>();
        init();
    }

    public void init() {
        value = 0;
        alpha = Integer.MIN_VALUE;
        beta = Integer.MAX_VALUE;
    }

    @Override
    public int compareTo(MoveNode o) {
        if (this.value != o.value) {
            return Integer.compare(this.value, o.value);
        } else {
            // Randomly sort when values are the same
            return random.nextInt(3) - 1;
        }
    }
}

class GameSimulator {
    static final Color GAME_CONTINUES = null;

    static Color checkStatus(Cell[][] board, Color player) {
        if (player == Color.BLUE && board[8][3].getPiece() != null && board[8][3].getPiece().getOwner().getColor() == Color.BLUE) {
            return player;
        }
        if (player == Color.RED && board[0][3].getPiece() != null && board[0][3].getPiece().getOwner().getColor() == Color.RED) {
            return player;
        }
        boolean opponentHasNoPiece = true;
        Color opponent = flipColor(player);
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 7; j++) {
                if (board[i][j].getPiece() != null && board[i][j].getPiece().getOwner().getColor() == opponent) {
                    opponentHasNoPiece = false;
                    break;
                }
            }
        }
        if (opponentHasNoPiece) {
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

