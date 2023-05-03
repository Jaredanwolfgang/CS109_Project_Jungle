package controller;

import listener.GameListener;
import model.AI.*;
import model.ChessPieces.*;
import model.Enum.*;
import model.ChessBoard.*;
import model.User.User;
import model.Timer.Timer;
import view.*;
import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Comparator;
import Server.*;

/**
 * Controller is the connection between model and view,
 * when a Controller receive a request from a view, the Controller
 * analyzes and then hands over to the model for processing
*/
public class GameController implements GameListener {
    public static final long animationInterval = 400;
    private static final String USER_FILE_PATH = "src\\model\\User\\users.txt";
    private Chessboard model;
    private ChessboardComponent view;
    private ServerThread server;
    private ClientThread client;
    public Timer timer;
    private PlayerColor colorOfUser;
    private PlayerColor currentPlayer;
    public static User user1;
    public static User user2;
    public static GameMode gameMode;

    // Record all moves on the board.
    private ArrayList<Move> allMovesOnBoard;
    private ArrayList<User> allUsers;

    // Record whether there is a selected piece before and where
    private ChessboardPoint selectedPoint;

    //If this variable is true, it means the controller is doing playback.
    private boolean onAutoPlayback;
    public static AIDifficulty aiDifficulty;
    public static int turnCount;

