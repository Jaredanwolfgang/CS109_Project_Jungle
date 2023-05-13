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
            //To show the timer countdown, I have implemented the method here.
            view.getChessGameFrame().changeTimeLabel(remaining);

            //This helps to ensure all time interval counts for 1 second.
            if(remaining<=5){
                view.getChessGameFrame().getChessboardComponent().shine(remaining,this);
            } else{
                try {
                    Thread.sleep(interval);
                } catch (InterruptedException e) {
                    // ignore interruptions
                }
            }
            view.getChessGameFrame().getChessboardComponent().refreshGridComponents();

            synchronized (this) {
                if (remaining <= 0) {
                    System.out.println("Time is up!");

                    AIDifficulty temp = GameController.aiDifficulty;
                    GameController.aiDifficulty = AIDifficulty.EASY;
                    gameController.gatAIMove();
                    GameController.aiDifficulty = temp;

                    reset();
                } else {
                    if (remaining % 5 == 0) {
                        System.out.println(remaining + " seconds remaining");
                    }
                    remaining--;
                }
            }
        }
    }
}
