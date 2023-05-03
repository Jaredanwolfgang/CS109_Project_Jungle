package Server;

import controller.GameController;
import model.ChessBoard.Move;
import model.Enum.GameMode;
import model.Enum.PlayerColor;
import model.Timer.Timer;
import model.User.User;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

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
    private User tempUser;
    private PlayerColor simulatedPlayerColor = PlayerColor.BLUE;
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
            gameController.setColorOfUser(playerColor);

            if(playerColor != PlayerColor.GRAY){
                if(gameController.getGameMode()!=GameMode.Online_PVP_Server){
                    gameController.setGameMode(GameMode.Online_PVP_Client);
                }

                outPut.writeObject(GameController.user1);
                outPut.flush();
                System.out.println("Client: Sent local user profile to server");

                User opponent = (User) inPut.readObject();
                GameController.user2 = opponent;
                System.out.println("Client: Received opponent profile from server: " + opponent.toString());

                try {
                    if(playerColor == PlayerColor.BLUE){
                        gameController.timer = new Timer(gameController,900);
                        gameController.timer.start();
                        this.runTimeBlue();
                    }else {
                        gameController.timer = new Timer(gameController,1000);
                        gameController.timer.start();
                        this.runTimeRed();
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

            }else{
                gameController.setGameMode(GameMode.Online_PVP_Spectator);

                tempUser = GameController.user1;

                GameController.user1 = (User) inPut.readObject();
                System.out.println("Client: Received user1 profile from server: " + GameController.user1.toString());
                GameController.user2 = (User) inPut.readObject();
                System.out.println("Client: Received user2 profile from server: " + GameController.user2.toString());

                gameController.timer = new Timer(gameController,1000);
                gameController.timer.start();

                ArrayList<Move> previousMoves = (ArrayList<Move>) inPut.readObject();
                for(Move move : previousMoves){
                    gameController.onPlayerClickChessPiece(move.getFromPoint(),simulatedPlayerColor);

                    try {
                        sleep(GameController.animationInterval);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                    if(move.isDoesCapture()){
                        gameController.onPlayerClickChessPiece(move.getToPoint(),simulatedPlayerColor);
                    }else{
                        gameController.onPlayerClickCell(move.getToPoint(),simulatedPlayerColor);
                    }

                    try {
                        sleep(GameController.animationInterval);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                    simulatedPlayerColor = simulatedPlayerColor == PlayerColor.BLUE ? PlayerColor.RED : PlayerColor.BLUE;
                }

                this.runTimeSpectator();

            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void runTimeSpectator() {
        System.out.println("Client: Running as spectator");
        while(true){
            try {
                moveFromServer = (Move) inPut.readObject();
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

            gameController.onPlayerClickChessPiece(moveFromServer.getFromPoint(),simulatedPlayerColor);

            try {
                sleep(GameController.animationInterval);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            if(moveFromServer.isDoesCapture()){
                gameController.onPlayerClickChessPiece(moveFromServer.getToPoint(),simulatedPlayerColor);
            }else{
                gameController.onPlayerClickCell(moveFromServer.getToPoint(),simulatedPlayerColor);
            }

            simulatedPlayerColor = simulatedPlayerColor == PlayerColor.BLUE ? PlayerColor.RED : PlayerColor.BLUE;

            try {
                waitForEndGameCall();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if(gameEnded){
                break;
            }
        }
        GameController.user1 = tempUser;

        this.shutDown();
    }

    private void runTimeRed() throws InterruptedException {
        System.out.println("Client: Running as red player");
        while(true){
            try {
                moveFromServer = (Move) inPut.readObject();
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

            gameController.onPlayerClickChessPiece(moveFromServer.getFromPoint(),PlayerColor.BLUE);

            sleep(GameController.animationInterval);

            if(moveFromServer.isDoesCapture()){
                gameController.onPlayerClickChessPiece(moveFromServer.getToPoint(),PlayerColor.BLUE);
            }else{
                gameController.onPlayerClickCell(moveFromServer.getToPoint(),PlayerColor.BLUE);
            }

            waitForEndGameCall();
            if(gameEnded){
                break;
            }

            gameController.timer.setInterval(900);

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

            gameController.timer.setInterval(1000);
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

            gameController.timer.setInterval(1000);

            try {
                moveFromServer = (Move) inPut.readObject();
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            gameController.onPlayerClickChessPiece(moveFromServer.getFromPoint(),PlayerColor.RED);

            sleep(GameController.animationInterval);

            if(moveFromServer.isDoesCapture()){
                gameController.onPlayerClickChessPiece(moveFromServer.getToPoint(),PlayerColor.RED);
            }else{
                gameController.onPlayerClickCell(moveFromServer.getToPoint(),PlayerColor.RED);
            }

            waitForEndGameCall();
            if(gameEnded){
                break;
            }

            gameController.timer.setInterval(900);
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