    public GameController(ChessboardComponent view, Chessboard model) {
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

        model.registerController(this);
        view.registerController(this);
        //view.initiateChessComponent(model);
        //view.repaint();
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
                return;
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
                return;
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

    public void setColorOfUser(PlayerColor playerColor) {
        this.colorOfUser = playerColor;
    }

    public PlayerColor getColorOfUser() {
        return colorOfUser;
    }

    public GameMode getGameMode(){
        return gameMode;
    }

    public void setGameMode(GameMode gameMode) {
        GameController.gameMode = gameMode;
    }

    //Judge if there is a winner in two ways.
    //First: One player's piece enters the other player's den.
    //Second: After a capture, one player has no piece left.
    private PlayerColor win() {
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

    public void gatAIMove() {
        //Do not use this function in veiw.
        //This is only a reusable method to create AI.
        Thread AI = new AIThread(this, model.getGrid(), currentPlayer);
        AI.start();
    }

    // click an empty cell
    @Override
    public void onPlayerClickCell(ChessboardPoint point, PlayerColor playerColor) {
        if(playerColor != currentPlayer){
            System.out.println("Not your turn!");

            /** Here should be code for GUI to tell user that it's not his turn */

            return;
        }
        if (selectedPoint != null) {
            //Try to move the selected piece to the clicked cell.
            try{
                Move moveToMake = model.moveChessPiece(selectedPoint,point);
                //If the move is invalid, the try sentence ends here.
                if(!onAutoPlayback){
                    allMovesOnBoard.add(moveToMake);
                }
                if((gameMode == GameMode.Online_PVP_Server || gameMode == GameMode.Online_PVP_Client) && currentPlayer == colorOfUser && !onAutoPlayback){
                    this.client.makeMove(moveToMake);
                }

                /** Here should be code for GUI to repaint the board.(One piece moved) */

                selectedPoint = null;
                this.swapColor();

                /** NOT NECESSARY: Here should be code for GUI to swap color (color of which player should perform a move) */

                if(gameMode == GameMode.Local_PVP){
                    this.swapUser();
                }
                turnCount++;

                /** NOT NECESSARY: Here should be code for GUI to update turn count */

                timer.reset();
            }catch (IllegalArgumentException e){
                //Print error message.
                System.out.println(e.getMessage());

                /** NOT NECESSARY: Here should be code for GUI to tell user that the move is invalid */

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

                /** Here should be code for GUI to display game over message to user */

                return;
            }else{
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
                this.gatAIMove();
            }
        }
        Chessboard.printChessBoard(model.getGrid());
        if(selectedPoint != null){
            System.out.printf("Selected piece is %s at point (%d , %d)\n",model.getChessPieceAt(selectedPoint).getName(),selectedPoint.getRow(),selectedPoint.getCol());
        }else{
            System.out.println("No point is selected");
        }
        System.out.println("Turn: " + turnCount);
    }

    // click a cell with a chess
    @Override
    public void onPlayerClickChessPiece(ChessboardPoint point, PlayerColor playerColor) {
        if(playerColor != currentPlayer){

            /** Here should be code for GUI to tell user that it's not his turn */

            System.out.println("Not your turn!");
            return;
        }
        if (selectedPoint == null) {
            if (model.getChessPieceOwner(point) == currentPlayer) {
                //If the clicked piece is the current player's piece, select it.
                selectedPoint = point;

                /** Here should be code for GUI to show all available moves for the selected piece */

            }
        }else{
            if (selectedPoint.equals(point)) {
                //If the clicked piece is the selected piece, deselect it.

                /** Here should be code for GUI to remove all possible moves of the previous selected piece */

                selectedPoint = null;
            }else{
                if(model.getChessPieceOwner(point) == currentPlayer){
                    //If the clicked piece is the current player's piece, select it.

                    /** Here should be code for GUI to remove all possible moves of the previous selected piece */

                    selectedPoint = point;

                    /** Here should be code for GUI to show all available moves for the selected piece */

                }else{
                    //Try to capture the clicked piece with the selected piece.
                    try{
                        Move moveToMake = model.captureChessPiece(selectedPoint,point);
                        //If the capture is invalid, the try sentence ends here.

                        if(!onAutoPlayback) {
                            allMovesOnBoard.add(moveToMake);
                        }
                        if((gameMode == GameMode.Online_PVP_Server || gameMode == GameMode.Online_PVP_Client) && currentPlayer == colorOfUser && !onAutoPlayback){
                            this.client.makeMove(moveToMake);
                        }

                        //Here should be code for GUI to repaint the board.(One piece captured)
                        selectedPoint = null;
                        this.swapColor();

                        /** NOT NECESSARY: Here should be code for GUI to swap color (color of which player should perform a move) */

                        if(gameMode == GameMode.Local_PVP){
                            this.swapUser();
                        }
                        turnCount++;

                        /** NOT NECESSARY: Here should be code for GUI to update turn count */

                        timer.reset();
                    }catch (IllegalArgumentException e){
                        //Print error message.
                        System.out.println(e.getMessage());

                        /** NOT NECESSARY: Here should be code for GUI to tell user that the move is invalid */

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

                        /** Here should be code for GUI to display game over message to user */

                        return;
                    }else{
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
                        this.gatAIMove();
                    }
                }
            }
        }
        Chessboard.printChessBoard(model.getGrid());
        if(selectedPoint != null){
            System.out.printf("Selected piece is %s at point (%d , %d)\n",model.getChessPieceAt(selectedPoint).getName(),selectedPoint.getRow(),selectedPoint.getCol());
        }else{
            System.out.println("No point is selected");
        }
        System.out.println("Turn: " + turnCount);
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
        selectedPoint = null;

        /** Here should be code for GUI to remove all possible moves of the previous selected piece */

        for (int i = 0; i < numberOfLoop; i++) {
            Move lastMove = allMovesOnBoard.remove(allMovesOnBoard.size() - 1);
            model.undoMove(lastMove);

            /** Here should be code for GUI to undo a move */

            this.swapColor();

            /** NOT NECESSARY: Here should be code for GUI to swap color (color of which player should perform a move) */

            if(gameMode == GameMode.Local_PVP){
                this.swapUser();
            }
            turnCount--;

            /** NOT NECESSARY: Here should be code for GUI to update turn count */

            timer.reset();
        }
        return true;
    }

    @Override
    public void onPlayerClickPlayBackButton() {
        selectedPoint = null;

        /** Here should be code for GUI to remove all possible moves of the previous selected piece */

        model.reset();
        this.currentPlayer = PlayerColor.BLUE;

        /** NOT NECESSARY: Here should be code for GUI to update color (color of which player should perform a move) */

        if(gameMode == GameMode.Local_PVP){
            this.colorOfUser = PlayerColor.BLUE;
        }
        turnCount = 1;

        /** NOT NECESSARY: Here should be code for GUI to update turn count */

        onAutoPlayback = true;
        for (Move move : allMovesOnBoard) {
            this.onPlayerClickChessPiece(move.getFromPoint(), currentPlayer);

            try {
                Thread.sleep(GameController.animationInterval);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            if(move.isDoesCapture()){
                this.onPlayerClickChessPiece(move.getToPoint(), currentPlayer);
            }else{
                this.onPlayerClickCell(move.getToPoint(), currentPlayer);
            }

            try {
                Thread.sleep(GameController.animationInterval);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        onAutoPlayback = false;
    }

    @Override
    public void onPlayerClickResetButton() {
        if(gameMode == GameMode.Online_PVP_Server || gameMode == GameMode.Online_PVP_Client || gameMode == GameMode.Online_PVP_Spectator){
            System.out.println("Reset is not allowed in online mode.");
            return;
        }
        turnCount = 1;

        /** NOT NECESSARY: Here should be code for GUI to update turn count */

        selectedPoint = null;

        /** Here should be code for GUI to remove all possible moves of the previous selected piece */

        model.reset();
        this.currentPlayer = PlayerColor.BLUE;

        /** NOT NECESSARY: Here should be code for GUI to update color (color of which player should perform a move) */

        this.colorOfUser = PlayerColor.BLUE;
        this.allMovesOnBoard.clear();
        timer.shutdown();
        timer = new Timer(this,1000);
        timer.start();

        //Here should be code for GUI to repaint the board.(Board reset)

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
                    return;
                }
            }
        }
        System.out.println("Finished writing moves to file: " + filePath);

        /** NOT NECESSARY: Here should be code for GUI to show a message indicating that the file is saved successfully */

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
                    throw new IllegalArgumentException("Invalid input format: " + line);
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
                    throw new IllegalArgumentException("Invalid input format(illegal moving chess piece color): " + line);
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
                            throw new IllegalArgumentException("Invalid input format(illegal moving chess piece type): " + line);
                };

                int fromRow;
                try{
                    fromRow = Integer.parseInt(parts[2]);
                    if(fromRow < 0 || fromRow > 8){
                        throw new NumberFormatException();
                    }
                }catch (NumberFormatException e){
                    throw new IllegalArgumentException("Invalid input format(illegal from point coordinate): " + line);
                }

                int fromCol;
                try {
                    fromCol = Integer.parseInt(parts[3]);
                    if(fromCol < 0 || fromCol > 6){
                        throw new NumberFormatException();
                    }
                }catch (NumberFormatException e){
                    throw new IllegalArgumentException("Invalid input format(illegal from point coordinate): " + line);
                }

                ChessboardPoint fromPoint = new ChessboardPoint(fromRow, fromCol);

                int toRow;
                try{
                    toRow = Integer.parseInt(parts[4]);
                    if(toRow < 0 || toRow > 8){
                        throw new NumberFormatException();
                    }
                }catch (NumberFormatException e){
                    throw new IllegalArgumentException("Invalid input format(illegal to point coordinate): " + line);
                }

                int toCol;
                try {
                    toCol = Integer.parseInt(parts[5]);
                    if(toCol < 0 || toCol > 6){
                        throw new NumberFormatException();
                    }
                }catch (NumberFormatException e){
                    throw new IllegalArgumentException("Invalid input format(illegal to point coordinate): " + line);
                }
                ChessboardPoint toPoint = new ChessboardPoint(toRow, toCol);

                boolean doesCapture;
                if(parts[6].equals("true")){
                    doesCapture = true;
                }else if(parts[6].equals("false")){
                    doesCapture = false;
                }else {
                    throw new IllegalArgumentException("Invalid input format(illegal doesCapture flag): " + line);
                }

                if (doesCapture) {

                    if(parts.length != 9){
                        throw new IllegalArgumentException("Invalid input format: " + line);
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
                        throw new IllegalArgumentException("Invalid input format(illegal captured chess piece color): " + line);
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
                                throw new IllegalArgumentException("Invalid input format(illegal captured piece type): " + line);
                    };
                    move = new Move(movingPiece, fromPoint, toPoint, true, capturedPiece);
                }else{

                    if(parts.length != 7){
                        throw new IllegalArgumentException("Invalid input format: " + line);
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
                return;
            }
        }

        System.out.println("Finished reading moves from file: " + filePath);

        boolean isValidFile = true;
        for(Move move : moves){
            try{
                if(move.isDoesCapture()){
                    if(model.getChessPieceAt(move.getFromPoint()) == null || model.getChessPieceAt(move.getToPoint()) == null){
                        throw new IllegalArgumentException("Invalid move(piece does not exist)");
                    }
                    if(model.getChessPieceAt(move.getFromPoint()).getCategory() != move.getMovingPiece().getCategory() || model.getChessPieceAt(move.getToPoint()).getCategory() != move.getCapturedPiece().getCategory()
                        || model.getChessPieceAt(move.getFromPoint()).getOwner() != move.getMovingPiece().getOwner() || model.getChessPieceAt(move.getToPoint()).getOwner() != move.getCapturedPiece().getOwner()){
                        throw new IllegalArgumentException("Invalid move(piece does not match)");
                    }
                    if(model.getChessPieceAt(move.getFromPoint()).getOwner() != move.getMovingPiece().getOwner() || this.currentPlayer != move.getMovingPiece().getOwner()){
                        throw new IllegalArgumentException("Invalid move(piece does not belong to the player)");
                    }
                    if(model.getChessPieceAt(move.getToPoint()).getOwner() == move.getMovingPiece().getOwner()){
                        throw new IllegalArgumentException("Invalid move(cannot capture your own piece)");
                    }
                    if (!model.isValidCapture(move.getFromPoint(), move.getToPoint())) {
                        throw new IllegalArgumentException("Invalid move(piece can't reach the destination or piece can't capture the target)");
                    }
                    allMovesOnBoard.add(model.captureChessPiece(move.getFromPoint(),move.getToPoint()));
                }else{
                    if(model.getChessPieceAt(move.getFromPoint()) == null){
                        throw new IllegalArgumentException("Invalid move(piece does not exist)");
                    }
                    if(model.getChessPieceAt(move.getFromPoint()).getCategory() != move.getMovingPiece().getCategory() || model.getChessPieceAt(move.getFromPoint()).getOwner() != move.getMovingPiece().getOwner()){
                        throw new IllegalArgumentException("Invalid move(piece does not match)");
                    }
                    if(model.getChessPieceAt(move.getToPoint()) != null){
                        throw new IllegalArgumentException("Invalid move(there is a piece in the target cell)");
                    }
                    if(model.getChessPieceAt(move.getFromPoint()).getOwner() != move.getMovingPiece().getOwner() || this.currentPlayer != move.getMovingPiece().getOwner()){
                        throw new IllegalArgumentException("Invalid move(piece does not belong to the player)");
                    }
                    if (!model.isValidMove(move.getFromPoint(), move.getToPoint())) {
                        throw new IllegalArgumentException("Invalid move(piece can't reach the destination)");
                    }
                    allMovesOnBoard.add(model.moveChessPiece(move.getFromPoint(), move.getToPoint()));
                }
                this.swapColor();
            }catch(IllegalArgumentException e){
                //Print error message.
                isValidFile = false;
                System.out.println(e.getMessage() + ": " + move);

                /** NOT NECESSARY: Here should be a pop-up window to show the error message. */

                break;
            }
        }
        if(!isValidFile){
            System.out.println("File Check Failed: Invalid file.");
            //Reset the board.
            this.onPlayerClickResetButton();
        }else{
            System.out.println("File Check Passed: Valid file.");

            /** NOT NECESSARY: Here should be a pop-up window to show file reads successfully. */

            this.onPlayerClickResetButton();
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

            }
        }
    }

