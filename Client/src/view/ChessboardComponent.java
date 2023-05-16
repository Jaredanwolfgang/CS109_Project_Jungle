package view;


import controller.GameController;
import listener.HoverListener;
import model.ChessBoard.Cell;
import model.ChessBoard.Chessboard;
import model.ChessBoard.ChessboardPoint;
import model.ChessPieces.ChessPiece;
import model.Enum.Numbers;
import model.Enum.Seasons;
import view.ChessComponent.*;
import view.Frame.ChessGameFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.HashSet;
import java.util.Set;

import static model.Enum.Constant.CHESSBOARD_COL_SIZE;
import static model.Enum.Constant.CHESSBOARD_ROW_SIZE;

/**
 * This class represents the checkerboard component object on the panel
 */
public class ChessboardComponent extends JComponent {
    private final CellComponent[][] gridComponents = new CellComponent[CHESSBOARD_ROW_SIZE.getNum()][CHESSBOARD_COL_SIZE.getNum()];
    private int CHESS_SIZE;
    private final Set<ChessboardPoint> riverCell = new HashSet<>();
    private final Set<ChessboardPoint> trapCell = new HashSet<>();
    private final Set<ChessboardPoint> densCell = new HashSet<>();
    private GameController gameController;
    private Seasons season = Seasons.SPRING;

    public ChessboardComponent(int chessSize) {
        CHESS_SIZE = chessSize;
        int width = CHESS_SIZE * 7;
        int height = CHESS_SIZE * 9;
        enableEvents(AWTEvent.MOUSE_EVENT_MASK);// Allow mouse events to occur
        setLayout(null); // Use absolute layout.
        setSize(width, height);
        //System.out.printf("chessboard width, height = [%d : %d], chess size = %d\n", width, height, CHESS_SIZE);

        initSet();
        initiateGridComponents();
    }

