package controller;


import listener.GameListener;
import model.AI.*;
import model.ChessPieces.*;
import model.Enum.*;
import model.ChessBoard.*;
import model.User.User;
import view.*;
import view.ChessComponent.ElephantChessComponent;
import java.io.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Objects;

/**
 * Controller is the connection between model and view,
 * when a Controller receive a request from a view, the Controller
 * analyzes and then hands over to the model for processing
*/
public class GameController implements GameListener {
    private static final String USER_FILE_PATH = "src\\model\\User\\users.txt";
    private Chessboard model;
    private ChessboardComponent view;
    private PlayerColor currentPlayer;
    static User user1;
    static User user2;
    static GameMode gameMode = GameMode.Local_PVP;

    // Record all moves on the board.
    private ArrayList<Move> allMovesOnBoard;
    private ArrayList<User> allUsers;

    // Record whether there is a selected piece before and where
    private ChessboardPoint selectedPoint;

    public GameController(ChessboardComponent view, Chessboard model) {
        this.view = view;
        this.model = model;
        this.currentPlayer = PlayerColor.BLUE;
        this.allMovesOnBoard = new ArrayList<>();
        this.allUsers = new ArrayList<>();
        this.selectedPoint = null;
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
                double score = Double.parseDouble(parts[2]);
                String playerType = parts[3];
                User user = new User();
                user.setUsername(username);
                user.setPassword(password);
                user.setScore(score);
                if(playerType.equals("HUMAN")){
                    user.setPlayerType(PlayerType.HUMAN);
                }else if(playerType.equals("AI")){
                    user.setPlayerType(PlayerType.AI);
                }
                allUsers.add(user);
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                System.err.println("Error closing file: " + e.getMessage());
            }
        }
    }

    private void writeUsers(){
        allUsers.sort(Comparator.comparing(x -> x.getScore()));
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(USER_FILE_PATH));
            for (User user : allUsers) {
                writer.write(user.toString());
            }
        } catch (IOException e) {
            System.err.println("Error writing file: " + e.getMessage());
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                System.err.println("Error closing file: " + e.getMessage());
            }
        }
    }

    // after a valid move swap the player
    private void swapColor() {
        currentPlayer = currentPlayer == PlayerColor.BLUE ? PlayerColor.RED : PlayerColor.BLUE;
    }

    //Judge if there is a winner in two ways.
    //First: One player's piece enters the other player's den.
    //Second: After a capture, one player has no piece left.
    private boolean win() {
        if((model.getGrid()[0][3].getPiece() != null && model.getGrid()[0][3].getPiece().getOwner() == PlayerColor.RED) ||
                (model.getGrid()[8][3].getPiece() != null && model.getGrid()[8][3].getPiece().getOwner() == PlayerColor.BLUE) ||
                model.noPieceLeft()){
            return true;
        }
        return false;
    }

    private void updateUserScore(User winner, User loser){
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

    // click an empty cell
    @Override
    public void onPlayerClickCell(ChessboardPoint point) {
        if (selectedPoint != null) {
            //Try to move the selected piece to the clicked cell.
            try{
                allMovesOnBoard.add(model.moveChessPiece(selectedPoint, point));
                //If the move is invalid, the try sentence ends here.

                //Here should be code for GUI to repaint the board.(One piece moved)

                selectedPoint = null;
                if(win()){
                    if(currentPlayer == PlayerColor.BLUE){
                        updateUserScore(user1, user2);
                    }else{
                        updateUserScore(user2, user1);
                    }

                    // TO DO: What should we do after one player wins?

                }
                this.swapColor();
            }catch (IllegalArgumentException e){
                //Print error message.
                System.out.println(e.getMessage());
            }
            if(gameMode == GameMode.PVE && currentPlayer == PlayerColor.RED){
                this.onPlayerClickAIMoveButton();
            }
        }
    }

    // click a cell with a chess
    @Override
    public void onPlayerClickChessPiece(ChessboardPoint point) {
        if (selectedPoint == null) {
            if (model.getChessPieceOwner(point) == currentPlayer) {
                //If the clicked piece is the current player's piece, select it.
                selectedPoint = point;

                //Following code is just for debugging.
                //It will print all valid moves of the selected chess piece, once a piece is selected.
                /*ChessPiece piece = model.getChessPieceAt(point);
                ArrayList<Move> validMoves = piece.getAvailableMoves(point, model.getGrid());
                for (Move move : validMoves) {
                    System.out.println(move);
                }*/

            }
        }else{
            if (selectedPoint.equals(point)) {
                //If the clicked piece is the selected piece, deselect it.
                selectedPoint = null;
            }else{
                if(model.getChessPieceOwner(point) == currentPlayer){
                    //If the clicked piece is the current player's piece, select it.
                    selectedPoint = point;
                }else{
                    //Try to capture the clicked piece with the selected piece.
                    try{
                        allMovesOnBoard.add(model.captureChessPiece(selectedPoint,point));
                        //If the capture is invalid, the try sentence ends here.

                        //Here should be code for GUI to repaint the board.(One piece captured)

                        selectedPoint = null;
                        if(win()){
                            if(currentPlayer == PlayerColor.BLUE){
                                updateUserScore(user1, user2);
                            }else{
                                updateUserScore(user2, user1);
                            }

                            // TO DO: What should we do after one player wins?

                        }
                        this.swapColor();
                    }catch (IllegalArgumentException e){
                        //Print error message.
                        System.out.println(e.getMessage());
                    }
                    if(gameMode == GameMode.PVE && currentPlayer == PlayerColor.RED){
                        this.onPlayerClickAIMoveButton();
                    }
                }
            }
        }
    }

    @Override
    public boolean onPlayerClickUndoButton() {
        int numberOfLoop;
        if(gameMode == GameMode.PVE) {
            numberOfLoop = 2;
        }else{
            numberOfLoop = 1;
        }
        if (allMovesOnBoard.size() == 0) {
            System.out.println("No move to undo");
            return false;
        }
        selectedPoint = null;
        for (int i = 0; i < numberOfLoop; i++) {
            Move lastMove = allMovesOnBoard.remove(allMovesOnBoard.size() - 1);
            model.undoMove(lastMove);

            //Here should be code for GUI to repaint the board.(Move undone)

            this.swapColor();
        }
        return true;
    }

    @Override
    public void onPlayerClickResetButton() {
        selectedPoint = null;
        model.reset();
        this.currentPlayer = PlayerColor.BLUE;
        this.allMovesOnBoard.clear();

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
        //First reset the board.
        this.onPlayerClickResetButton();

        ArrayList<Move> moves = new ArrayList<>();
        BufferedReader reader = null;
        try{
            reader = new BufferedReader(new FileReader(filePath));
            String line;
            while ((line = reader.readLine()) != null) {
                Move move = null;

                String[] parts = line.split(",");

                if(parts.length < 7){
                    throw new IllegalArgumentException("Invalid input format: " + line);
                }
                
                String movingPieceName = parts[0];
                String movingPieceOwner = parts[1];
                ChessPiece movingPiece = null;
                PlayerColor movingPieceOwnerColor = null;

                if(movingPieceOwner.equals("BLUE")) {
                    movingPieceOwnerColor = PlayerColor.BLUE;
                }else if(movingPieceOwner.equals("RED")){
                    movingPieceOwnerColor = PlayerColor.RED;
                }else{
                    throw new IllegalArgumentException("Invalid input format(illegal moving chess piece color): " + line);
                }

                switch(movingPieceName){
                    case "Elephant":
                        movingPiece = new ElephantChessPiece(movingPieceOwnerColor);
                        break;
                    case "Lion":
                        movingPiece = new LionChessPiece(movingPieceOwnerColor);
                        break;
                    case "Tiger":
                        movingPiece = new TigerChessPiece(movingPieceOwnerColor);
                        break;
                    case "Leopard":
                        movingPiece = new LeopardChessPiece(movingPieceOwnerColor);
                        break;
                    case "Wolf":
                        movingPiece = new WolfChessPiece(movingPieceOwnerColor);
                        break;
                    case "Dog":
                        movingPiece = new DogChessPiece(movingPieceOwnerColor);
                        break;
                    case "Cat":
                        movingPiece = new CatChessPiece(movingPieceOwnerColor);
                        break;
                    case "Rat":
                        movingPiece = new RatChessPiece(movingPieceOwnerColor);
                        break;
                    default:
                        throw new IllegalArgumentException("Invalid input format(illegal moving chess piece type): " + line);
                }

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
                    String capturedPieceOwner = parts[1];
                    ChessPiece capturedPiece = null;
                    PlayerColor capturedPieceOwnerColor = null;
                    if(capturedPieceOwner.equals("BLUE")) {
                        capturedPieceOwnerColor = PlayerColor.BLUE;
                    }else if(capturedPieceOwner.equals("RED")){
                        capturedPieceOwnerColor = PlayerColor.RED;
                    }else{
                        throw new IllegalArgumentException("Invalid input format(illegal captured chess piece color): " + line);
                    }
                    switch(capturedPieceName){
                        case "Elephant":
                            capturedPiece = new ElephantChessPiece(capturedPieceOwnerColor);
                            break;
                        case "Lion":
                            capturedPiece = new LionChessPiece(capturedPieceOwnerColor);
                            break;
                        case "Tiger":
                            capturedPiece = new TigerChessPiece(capturedPieceOwnerColor);
                            break;
                        case "Leopard":
                            capturedPiece = new LeopardChessPiece(capturedPieceOwnerColor);
                            break;
                        case "Wolf":
                            capturedPiece = new WolfChessPiece(capturedPieceOwnerColor);
                            break;
                        case "Dog":
                            capturedPiece = new DogChessPiece(capturedPieceOwnerColor);
                            break;
                        case "Cat":
                            capturedPiece = new CatChessPiece(capturedPieceOwnerColor);
                            break;
                        case "Rat":
                            capturedPiece = new RatChessPiece(capturedPieceOwnerColor);
                            break;
                        default:
                            throw new IllegalArgumentException("Invalid input format(illegal captured piece type): " + line);
                    }
                    move = new Move(movingPiece, fromPoint, toPoint, doesCapture, capturedPiece);
                }else{

                    if(parts.length != 7){
                        throw new IllegalArgumentException("Invalid input format: " + line);
                    }

                    move = new Move(movingPiece, fromPoint, toPoint, doesCapture, null);
                }
                moves.add(move);
            }
        } catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
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
                break;
            }
        }
        if(!isValidFile){
            System.out.println("File Check Failed: Invalid file.");

            //Here should be code in GUI to show error message.

            //Reset the board.
            this.onPlayerClickResetButton();
        }else{
            System.out.println("File Check Passed: Valid file.");

            //Here should be code in GUI to show success message.
            this.onPlayerClickResetButton();
            for(Move move : moves){
                this.onPlayerClickChessPiece(move.getFromPoint());
                if(move.isDoesCapture()){
                    this.onPlayerClickChessPiece(move.getToPoint());
                }else{
                    this.onPlayerClickCell(move.getToPoint());
                }
            }
        }
    }

    @Override
    public boolean onPlayerClickLoginButton(String username, String password) {
        for (User user : allUsers) {
            if(user.getUsername().equals(username) && user.validatePassword(password) && user.getPlayerType() != PlayerType.AI){
                user1 = user;
                return true;
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
    public void onPlayerClickAIMoveButton() {
        //TO DO: possible function? In pvp or net pvp mode, player can click this button to let AI make a move.
        Move AIMove = AI_MCTS.findBestOneMove(model.getGrid(), currentPlayer.getColor());
        this.onPlayerClickChessPiece(AIMove.getFromPoint());
        if(AIMove.isDoesCapture()){
            this.onPlayerClickChessPiece(AIMove.getToPoint());
        }else{
            this.onPlayerClickCell(AIMove.getToPoint());
        }
    }

    @Override
    public boolean onPlayerSelectLocalPVPMode(String username, String password) {
        for (User user : allUsers) {
            if(user.getUsername().equals(username) && user.validatePassword(password) && user.getPlayerType() != PlayerType.AI){
                user2 = user;
                gameMode = GameMode.Local_PVP;
                return true;
            }
        }
        return false;
    }

    @Override
    public void onPlayerSelectLocalPVEMode() {
        for (User user : allUsers) {
            if(user.getPlayerType() == PlayerType.AI){
                user2 = user;
            }
        }
        gameMode = GameMode.PVE;
    }

    @Override
    public void onPlayerExitGameFrame() {
        user2 = null;
        gameMode = null;
        this.onPlayerClickResetButton();
    }

    public void testViaKeyboard(int x,int y){
        ChessboardPoint point = new ChessboardPoint(x,y);
        if(model.getChessPieceAt(point) == null){
            onPlayerClickCell(point);
        }else{
            onPlayerClickChessPiece(point);
        }
        Chessboard.printChessBoard(model.getGrid());
        if(selectedPoint != null){
            System.out.printf("Selected piece is %s at point (%d , %d)\n",model.getChessPieceAt(selectedPoint).getName(),selectedPoint.getRow(),selectedPoint.getCol());
        }else{
            System.out.println("No point is selected");
        }
    }
    public Move AIMoveTest(){
        return AI_MCTS.findBestOneMove(model.getGrid(), currentPlayer.getColor());
    }
}
