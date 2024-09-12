package betline.sport.icehockey;

/*
 * Набор параметров для хоккейного матча, на основе которого осуществляется моделирование.
 */

public class IceHockeyConfig {
    private double homeExpected = 2.5;
    private double guestExpected = 2.5;
    private int currentSecond = 0;
    private int emptyDifference = 2;
    private int emptyDuration = 90;
    private int gameDuration = 3600;

    public int getGameDuration() {
        return gameDuration;
    }

    public void setGameDuration(int gameDuration) {
        this.gameDuration = gameDuration;
    }




    public void setHomeExpected(double homeExpected) {
        this.homeExpected = homeExpected;
    }

    public void setGuestExpected(double guestExpected) {
        this.guestExpected = guestExpected;
    }

    public void setCurrentSecond(int currentSecond) {
        this.currentSecond = currentSecond;
    }

    public void setEmptyDifference(int emptyDifference) {
        this.emptyDifference = emptyDifference;
    }

    public void setEmptyDuration(int emptyDuration) {
        this.emptyDuration = emptyDuration;
    }

    public double getHomeExpected() {
        return homeExpected;
    }

    public double getGuestExpected() {
        return guestExpected;
    }

    public int getCurrentSecond() {
        return currentSecond;
    }

    public int getEmptyDifference() {
        return emptyDifference;
    }

    public int getEmptyDuration() {
        return emptyDuration;
    }
}