    /**
     * This method represents how to initiate ChessComponent
     * according to Chessboard information
     */
    public void registerGameController(GameController gameController) {
        this.gameController = gameController;
    }
    public void initSet() {
        riverCell.add(new ChessboardPoint(3, 1));
        riverCell.add(new ChessboardPoint(3, 2));
        riverCell.add(new ChessboardPoint(4, 1));
        riverCell.add(new ChessboardPoint(4, 2));
        riverCell.add(new ChessboardPoint(5, 1));
        riverCell.add(new ChessboardPoint(5, 2));

        riverCell.add(new ChessboardPoint(3, 4));
        riverCell.add(new ChessboardPoint(3, 5));
        riverCell.add(new ChessboardPoint(4, 4));
        riverCell.add(new ChessboardPoint(4, 5));
        riverCell.add(new ChessboardPoint(5, 4));
        riverCell.add(new ChessboardPoint(5, 5));

        trapCell.add(new ChessboardPoint(0, 2));
        trapCell.add(new ChessboardPoint(0, 4));
        trapCell.add(new ChessboardPoint(1, 3));

        trapCell.add(new ChessboardPoint(8, 2));
        trapCell.add(new ChessboardPoint(8, 4));
        trapCell.add(new ChessboardPoint(7, 3));

        densCell.add(new ChessboardPoint(0, 3));
        densCell.add(new ChessboardPoint(8, 3));
    }
    public void initiateChessComponent(Chessboard chessboard) {
        Cell[][] grid = chessboard.getGrid();
        for (int i = 0; i < CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < CHESSBOARD_COL_SIZE.getNum(); j++) {
                //Implement the initialization checkerboard
                if (grid[i][j].getPiece() != null) {
                    ChessPiece chessPiece = grid[i][j].getPiece();
                    switch (chessPiece.getCategory()) {
                        case ELEPHANT ->
                                gridComponents[i][j].add(new ElephantChessComponent(chessPiece.getOwner(), CHESS_SIZE));
                        case LION ->
                                gridComponents[i][j].add(new LionChessComponent(chessPiece.getOwner(), CHESS_SIZE));
                        case TIGER ->
                                gridComponents[i][j].add(new TigerChessComponent(chessPiece.getOwner(), CHESS_SIZE));
                        case LEOPARD ->
                                gridComponents[i][j].add(new LeopardChessComponent(chessPiece.getOwner(), CHESS_SIZE));
                        case WOLF ->
                                gridComponents[i][j].add(new WolfChessComponent(chessPiece.getOwner(), CHESS_SIZE));
                        case DOG -> gridComponents[i][j].add(new DogChessComponent(chessPiece.getOwner(), CHESS_SIZE));
                        case CAT -> gridComponents[i][j].add(new CatChessComponent(chessPiece.getOwner(), CHESS_SIZE));
                        case RAT -> gridComponents[i][j].add(new RatChessComponent(chessPiece.getOwner(), CHESS_SIZE));
                    }
                }
            }
        }
    }
    public void initiateGridComponents() {
        for (int i = 0; i < CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < CHESSBOARD_COL_SIZE.getNum(); j++) {
                ChessboardPoint temp = new ChessboardPoint(i, j);
                CellComponent cell;
                if (riverCell.contains(temp)) {
                    cell = new CellComponent(season.getColors()[1], season.getColors()[5], season.getColors()[4], calculatePoint(i, j), CHESS_SIZE);
                } else if (trapCell.contains(temp)) {
                    cell = new CellComponent(season.getColors()[2], season.getColors()[5], season.getColors()[4], calculatePoint(i, j), CHESS_SIZE);
                } else if (densCell.contains(temp)) {
                    cell = new CellComponent(season.getColors()[3], season.getColors()[5], season.getColors()[4], calculatePoint(i, j), CHESS_SIZE);
                } else {
                    cell = new CellComponent(season.getColors()[0], season.getColors()[5], season.getColors()[4], calculatePoint(i, j), CHESS_SIZE);
                }
                this.add(cell);
                gridComponents[i][j] = cell;
                cell.setHoverListener(new HoverListener() {
                    @Override
                    public void onHovered(CellComponent cellComponent) {
                        // Handle hover event here
                        cell.setHovered(true);
                        repaint();
                    }

                    @Override
                    public void onExited(CellComponent cellComponent) {
                        // Handle exit event here
                        cell.setHovered(false);
                        repaint();
                    }
                });
            }
        }
    }

    public void refreshGridComponents() {
        for (int i = 0; i < CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < CHESSBOARD_COL_SIZE.getNum(); j++) {
                ChessboardPoint temp = new ChessboardPoint(i, j);
                CellComponent cell = getGridComponentAt(temp);
                cell.setTimerMode(false);
                if (riverCell.contains(temp)) {
                    cell.setBackground(season.getColors()[1], season.getColors()[5], season.getColors()[4]);
                } else if (trapCell.contains(temp)) {
                    cell.setBackground(season.getColors()[2], season.getColors()[5], season.getColors()[4]);
                } else if (densCell.contains(temp)) {
                    cell.setBackground(season.getColors()[3], season.getColors()[5], season.getColors()[4]);
                } else {
                    cell.setBackground(season.getColors()[0], season.getColors()[5], season.getColors()[4]);
                }
                repaint();
                cell.setHoverListener(new HoverListener() {
                    @Override
                    public void onHovered(CellComponent cellComponent) {
                        // Handle hover event here
                        cell.setHovered(true);
                        repaint();
                    }

                    @Override
                    public void onExited(CellComponent cellComponent) {
                        // Handle exit event here
                        cell.setHovered(false);
                        repaint();
                    }
                });
            }
        }
    }

    public void changeSize (int chessSize){
        removeAll();
        CHESS_SIZE = chessSize;
        setSize(CHESS_SIZE * 7, CHESS_SIZE * 9);
        Chessboard chessboard = gameController.getModel();

        initiateGridComponents();
        initiateChessComponent(chessboard);
        revalidate();
        repaint();
    }

    public void shine(int remaining, model.Timer.Timer timer) {
        new Thread(() -> {
            int[][] shineCellComponent = switch (remaining) {
                case 0 -> Numbers.ZERO;
                case 1 -> Numbers.ONE;
                case 2 -> Numbers.TWO;
                case 3 -> Numbers.THREE;
                case 4 -> Numbers.FOUR;
                case 5 -> Numbers.FIVE;
                default -> null;
            };
            for (int[] ints : shineCellComponent) {
                ChessboardPoint temp = new ChessboardPoint(ints[0], ints[1]);
                CellComponent cell = getGridComponentAt(temp);
                cell.setTimerMode(true);
                repaint();
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    // ignore interruptions
                }
            }
            try {
                Thread.sleep(timer.getInterval() - shineCellComponent.length * 20L - 250);
            } catch (InterruptedException e) {
                // ignore interruptions
            }
            refreshGridComponents();
        }).start();
    }

    public void setChessComponentAtGrid(ChessboardPoint point, ChessComponent chess) {
        getGridComponentAt(point).add(chess);
    }

    public ChessComponent removeChessComponentAtGrid(ChessboardPoint point) {
        // Note re-validation is required after remove / removeAll.
        ChessComponent chess = (ChessComponent) getGridComponentAt(point).getComponents()[0];
        getGridComponentAt(point).removeAll();
        getGridComponentAt(point).revalidate();
        chess.setSelected(false);
        return chess;
    }

    public CellComponent getGridComponentAt(ChessboardPoint point) {
        return gridComponents[point.getRow()][point.getCol()];
    }

    private ChessboardPoint getChessboardPoint(Point point) {
//        System.out.println("[" + point.y / CHESS_SIZE + ", " + point.x / CHESS_SIZE + "] Clicked");
        return new ChessboardPoint(point.y / CHESS_SIZE, point.x / CHESS_SIZE);
    }

    private Point calculatePoint(int row, int col) {
        return new Point(col * CHESS_SIZE, row * CHESS_SIZE);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }

    @Override
    protected void processMouseEvent(MouseEvent e) {
        if(ChessGameFrame.enabled) {
            if (e.getID() == MouseEvent.MOUSE_PRESSED) {
                JComponent clickedComponent = (JComponent) getComponentAt(e.getX(), e.getY());
                if (clickedComponent.getComponentCount() == 0) {
                    System.out.print("None chess here and ");
                    gameController.onPlayerClickCell(getChessboardPoint(e.getPoint()), gameController.getColorOfUser());
                } else {
                    System.out.print("One chess here and ");
                    gameController.onPlayerClickChessPiece(getChessboardPoint(e.getPoint()), gameController.getColorOfUser());
                }
            }
        }
    }

    public Seasons getSeason() {
        return season;
    }

    public void setSeason(Seasons season) {
        this.season = season;
    }

    public void unloadAllListener() {
        for (int i = 0; i < CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < CHESSBOARD_COL_SIZE.getNum(); j++) {
                gridComponents[i][j].removeMouseListener(gridComponents[i][j].getMouseListeners()[0]);
            }
        }
    }
}
