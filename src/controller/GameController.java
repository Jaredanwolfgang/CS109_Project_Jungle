package controller;

import Server.ClientThread;
import Server.ServerThread;
import listener.GameListener;
import model.AI.AIThread;
import model.ChessBoard.Chessboard;
import model.ChessBoard.ChessboardPoint;
import model.ChessBoard.Move;
import model.ChessPieces.*;
import model.Enum.AIDifficulty;
import model.Enum.GameMode;
import model.Enum.PlayerColor;
import model.Enum.PlayerType;
import model.Timer.Timer;
import model.User.User;
import view.ChessComponent.ChessComponent;
import view.ChessboardComponent;
import view.Dialog.FailDialog;
import view.Frame.ChessGameFrame;
import view.Frame.Frame;
import view.UI.ChessClick;

import javax.swing.*;
import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Comparator;

public class GameController implements GameListener {
    public static final long animationInterval = 400;
    private static final String USER_FILE_PATH = "Information\\users.txt";
    private final Chessboard model;
    private final Frame view;
    private ServerThread server;
    private ClientThread client;
    public Timer timer;

    //Color of User is used for distinguishing different modes and
    //locking the chessboard to prevent user from moving chess invalidly.
    private PlayerColor colorOfUser;

    //Current User will change every round.
    private PlayerColor currentPlayer;
    public static User user1;
    public static User user2;
    public static GameMode gameMode;

    // Record all moves on the board.
    private final ArrayList<Move> allMovesOnBoard;
    private final ArrayList<User> allUsers;

    // Record whether there is a selected piece before and where
    private ChessboardPoint selectedPoint;

    //If this variable is true, it means the controller is doing playback.
    private boolean onAutoPlayback;
    public static AIDifficulty aiDifficulty;
    public static int turnCount;

    public GameController(Frame view, Chessboard model) {
        this.view = view;
        this.model = model;
        this.currentPlayer = PlayerColor.BLUE;
        this.allMovesOnBoard = new ArrayList<>();
        this.allUsers = new ArrayList<>();
        this.selectedPoint = null;
        this.onAutoPlayback = false;
        aiDifficulty = AIDifficulty.EASY;
        turnCount = 1;
        this.readUsers();

        view.registerFrame(this);
        view.getChessGameFrame().getChessboardComponent().initiateChessComponent(model);
        view.getChessGameFrame().getChessboardComponent().registerGameController(this);
        view.getChessGameFrame().getChessboardComponent().repaint();
    }

