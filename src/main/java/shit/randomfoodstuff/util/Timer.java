package shit.randomfoodstuff.util;

public class Timer {

    private int timeRemaining;
    private int startingTime;

    public Timer(int seconds) {
        this.timeRemaining = seconds * 20;
        this.startingTime = seconds * 20;
    }

    public Timer() {
    }

    public int getTimeRemaining() {
        return timeRemaining;
    }

    public int getStartingTime() {
        return startingTime;
    }

    public void setTimeRemaining(int timeRemaining) {
        this.timeRemaining = timeRemaining;
    }

    public boolean isFinished() {
        return timeRemaining <= 0;
    }

    public void update() {
        timeRemaining--;
    }

    public void setToZero() {
        this.timeRemaining = 0;
    }

    public void refresh() {
        this.timeRemaining = startingTime;
    }

    public void setTo(int startingTime, int timeRemaining) {
        this.startingTime = startingTime;
        this.timeRemaining = timeRemaining;
    }
}
