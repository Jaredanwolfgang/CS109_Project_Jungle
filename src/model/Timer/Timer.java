package model.Timer;

import controller.GameController;
import model.Enum.AIDifficulty;
import view.Frame.Frame;

public class Timer extends Thread {
    private int interval;
    private final int period = 45;
    private int remaining;
    private boolean running;
    private GameController gameController;
    private Frame view;

    public Timer(GameController gameController, Frame view,int interval) {
        this.remaining = period;
        this.gameController = gameController;
        this.view = view;
        this.running = true;
        this.interval = interval;
    }

    public synchronized void reset() {
        this.remaining = this.period;
    }
    public synchronized void setInterval(int newInterval) {
        this.interval = newInterval;
        reset();
    }

    public synchronized void shutdown() {
        this.running = false;
    }

    @Override
    public void run() {
        while (running) {
            try {
                Thread.sleep(interval);
            } catch (InterruptedException e) {
                // ignore interruptions
            }

            synchronized (this) {
                if (remaining <= 0) {
                    System.out.println("Time is up!");

                    AIDifficulty temp = GameController.aiDifficulty;
                    GameController.aiDifficulty = AIDifficulty.EASY;
                    gameController.gatAIMove();
                    GameController.aiDifficulty = temp;

                    reset();
                } else {
                    if(remaining % 5 == 0) {
                        System.out.println(remaining + " seconds remaining");
                    }
                    remaining--;
                }
            }
        }
    }
}
