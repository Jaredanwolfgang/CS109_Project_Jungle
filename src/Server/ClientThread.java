package Server;

import controller.GameController;
import model.ChessBoard.Move;
import model.Enum.PlayerColor;

import java.io.*;
import java.net.Socket;

public class ClientThread extends Thread{
    private Socket socket;
    private GameController gameController;
    private ObjectOutputStream outPut;
    private ObjectInputStream inPut;
    private boolean isMoveAvailable = false;
    private volatile boolean gameEnded = false;
    private Move moveFromCurrentPlayer;
    private Move moveFromServer;
    private boolean ready = false;
    public ClientThread(Socket socket , GameController gameController){
        this.socket = socket;
        this.gameController = gameController;
        try {
            outPut = new ObjectOutputStream(socket.getOutputStream());
            inPut = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            throw new IllegalArgumentException("Client: Error obtaining object stream: " + e.getMessage());
        }
    }

    @Override
    public void run() {
        try {
            PlayerColor playerColor = (PlayerColor) inPut.readObject();
            System.out.println("Client: Player color received");
            gameController.setPlayerColor(playerColor);
            try {
                if(playerColor == PlayerColor.BLUE){
                    this.runTimeBlue();
                }else {
                    this.runTimeRed();
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void runTimeRed() throws InterruptedException {
        System.out.println("Client: Running as red player");
        while(true){
            try {
                moveFromServer = (Move) inPut.readObject();
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            gameController.onPlayerClickChessPiece(moveFromServer.getFromPoint());
            if(moveFromServer.isDoesCapture()){
                gameController.onPlayerClickChessPiece(moveFromServer.getToPoint());
            }else{
                gameController.onPlayerClickCell(moveFromServer.getToPoint());
            }

            waitForEndGameCall();
            if(gameEnded){
                break;
            }

            waitForPlayerMove();

            try {
                outPut.writeObject(moveFromCurrentPlayer);
                outPut.flush();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            waitForEndGameCall();
            if(gameEnded){
                break;
            }
        }
        this.shutDown();
    }

    private void runTimeBlue() throws InterruptedException {
        System.out.println("Client: Running as blue player");
        while(true){
            waitForPlayerMove();

            try {
                outPut.writeObject(moveFromCurrentPlayer);
                outPut.flush();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            waitForEndGameCall();
            if(gameEnded){
                break;
            }

            try {
                moveFromServer = (Move) inPut.readObject();
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            gameController.onPlayerClickChessPiece(moveFromServer.getFromPoint());
            if(moveFromServer.isDoesCapture()){
                gameController.onPlayerClickChessPiece(moveFromServer.getToPoint());
            }else{
                gameController.onPlayerClickCell(moveFromServer.getToPoint());
            }

            waitForEndGameCall();
            if(gameEnded){
                break;
            }
        }
        this.shutDown();
    }
    public void shutDown(){
        try {
            outPut.close();
            inPut.close();
            socket.close();
            System.out.println("Client: Client shutdown successfully");
        } catch (IOException e) {
            System.out.println("Client: Error shutting down client: " + e.getMessage());
        }
    }
    public synchronized void waitForPlayerMove() throws InterruptedException {
        while(!isMoveAvailable){
            System.out.println("Client: Waiting for player move");
            wait();
        }
        System.out.println("Client: Waiting terminated");
        isMoveAvailable = false;
    }

    public synchronized void makeMove(Move move) {
        isMoveAvailable = true;
        moveFromCurrentPlayer = move;
        notify();
        System.out.println("Client: Move made by player received");
    }

    public synchronized void setEndGame(boolean gameEnded){
        this.gameEnded = gameEnded;
        ready = true;
        notify();
    }

    public synchronized void waitForEndGameCall() throws InterruptedException {
        while(!ready){
            wait();
        }
        ready = false;
    }
}
