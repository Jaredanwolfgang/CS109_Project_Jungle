package Server;

import model.ChessBoard.Move;
import model.Enum.PlayerColor;
import model.User.User;
import view.Dialog.WaitingDialog;
import view.Frame.ChessGameFrame;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ServerThread extends Thread{
    private ServerSocket serverSocket;
    private User player1;
    private User player2;
    private Socket player1Socket;
    private Socket player2Socket;
    private ObjectOutputStream player1Output;
    private ObjectOutputStream player2Output;
    private ObjectInputStream player1Input;
    private ObjectInputStream player2Input;
    private boolean gameEnded = false;
    private boolean ready = false;
    private SpectatorThread spectatorThread;

    ArrayList<Move> allMoves = new ArrayList<>();
    public ServerThread() {
    }

    @Override
    public void run() {
        try{
            serverSocket = new ServerSocket(1234);
            System.out.println("Server: Waiting for player1 to connect......");
            player1Socket = serverSocket.accept();
            System.out.println("Server: Player1 connected");
            player1Output = new ObjectOutputStream(player1Socket.getOutputStream());
            player1Input = new ObjectInputStream(player1Socket.getInputStream());
            System.out.println("Server: Waiting for player2 to connect......");

            WaitingDialog waitingDialog = new WaitingDialog();
            ChessGameFrame.enabled = false;

            player2Socket = serverSocket.accept();
            System.out.println("Server: Player2 connected");

            waitingDialog.dispose();
            ChessGameFrame.enabled = true;

            player2Output = new ObjectOutputStream(player2Socket.getOutputStream());
            player2Input = new ObjectInputStream(player2Socket.getInputStream());
        }catch (IOException e) {
            throw new IllegalArgumentException("Server: Error connecting to clients: " + e.getMessage());
        }

        System.out.println("Server: All set, starting game");
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        this.startGame();
    }

    private void startGame() {


        try{
            player1Output.writeObject(PlayerColor.BLUE);
            player2Output.writeObject(PlayerColor.RED);
            player1Output.flush();
            player2Output.flush();
        } catch (IOException e) {
            System.out.println("Server: Error distributing player colors: " + e.getMessage());
            return;
        }
        System.out.println("Server: Player colors distributed");

        try {
            player1 = (User) player1Input.readObject();
            System.out.println("Server: Player1 profiles received");
            player2 = (User) player2Input.readObject();
            System.out.println("Server: Player2 profiles received");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Server: Error receiving player profiles: " + e.getMessage());
            return;
        }

        try {
            player1Output.writeObject(player2);
            player2Output.writeObject(player1);
            player1Output.flush();
            player2Output.flush();
        } catch (IOException e) {
            System.out.println("Server: Error distributing player profiles: " + e.getMessage());
            return;
        }

        System.out.println("Server: Player profiles distributed");

        this.spectatorThread = new SpectatorThread(this, serverSocket, player1, player2);
        this.spectatorThread.start();

        this.runTime();
    }

    private synchronized void runTime() {
        Move move;
        while(true) {
            try{
                System.out.println("Server: Waiting for player1 move......");
                move = (Move) player1Input.readObject();
                System.out.println("Server: Player1 move received");
                player2Output.writeObject(move);
                player2Output.flush();
                System.out.println("Server: Player1 move sent to player2");
                spectatorThread.updateMove(move);
                System.out.println("Server: Move added to spectator thread");

                waitForEndGameCall();
                if(gameEnded){
                    break;
                }

                System.out.println("Server: Waiting for player2 move......");
                move = (Move) player2Input.readObject();
                System.out.println("Server: Player2 move received");
                player1Output.writeObject(move);
                player1Output.flush();
                System.out.println("Server: Player2 move sent to player1");
                spectatorThread.updateMove(move);
                System.out.println("Server: Move added to spectator thread");

                waitForEndGameCall();
                if(gameEnded){
                    break;
                }
            } catch (IOException | ClassNotFoundException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println("Server: Game ended signal received");
        this.shutdown();
    }

    private void shutdown() {
        spectatorThread.shutDown();
        try{
            player1Output.close();
            player2Output.close();
            player1Input.close();
            player2Input.close();
            player1Socket.close();
            player2Socket.close();
            serverSocket.close();
            System.out.println("Server: Server shutdown successfully");
        } catch (IOException e) {
            System.out.println("Server: Error shutting down server: " + e.getMessage());
        }
    }
    public synchronized void setEndGame(boolean gameEnded){
        this.gameEnded = gameEnded;
        ready = true;
        notify();
    }

    public synchronized void waitForEndGameCall() throws InterruptedException {
        while(!ready){
            System.out.println("Server: Waiting for end game call");
            wait();
        }
        System.out.println("Server: End game call received");
        ready = false;
    }

    public User getPlayer1() {
        return player1;
    }

    public User getPlayer2() {
        return player2;
    }

    public ArrayList<Move> getAllMoves() {
        return allMoves;
    }
}