    //This method read all users from file when game controller created.
    private void readUsers() {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(USER_FILE_PATH));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String username = parts[0];
                String password = parts[1];
                int wins = Integer.parseInt(parts[2]);
                int losses = Integer.parseInt(parts[3]);
                double score = Double.parseDouble(parts[4]);
                String playerType = parts[5];
                User user = new User();
                user.setUsername(username);
                user.setPassword(password);
                user.setScore(score);
                user.setWins(wins);
                user.setLosses(losses);
                if(playerType.equals("HUMAN")){
                    user.setPlayerType(PlayerType.HUMAN);
                }else if(playerType.equals("AI")){
                    user.setPlayerType(PlayerType.AI);
                }
                allUsers.add(user);
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            return;
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                System.err.println("Error closing file: " + e.getMessage());
            }
        }
        allUsers.sort(Comparator.comparing(User::getScore));
        System.out.println("Read users successfully!");
    }

    private void writeUsers(){
        allUsers.sort(Comparator.comparing(User::getScore));
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(USER_FILE_PATH));
            for (User user : allUsers) {
                writer.write(user.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing file: " + e.getMessage());
            return;
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                System.err.println("Error closing file: " + e.getMessage());
            }
        }
        System.out.println("Write users successfully!");
    }

    // after a valid move swap the player
    private void swapColor() {
        currentPlayer = currentPlayer == PlayerColor.BLUE ? PlayerColor.RED : PlayerColor.BLUE;
    }

    private void swapUser() {
        colorOfUser = colorOfUser == PlayerColor.BLUE ? PlayerColor.RED : PlayerColor.BLUE;
    }

    //Judge if there is a winner in two ways.
    //First: One player's piece enters the other player's den.
    //Second: After a capture, one player has no piece left.
    /** Jerry: I have changed the win() method to public, because I need to implement the method in frame.*/
    public PlayerColor win() {
        PlayerColor noPieceLeft = model.noPieceLeft();
        if((model.getGrid()[0][3].getPiece() != null && model.getGrid()[0][3].getPiece().getOwner() == PlayerColor.RED) || noPieceLeft == PlayerColor.BLUE){
            return PlayerColor.RED;
        }
        if((model.getGrid()[8][3].getPiece() != null && model.getGrid()[8][3].getPiece().getOwner() == PlayerColor.BLUE) || noPieceLeft == PlayerColor.RED){
            return PlayerColor.BLUE;
        }
        return null;
    }

    private void updateUserScore(User winner, User loser){
        winner.setWins(winner.getWins() + 1);
        loser.setLosses(loser.getLosses() + 1);
        double winnerExpectedScore = 1 / (1 + Math.pow(10, (loser.getScore() - winner.getScore()) / 400));
        double loserExpectedScore = 1 / (1 + Math.pow(10, (winner.getScore() - loser.getScore()) / 400));
        if(winner.getPlayerType() != PlayerType.AI) {
            winner.setScore(winner.getScore() + 32 * (1 - winnerExpectedScore));
        }
        if(loser.getPlayerType() != PlayerType.AI) {
            loser.setScore(loser.getScore() + 32 * (0 - loserExpectedScore));
        }
        this.writeUsers();
    }

    public void gatAIMove(AIDifficulty aiDifficulty) {
        //Do not use this function in view.
        //This is only a reusable method to create AI.
        Thread AI = new AIThread(this, model.getGrid(), currentPlayer,aiDifficulty);
        AI.start();
    }

    // click an empty cell
    @Override
    public void onPlayerClickCell(ChessboardPoint point, PlayerColor playerColor) {
        if(playerColor != currentPlayer){
            System.out.println("Not your turn!");
            // Here is code for GUI to tell user that it's not his turn
            //new FailDialog("Not your turn!",view.getChessGameFrame());
            return;
        }

        if (selectedPoint != null) {
            try{
                //To avoid Null pointer
                ArrayList<Move> Moves = model.getChessPieceAt(new ChessboardPoint(selectedPoint.getRow(),selectedPoint.getCol())).getAvailableMoves(new ChessboardPoint(selectedPoint.getRow(),selectedPoint.getCol()),model.getGrid());
                //Try to move the selected piece to the clicked cell.
                System.out.printf("Try to move %s at point (%d , %d) to point (%d , %d)\n",model.getChessPieceAt(selectedPoint).getName(),selectedPoint.getRow(),selectedPoint.getCol(),point.getRow(),point.getCol());

                Move moveToMake = model.moveChessPiece(selectedPoint,point);

                //Remove possible moves and deselect the previous point
                view.removeAllPossibleMoves(Moves);

                //Here is the code for GUI to repaint the board.(One piece moved)
                view.move(point,selectedPoint);
                view.getChessGameFrame().getChessboardComponent().repaint();

                if(!onAutoPlayback){
                    allMovesOnBoard.add(moveToMake);
                }
                if((gameMode == GameMode.Online_PVP_Server || gameMode == GameMode.Online_PVP_Client) && currentPlayer == colorOfUser && !onAutoPlayback){
                    this.client.makeMove(moveToMake);
                }
                selectedPoint = null;
                if(gameMode == GameMode.Local_PVP){
                    this.swapUser();
                }
                swapColor();

                timer.reset();
            }catch (IllegalArgumentException e){
                //Print error message.
                System.out.println(e.getMessage());
                //Here should be code for GUI to tell user that the move is invalid
                //new FailDialog(e.getMessage(),view.getChessGameFrame());
                return;
            }

            if(win() != null){
                timer.shutdown();
                if(gameMode == GameMode.PVE || gameMode == GameMode.Local_PVP){
                    if(win() == PlayerColor.BLUE){
                        updateUserScore(user1, user2);
                    }else{
                        updateUserScore(user2, user1);
                    }
                }
                if(gameMode == GameMode.Online_PVP_Client || gameMode == GameMode.Online_PVP_Server || gameMode == GameMode.Online_PVP_Spectator){
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    client.setEndGame(true);
                    if(gameMode == GameMode.Online_PVP_Server){
                        server.setEndGame(true);
                    }
                }

                /* Here should be code for GUI to display game over message to user */
                view.getChessGameFrame().addWinLabel();
                return;
            }else{
                /* Here should be code for GUI to swap color (color of which player should perform a move) */
                /* Here should be code for GUI to update turn count */
                turnCount++;
                view.updateTurnAccount(turnCount);

                if((gameMode == GameMode.Online_PVP_Client || gameMode == GameMode.Online_PVP_Server || gameMode == GameMode.Online_PVP_Spectator) && !onAutoPlayback) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    client.setEndGame(false);
                    if (gameMode == GameMode.Online_PVP_Server) {
                        server.setEndGame(false);
                    }
                }
            }
            if(gameMode == GameMode.PVE && currentPlayer != colorOfUser && !onAutoPlayback){
                this.gatAIMove(GameController.aiDifficulty);
            }
        }

        if(selectedPoint != null){
            System.out.printf("Selected piece is %s at point (%d , %d)\n",model.getChessPieceAt(selectedPoint).getName(),selectedPoint.getRow(),selectedPoint.getCol());
        }else{
            System.out.println("No point is selected");
        }

        System.out.println("Turn: " + turnCount);
        Chessboard.printChessBoard(model.getGrid());
    }

    // click a cell with a chess
    @Override
    public void onPlayerClickChessPiece(ChessboardPoint point, PlayerColor playerColor) {
        if(playerColor != currentPlayer){
            // Here should be code for GUI to tell user that it's not his turn
            System.out.println("Not your turn!");
            //new FailDialog("Not your turn!",view.getChessGameFrame());
            return;
        }
        ChessboardComponent chessboardComponent = view.getChessGameFrame().getChessboardComponent();
        ChessComponent chessComponent = (ChessComponent) chessboardComponent.getGridComponentAt(point).getComponents()[0];

        if (selectedPoint == null) {
            if (model.getChessPieceOwner(point) == currentPlayer) {
                new ChessClick();

                //If the clicked piece is the current player's piece, select it.
                chessComponent.setSelected(true);
                selectedPoint = point;
                // Here is the code for GUI to show all available moves for the selected piece.
                view.showAllPossibleMoves(model.getChessPieceAt(new ChessboardPoint(selectedPoint.getRow(),selectedPoint.getCol())).getAvailableMoves(new ChessboardPoint(selectedPoint.getRow(),selectedPoint.getCol()),model.getGrid()));
                view.getChessGameFrame().getChessboardComponent().repaint();
            }
        }else{
            ChessComponent chessComponentOrigin = (ChessComponent)chessboardComponent.getGridComponentAt(selectedPoint).getComponents()[0];
            if (selectedPoint.equals(point)) {
                new ChessClick();

                //If the clicked piece is the selected piece, deselect it.
                chessComponent.setSelected(false);
                //Here is the code for GUI to remove all possible moves of the previous selected piece.
                view.removeAllPossibleMoves(model.getChessPieceAt(new ChessboardPoint(selectedPoint.getRow(),selectedPoint.getCol())).getAvailableMoves(new ChessboardPoint(selectedPoint.getRow(),selectedPoint.getCol()),model.getGrid()));
                view.getChessGameFrame().getChessboardComponent().repaint();
                selectedPoint = null;
            }else{
                if(model.getChessPieceOwner(point) == currentPlayer){
                    new ChessClick();

                    //If the clicked piece is the current player's piece, select it.
                    chessComponent.setSelected(true);
                    chessComponentOrigin.setSelected(false);

                    //Here is the code for GUI to remove all possible moves of the previous selected piece.
                    view.removeAllPossibleMoves(model.getChessPieceAt(new ChessboardPoint(selectedPoint.getRow(),selectedPoint.getCol())).getAvailableMoves(new ChessboardPoint(selectedPoint.getRow(),selectedPoint.getCol()),model.getGrid()));
                    selectedPoint = point;
                    //Here is the code for GUI to show all available moves for the selected piece
                    view.showAllPossibleMoves(model.getChessPieceAt(new ChessboardPoint(selectedPoint.getRow(),selectedPoint.getCol())).getAvailableMoves(new ChessboardPoint(selectedPoint.getRow(),selectedPoint.getCol()),model.getGrid()));
                    view.getChessGameFrame().getChessboardComponent().repaint();
                }else{
                    //Try to capture the clicked piece with the selected piece.
                    try{
                        ArrayList<Move> Moves = model.getChessPieceAt(new ChessboardPoint(selectedPoint.getRow(),selectedPoint.getCol())).getAvailableMoves(new ChessboardPoint(selectedPoint.getRow(),selectedPoint.getCol()),model.getGrid());
                        Move moveToMake = model.captureChessPiece(selectedPoint,point);
                        //If the capture is invalid, the try sentence ends here.
                        //delete all possible moves shown on board
                        view.removeAllPossibleMoves(Moves);

                        //Here is the code for GUI to repaint the board.(One piece captured)
                        view.eat(point,selectedPoint);
                        view.getChessGameFrame().getChessboardComponent().repaint();

                        if(!onAutoPlayback) {
                            allMovesOnBoard.add(moveToMake);
                        }
                        if((gameMode == GameMode.Online_PVP_Server || gameMode == GameMode.Online_PVP_Client) && currentPlayer == colorOfUser && !onAutoPlayback){
                            this.client.makeMove(moveToMake);
                        }
                        selectedPoint = null;
                        if(gameMode == GameMode.Local_PVP){
                            this.swapUser();
                        }
                        swapColor();

                        timer.reset();
                    }catch (IllegalArgumentException e){
                        //Print error message.
                        System.out.println(e.getMessage());
                        //NOT NECESSARY: Here should be code for GUI to tell user that the move is invalid
                        //new FailDialog(e.getMessage(),view.getChessGameFrame());
                        return;
                    }

                    if(win() != null){
                        timer.shutdown();
                        if(gameMode == GameMode.PVE || gameMode == GameMode.Local_PVP){
                            if(win() == PlayerColor.BLUE){
                                updateUserScore(user1, user2);
                            }else{
                                updateUserScore(user2, user1);
                            }
                        }
                        if(gameMode == GameMode.Online_PVP_Client || gameMode == GameMode.Online_PVP_Server || gameMode == GameMode.Online_PVP_Spectator){
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                            client.setEndGame(true);
                            if(gameMode == GameMode.Online_PVP_Server){
                                server.setEndGame(true);
                            }
                        }

                        //Here should be code for GUI to display game over message to user */
                        view.getChessGameFrame().addWinLabel();
                        return;
                    }else{
                        //NOT NECESSARY: Here should be code for GUI to update turn count */
                        turnCount++;
                        view.updateTurnAccount(turnCount);

                        if((gameMode == GameMode.Online_PVP_Client || gameMode == GameMode.Online_PVP_Server || gameMode == GameMode.Online_PVP_Spectator) && !onAutoPlayback){
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                            client.setEndGame(false);
                            if (gameMode == GameMode.Online_PVP_Server) {
                                server.setEndGame(false);
                            }
                        }
                    }
                    if(gameMode == GameMode.PVE && currentPlayer != colorOfUser && !onAutoPlayback){
                        this.gatAIMove(GameController.aiDifficulty);
                    }
                }
            }
        }
        //Print the state, whether the selected point is null.
        if(selectedPoint != null){
            System.out.printf("Selected piece is %s at point (%d , %d)\n",model.getChessPieceAt(selectedPoint).getName(),selectedPoint.getRow(),selectedPoint.getCol());
        }else{
            System.out.println("No point is selected");
        }
        System.out.println("Turn: " + turnCount);
        Chessboard.printChessBoard(model.getGrid());
    }

    @Override
    public boolean onPlayerClickUndoButton() {
        int numberOfLoop;
        if(gameMode == GameMode.PVE) {
            numberOfLoop = 2;
        }else if(gameMode == GameMode.Local_PVP){
            numberOfLoop = 1;
        }else{
            System.out.println("Undo is not allowed in online mode.");
            return false;
        }

        if (allMovesOnBoard.size() == 0) {
            System.out.println("No move to undo");
            return false;
        }
        //Here should be code for GUI to remove all possible moves of the previous selected piece */
        if (selectedPoint != null) {
            ArrayList<Move> Moves = model.getChessPieceAt(new ChessboardPoint(selectedPoint.getRow(),selectedPoint.getCol())).getAvailableMoves(new ChessboardPoint(selectedPoint.getRow(),selectedPoint.getCol()),model.getGrid());
            view.removeAllPossibleMoves(Moves);
        }
        selectedPoint = null;

        for (int i = 0; i < numberOfLoop; i++) {
            Move lastMove = allMovesOnBoard.remove(allMovesOnBoard.size() - 1);

            //Here should be code for GUI to undo a move */
            view.undo(lastMove);
            this.swapColor();
            model.undoMove(lastMove);

            if(gameMode == GameMode.Local_PVP){
                this.swapUser();
            }
            turnCount--;

            //NOT NECESSARY: Here should be code for GUI to swap color (color of which player should perform a move) */
            //NOT NECESSARY: Here should be code for GUI to update turn count */
            view.updateTurnAccount(turnCount);
            timer.reset();
        }
        return true;
    }

    @Override
    public void onPlayerClickPlayBackButton() {
        System.out.println("Click on PlayBackButton");
        selectedPoint = null;
        this.currentPlayer = PlayerColor.BLUE;

        if(gameMode == GameMode.Local_PVP){
            this.colorOfUser = PlayerColor.BLUE;
        }
        turnCount = 1;

        model.reset();
        Thread thread = new Thread(() -> {
            view.resetChessBoardComponent();

            onAutoPlayback = true;
            ChessGameFrame.enabled = false;
            for (Move move : allMovesOnBoard) {
                onPlayerClickChessPiece(move.getFromPoint(), currentPlayer);

                try {
                    Thread.sleep(GameController.animationInterval);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                if(move.isDoesCapture()){
                    onPlayerClickChessPiece(move.getToPoint(), currentPlayer);
                }else{
                    onPlayerClickCell(move.getToPoint(), currentPlayer);
                }

                try {
                    Thread.sleep(GameController.animationInterval);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            onAutoPlayback = false;
            ChessGameFrame.enabled = true;
        });
        thread.start();
    }

    @Override
    public void onPlayerClickResetButton() {
        if(gameMode == GameMode.Online_PVP_Server || gameMode == GameMode.Online_PVP_Client || gameMode == GameMode.Online_PVP_Spectator){
            System.out.println("Reset is not allowed in online mode.");
            return;
        }
        timer.shutdown();
        timer = new Timer(this,view,1000);
        timer.start();
        turnCount = 1;
        selectedPoint = null;
        model.reset();
        view.resetChessBoardComponent();
        this.currentPlayer = PlayerColor.BLUE;
        this.colorOfUser = PlayerColor.BLUE;
        this.allMovesOnBoard.clear();
    }

    @Override
    public void onPlayerClickSaveButton(String filePath) {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(filePath));
            for (Move move : allMovesOnBoard) {
                writer.write(move.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing moves to file: " + filePath);
            e.printStackTrace();
            return;
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    System.out.println("Error closing file: " + filePath);
                    e.printStackTrace();
                }
            }
        }
        System.out.println("Finished writing moves to file: " + filePath);
    }

    @Override
    public void onPlayerClickLoadButton(String filePath) {
        if(gameMode != GameMode.Local_PVP){
            System.out.println("Load is only allowed in local PVP mode.");
            return;
        }

        //First reset the board.
        this.onPlayerClickResetButton();

        ArrayList<Move> moves = new ArrayList<>();
        BufferedReader reader = null;
        try{
            reader = new BufferedReader(new FileReader(filePath));
            String line;
            while ((line = reader.readLine()) != null) {
                Move move;

                String[] parts = line.split(",");

                if(parts.length < 7){
                    throw new IllegalArgumentException("ERROR 106: Invalid input format: " + line);
                }

                String movingPieceName = parts[0];
                String movingPieceOwner = parts[1];
                ChessPiece movingPiece;
                PlayerColor movingPieceOwnerColor;

                if(movingPieceOwner.equals("BLUE")) {
                    movingPieceOwnerColor = PlayerColor.BLUE;
                }else if(movingPieceOwner.equals("RED")){
                    movingPieceOwnerColor = PlayerColor.RED;
                }else{
                    throw new IllegalArgumentException("ERROR 103: Invalid input format(illegal moving chess piece color): " + line);
                }

                movingPiece = switch (movingPieceName) {
                    case "Elephant" -> new ElephantChessPiece(movingPieceOwnerColor);
                    case "Lion" -> new LionChessPiece(movingPieceOwnerColor);
                    case "Tiger" -> new TigerChessPiece(movingPieceOwnerColor);
                    case "Leopard" -> new LeopardChessPiece(movingPieceOwnerColor);
                    case "Wolf" -> new WolfChessPiece(movingPieceOwnerColor);
                    case "Dog" -> new DogChessPiece(movingPieceOwnerColor);
                    case "Cat" -> new CatChessPiece(movingPieceOwnerColor);
                    case "Rat" -> new RatChessPiece(movingPieceOwnerColor);
                    default ->
                            throw new IllegalArgumentException("ERROR 103: Invalid input format(illegal moving chess piece type): " + line);
                };

                int fromRow;
                try{
                    fromRow = Integer.parseInt(parts[2]);
                    if(fromRow < 0 || fromRow > 8){
                        throw new NumberFormatException();
                    }
                }catch (NumberFormatException e){
                    throw new IllegalArgumentException("ERROR 102: Invalid input format(illegal from point coordinate): " + line);
                }

                int fromCol;
                try {
                    fromCol = Integer.parseInt(parts[3]);
                    if(fromCol < 0 || fromCol > 6){
                        throw new NumberFormatException();
                    }
                }catch (NumberFormatException e){
                    throw new IllegalArgumentException("ERROR 102: Invalid input format(illegal from point coordinate): " + line);
                }

                ChessboardPoint fromPoint = new ChessboardPoint(fromRow, fromCol);

                int toRow;
                try{
                    toRow = Integer.parseInt(parts[4]);
                    if(toRow < 0 || toRow > 8){
                        throw new NumberFormatException();
                    }
                }catch (NumberFormatException e){
                    throw new IllegalArgumentException("ERROR 102: Invalid input format(illegal to point coordinate): " + line);
                }

                int toCol;
                try {
                    toCol = Integer.parseInt(parts[5]);
                    if(toCol < 0 || toCol > 6){
                        throw new NumberFormatException();
                    }
                }catch (NumberFormatException e){
                    throw new IllegalArgumentException("ERROR 102: Invalid input format(illegal to point coordinate): " + line);
                }
                ChessboardPoint toPoint = new ChessboardPoint(toRow, toCol);

                boolean doesCapture;
                if(parts[6].equals("true")){
                    doesCapture = true;
                }else if(parts[6].equals("false")){
                    doesCapture = false;
                }else {
                    throw new IllegalArgumentException("ERROR 106: Invalid input format(illegal doesCapture flag): " + line);
                }

                if (doesCapture) {

                    if(parts.length != 9){
                        throw new IllegalArgumentException("ERROR 106: Invalid input format: " + line);
                    }

                    String capturedPieceName = parts[7];
                    String capturedPieceOwner = parts[8];
                    ChessPiece capturedPiece;
                    PlayerColor capturedPieceOwnerColor;
                    if(capturedPieceOwner.equals("BLUE")) {
                        capturedPieceOwnerColor = PlayerColor.BLUE;
                    }else if(capturedPieceOwner.equals("RED")){
                        capturedPieceOwnerColor = PlayerColor.RED;
                    }else{
                        throw new IllegalArgumentException("ERROR 103: Invalid input format(illegal captured chess piece color): " + line);
                    }
                    capturedPiece = switch (capturedPieceName) {
                        case "Elephant" -> new ElephantChessPiece(capturedPieceOwnerColor);
                        case "Lion" -> new LionChessPiece(capturedPieceOwnerColor);
                        case "Tiger" -> new TigerChessPiece(capturedPieceOwnerColor);
                        case "Leopard" -> new LeopardChessPiece(capturedPieceOwnerColor);
                        case "Wolf" -> new WolfChessPiece(capturedPieceOwnerColor);
                        case "Dog" -> new DogChessPiece(capturedPieceOwnerColor);
                        case "Cat" -> new CatChessPiece(capturedPieceOwnerColor);
                        case "Rat" -> new RatChessPiece(capturedPieceOwnerColor);
                        default ->
                                throw new IllegalArgumentException("ERROR 103: Invalid input format(illegal captured piece type): " + line);
                    };
                    move = new Move(movingPiece, fromPoint, toPoint, true, capturedPiece);
                }else{

                    if(parts.length != 7){
                        throw new IllegalArgumentException("ERROR 106: Invalid input format: " + line);
                    }

                    move = new Move(movingPiece, fromPoint, toPoint, false, null);
                }
                moves.add(move);
            }
        } catch (IllegalArgumentException e){
            System.out.println(e.getMessage());

            /** NOT NECESSARY: Here should be a pop-up window to show the error message.
             * (I don't think the three other exceptions below need this)
             */
            JOptionPane.showMessageDialog(view.getChessGameFrame(), e.getMessage(), "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + filePath);
            e.printStackTrace();
            return;
        } catch (IOException e) {
            System.out.println("Error reading file: " + filePath);
            e.printStackTrace();
            return;
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                System.out.println("Error closing file: " + filePath);
                e.printStackTrace();
            }
        }

        System.out.println("Finished reading moves from file: " + filePath);

        boolean isValidFile = true;
        for(Move move : moves){
            try{
                if(move.isDoesCapture()){
                    if(this.currentPlayer != move.getMovingPiece().getOwner()){
                        throw new IllegalArgumentException("ERROR 104: Invalid move(it's not your turn)");
                    }
                    if(model.getChessPieceAt(move.getFromPoint()) == null || model.getChessPieceAt(move.getToPoint()) == null){
                        throw new IllegalArgumentException("ERROR 105: Invalid move(piece does not exist)");
                    }
                    if(model.getChessPieceAt(move.getFromPoint()).getCategory() != move.getMovingPiece().getCategory() || model.getChessPieceAt(move.getToPoint()).getCategory() != move.getCapturedPiece().getCategory()
                            || model.getChessPieceAt(move.getFromPoint()).getOwner() != move.getMovingPiece().getOwner() || model.getChessPieceAt(move.getToPoint()).getOwner() != move.getCapturedPiece().getOwner()){
                        throw new IllegalArgumentException("ERROR 105: Invalid move(piece does not match)");
                    }
                    if(model.getChessPieceAt(move.getFromPoint()).getOwner() != move.getMovingPiece().getOwner()){
                        throw new IllegalArgumentException("ERROR 105: Invalid move(piece does not belong to the player)");
                    }
                    if(model.getChessPieceAt(move.getToPoint()).getOwner() == move.getMovingPiece().getOwner()){
                        throw new IllegalArgumentException("ERROR 105: Invalid move(cannot capture your own piece)");
                    }
                    if (!model.isValidCapture(move.getFromPoint(), move.getToPoint())) {
                        throw new IllegalArgumentException("ERROR 105: Invalid move(piece can't reach the destination or piece can't capture the target)");
                    }
                    allMovesOnBoard.add(model.captureChessPiece(move.getFromPoint(),move.getToPoint()));
                }else{
                    if(this.currentPlayer != move.getMovingPiece().getOwner()){
                        throw new IllegalArgumentException("ERROR 104: Invalid move(it's not your turn)");
                    }
                    if(model.getChessPieceAt(move.getFromPoint()) == null){
                        throw new IllegalArgumentException("ERROR 105: Invalid move(piece does not exist)");
                    }
                    if(model.getChessPieceAt(move.getFromPoint()).getCategory() != move.getMovingPiece().getCategory() || model.getChessPieceAt(move.getFromPoint()).getOwner() != move.getMovingPiece().getOwner()){
                        throw new IllegalArgumentException("ERROR 105: Invalid move(piece does not match)");
                    }
                    if(model.getChessPieceAt(move.getToPoint()) != null){
                        throw new IllegalArgumentException("ERROR 105: Invalid move(there is a piece in the target cell)");
                    }
                    if(model.getChessPieceAt(move.getFromPoint()).getOwner() != move.getMovingPiece().getOwner()){
                        throw new IllegalArgumentException("ERROR 105: Invalid move(piece does not belong to the player)");
                    }
                    if (!model.isValidMove(move.getFromPoint(), move.getToPoint())) {
                        throw new IllegalArgumentException("ERROR 105: Invalid move(piece can't reach the destination)");
                    }
                    allMovesOnBoard.add(model.moveChessPiece(move.getFromPoint(), move.getToPoint()));
                }
                this.swapColor();
            }catch(IllegalArgumentException e){
                //Print error message.
                isValidFile = false;
                System.out.println(e.getMessage() + ": " + move);

                JOptionPane.showMessageDialog(view.getChessGameFrame(), e.getMessage(), "Warning", JOptionPane.WARNING_MESSAGE);

                break;
            }
        }
        if(!isValidFile){
            System.out.println("File Check Failed: Invalid file.");
            //Reset the board.
            this.onPlayerClickResetButton();
        }else{
            System.out.println("File Check Passed: Valid file.");

            //NOT NECESSARY: Here should be a pop-up window to show file reads successfully. */
            /*this.onPlayerClickResetButton();
            for(Move move : moves){
                this.onPlayerClickChessPiece(move.getFromPoint(),currentPlayer);

                try {
                    Thread.sleep(GameController.animationInterval);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                if(move.isDoesCapture()){
                    this.onPlayerClickChessPiece(move.getToPoint(),currentPlayer);
                }else{
                    this.onPlayerClickCell(move.getToPoint(),currentPlayer);
                }

                try {
                    Thread.sleep(GameController.animationInterval);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

            }*/
            /** Jerry: I have added a new Thread here so that it can show the playback process of the chess*/
            Thread thread = new Thread(() -> {
                onPlayerClickResetButton();
                ChessGameFrame.enabled = false;
                for (Move move : moves) {
                    onPlayerClickChessPiece(move.getFromPoint(), currentPlayer);

                    try {
                        Thread.sleep(GameController.animationInterval);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                    if(move.isDoesCapture()){
                        onPlayerClickChessPiece(move.getToPoint(), currentPlayer);
                    }else{
                        onPlayerClickCell(move.getToPoint(), currentPlayer);
                    }

                    try {
                        Thread.sleep(GameController.animationInterval);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                ChessGameFrame.enabled = true;
            });
            thread.start();
        }
    }

    @Override
    public boolean onPlayerClickLoginButton(String username, String password) {
        if(user1 == null){
            for (User user : allUsers) {
                if(user.getUsername().equals(username) && user.validatePassword(password) && user.getPlayerType() != PlayerType.AI){
                    user1 = user;
                    System.out.println("User 1 successfully log in.");
                    return true;
                }
            }
        }else{
            for (User user : allUsers) {
                if(user.getUsername().equals(username) && user.validatePassword(password) && user.getPlayerType() != PlayerType.AI && user != user1){
                    user2 = user;
                    System.out.println("User 2 successfully log in.");
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public boolean onPlayerClickRegisterButton(String username, String password) {
        for (User user : allUsers) {
            if(user.getUsername().equals(username)){
                return false;
            }
        }
        allUsers.add(new User(username,password));
        this.writeUsers();
        return true;
    }

    @Override
    public void onPlayerSelectLocalPVPMode() {
        gameMode = GameMode.Local_PVP;
        colorOfUser = PlayerColor.BLUE;

        timer = new Timer(this,view,1000);
        timer.start();
    }

    @Override
    public void onPlayerSelectLocalPVEMode(AIDifficulty difficulty) {
        aiDifficulty = difficulty;
        if(difficulty == AIDifficulty.EASY) {
            for (User user : allUsers) {
                if (user.getPlayerType() == PlayerType.AI && user.getUsername().equals("AI_EASY")){
                    user2 = user;
                }
            }
        }else if(difficulty == AIDifficulty.MEDIUM) {
            for (User user : allUsers) {
                if (user.getPlayerType() == PlayerType.AI && user.getUsername().equals("AI_MEDIUM")) {
                    user2 = user;
                }
            }
        }else{
            for (User user : allUsers) {
                if (user.getPlayerType() == PlayerType.AI && user.getUsername().equals("AI_HARD")) {
                    user2 = user;
                }
            }
        }
        colorOfUser = PlayerColor.BLUE;
        gameMode = GameMode.PVE;

        timer = new Timer(this,view,1000);
        timer.start();
    }
    @Override
    public void onPlayerCreateServer() {
        gameMode = GameMode.Online_PVP_Server;

        server = new ServerThread();
        Socket socket = new Socket();
        System.out.println("Starting a new server");

        try{
            server.start();
        }catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
            return;
        }
        System.out.println("Server thread started successfully");

        try {
            Thread.sleep(100);
            socket.connect(new InetSocketAddress("localhost", 1234), 1000);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        try {
            client = new ClientThread(socket,this);
            client.start();
        }catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
            return;
        }
        System.out.println("Client thread started successfully");
    }

    @Override
    public boolean onPlayerJoinServer(String ipAddress) {
        Socket socket = new Socket();
        try {
            socket.connect(new InetSocketAddress(ipAddress, 1234), 500);
        } catch (SocketTimeoutException e){
            JOptionPane.showMessageDialog(view.getSelectOnlinePVPFrame(),"Server not found","Error",JOptionPane.WARNING_MESSAGE);
            return false;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        gameMode = GameMode.Online_PVP_Client;

        try {
            client = new ClientThread(socket,this);
            client.start();
        }catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
            return false;
        }
        System.out.println("Client thread started successfully");

        return true;
    }

    @Override
    public ArrayList<User> onPlayerClickRankListButtonByScore() {
        allUsers.sort(Comparator.comparing(User::getScore).reversed());
        return allUsers;
    }
    @Override
    public ArrayList<User> onPlayerClickRankListButtonByWinRate() {
        allUsers.sort(Comparator.comparing(User::getWinRate).reversed());
        return allUsers;
    }

    //Logout is used when you return to the InitFrame
    @Override
    public void onPlayerClickLogoutButton() {
        user1 = null;
    }

    //Exit is used when you end a ChessGameFrame and Return to the Start Frame
    @Override
    public void onPlayerExitGameFrame() {
        turnCount = 1;
        selectedPoint = null;

        model.reset();
        view.resetChessBoardComponent();

        currentPlayer = PlayerColor.BLUE;
        allMovesOnBoard.clear();
        timer.shutdown();
        timer = null;
        aiDifficulty = AIDifficulty.EASY;
        user2 = null;
        gameMode = null;
        colorOfUser = null;
    }



    //Getter and Setter
    public void setColorOfUser(PlayerColor colorOfUser) {
        this.colorOfUser = colorOfUser;
    }
    public PlayerColor getColorOfUser(){
        return this.colorOfUser;
    }

    public PlayerColor getCurrentPlayer() {
        return currentPlayer;
    }

    public Chessboard getModel() {
        return model;
    }

    public Frame getView() {
        return view;
    }

}