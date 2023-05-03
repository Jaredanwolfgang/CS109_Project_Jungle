package Server;

import model.ChessBoard.Move;
import model.Enum.PlayerColor;
import model.User.User;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class SpectatorThread extends Thread{
    private ServerThread serverThread;
    private ServerSocket serverSocket;
    private User player1;
    private User player2;
    private ArrayList<Move> allMoves = new ArrayList<>();
    private List<Socket> spectatorSockets = new ArrayList<>();
    private List<ObjectOutputStream> spectatorOutputs = new ArrayList<>();
    public SpectatorThread(ServerThread serverThread, ServerSocket serverSocket, User player1, User player2){
        this.serverThread = serverThread;
        this.serverSocket = serverSocket;
        this.player1 = player1;
        this.player2 = player2;
    }

    @Override
    public void run(){
        while(true){
            Socket spectatorSocket;
            try {
                spectatorSocket = serverSocket.accept();
            } catch (IOException ignored) {
                break;
            }
            spectatorSockets.add(spectatorSocket);
            try {
                spectatorOutputs.add(new ObjectOutputStream(spectatorSocket.getOutputStream()));
            } catch (IOException e) {
                System.out.println("Spectator: Error creating object output stream: " + e.getMessage());
                throw new RuntimeException(e);
            }

            try {
                spectatorOutputs.get(spectatorOutputs.size() - 1).writeObject(PlayerColor.GRAY);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            try {
                spectatorOutputs.get(spectatorOutputs.size() - 1).writeObject(player1);
                spectatorOutputs.get(spectatorOutputs.size() - 1).writeObject(player2);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            try {
                spectatorOutputs.get(spectatorOutputs.size() - 1).writeObject(allMoves);
            } catch (IOException e) {
                System.out.println("Spectator: Error sending move list to spectator: " + e.getMessage());
                throw new RuntimeException(e);
            }
        }
    }


    public void updateMove(Move move){
        allMoves.add(move);
        System.out.println("Spectator: Sending move to spectators");

        for(ObjectOutputStream spectatorOutput : spectatorOutputs){
            try {
                spectatorOutput.writeObject(move);
            } catch (IOException e) {
                System.out.println("Spectator: Error sending move to spectator: " + e.getMessage());
                throw new RuntimeException(e);
            }
        }

        System.out.println("Spectator: Move sent to spectators");
    }

    public void shutDown() {
        for (ObjectOutputStream spectatorOutput : spectatorOutputs) {
            try {
                spectatorOutput.close();
            } catch (IOException e) {
                System.out.println("Spectator: Error closing spectator output stream: " + e.getMessage());
            }
        }

        for (Socket spectatorSocket : spectatorSockets) {
            try {
                spectatorSocket.close();
            } catch (IOException e) {
                System.out.println("Spectator: Error closing spectator socket: " + e.getMessage());
            }
        }
    }
}
