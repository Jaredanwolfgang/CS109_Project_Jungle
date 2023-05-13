package model.Timer;

import controller.GameController;
import model.Enum.AIDifficulty;
import view.Frame.Frame;

public class Timer extends Thread {
    private int interval;
    private final int period = 45;
    private int remaining;
    private boolean running;
    private final GameController gameController;
    private final Frame view;

    public Timer(GameController gameController, Frame view, int interval) {
        this.remaining = period;
        this.gameController = gameController;
        this.view = view;
        this.running = true;
        this.interval = interval;
    }

    public synchronized void reset() {
        this.remaining = this.period;
        view.getChessGameFrame().getChessboardComponent().refreshGridComponents();
    }

    public synchronized void setInterval(int newInterval) {
        this.interval = newInterval;
        reset();
    }

    public int getInterval() {
        return interval;
    }

    public synchronized void shutdown() {
        this.running = false;
    }

    @Override
    public void run() {
        while (running) {

            try {
                Thread.sleep(interval);
            } catch (InterruptedException ignored) {
                // ignore interruptions
            }

            synchronized (this) {
                if (remaining <= 0) {
                    System.out.println("Time is up!");
                    gameController.gatAIMove(AIDifficulty.EASY);
                    reset();
                } else {
                    view.getChessGameFrame().changeTimeLabel(remaining);
                    if(remaining<=5){
                        view.getChessGameFrame().getChessboardComponent().shine(remaining,this);
                    }

                    if (remaining % 5 == 0) {
                        System.out.println(remaining + " seconds remaining");
                    }
                    remaining--;
                }
            }
        }
    }
}
