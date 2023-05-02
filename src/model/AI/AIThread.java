package model.AI;

import controller.GameController;
import model.ChessBoard.Cell;
import model.ChessBoard.Move;
import model.Enum.AIDifficulty;
import model.Enum.PlayerColor;

public class AIThread extends Thread{
    private GameController gameController;
    private Cell[][] grid;
    private PlayerColor playerColor;
    public AIThread(GameController gameController, Cell[][] grid, PlayerColor playerColor){
        this.gameController = gameController;
        this.grid = grid;
        this.playerColor = playerColor;
    }
    @Override
    public void run(){
        Move AIMove;
        if(GameController.aiDifficulty == AIDifficulty.EASY){
            AIMove = AI_Easy.findBestOneMove(grid, playerColor.getColor());
        }else if(GameController.aiDifficulty == AIDifficulty.MEDIUM){
            AIMove = AI_Medium.findBestOneMove(grid, playerColor.getColor());
        }else{
            AIMove = AI_Hard.findBestOneMove(grid, playerColor.getColor());
        }

        gameController.onPlayerClickChessPiece(AIMove.getFromPoint(), playerColor);

        try {
            Thread.sleep(GameController.animationInterval);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        if(AIMove.isDoesCapture()){
            gameController.onPlayerClickChessPiece(AIMove.getToPoint(), playerColor);
        }else{
            gameController.onPlayerClickCell(AIMove.getToPoint(), playerColor);
        }

        try {
            Thread.sleep(GameController.animationInterval);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