    @Override
    public boolean onPlayerClickLoginButton(String username, String password) {
        if(user1 == null){
            for (User user : allUsers) {
                if(user.getUsername().equals(username) && user.validatePassword(password) && user.getPlayerType() != PlayerType.AI){
                    user1 = user;
                    return true;
                }
            }
        }else{
            for (User user : allUsers) {
                if(user.getUsername().equals(username) && user.validatePassword(password) && user.getPlayerType() != PlayerType.AI && user != user1){
                    user2 = user;
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public boolean onPlayerClickRegisterButton(String username, String password) {
        for (User user : allUsers) {
            if(user.getUsername().equals(username) && user.validatePassword(password)){
                return false;
            }
        }
        allUsers.add(new User(username,password));
        this.writeUsers();
        return true;
    }

    @Override
    public void onPlayerClickLogoutButton() {
        user1 = null;
    }

    @Override
    public void onPlayerSelectLocalPVPMode() {
        gameMode = GameMode.Local_PVP;
        colorOfUser = PlayerColor.BLUE;

        timer = new Timer(this,1000);
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

        timer = new Timer(this,1000);
        timer.start();
    }

    @Override
    public void onPlayerSelectOnlinePVPMode() {
        Socket socket = new Socket();
        try{
            socket.connect(new InetSocketAddress("localhost", 1234), 1000);
            System.out.println("Server found, connected to server");
        }catch (Exception ex){
            server = new ServerThread();
            System.out.println("No server found, starting a new server");
            try{
                server.start();
            }catch (IllegalArgumentException e){
                System.out.println(e.getMessage());
                return;
            }
            System.out.println("Server thread started successfully");
            gameMode = GameMode.Online_PVP_Server;
            try {
                Thread.sleep(100);
                socket = new Socket();
                socket.connect(new InetSocketAddress("localhost", 1234), 1000);
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
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
    public ArrayList<User> onPlayerClickRankListButton() {
        allUsers.sort(Comparator.comparing(User::getScore).reversed());
        return allUsers;
    }

    @Override
    public void onPlayerExitGameFrame() {
        this.onPlayerClickResetButton();
        aiDifficulty = AIDifficulty.EASY;
        user2 = null;
        gameMode = null;
        colorOfUser = null;
        timer.shutdown();
        timer = null;
    }

    public void testViaKeyboard(int x,int y){
        ChessboardPoint point = new ChessboardPoint(x,y);
        if(model.getChessPieceAt(point) == null){
            onPlayerClickCell(point,colorOfUser);
        }else{
            onPlayerClickChessPiece(point,colorOfUser);
        }
    }
}