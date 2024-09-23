package betline.sport.icehockey;

/*
 * Набор параметров для хоккейного матча, на основе
 * которого осуществляется моделирование в генераторе.
 */

public class IceHockeyConfig {
    private double homeExpected = 2.5;
    private double guestExpected = 2.5;
    private int currentSecond = 0;
    private int emptyDifference = 2;
    private int emptyDuration = 90;
    private int gameDuration = 3600;
    private int gameWithOtDuration = 3900;

    private double correction3x3 = 2.5;
    private double correction5x6 = 7.5;
    private double correction6x5 = 1.5;

    private int shootoutLength = 5;
    private double shootoutScoreProbability = 0.35;


    public double getHomeExpected() {
        return homeExpected;
    }

    public void setHomeExpected(double homeExpected) {
        this.homeExpected = homeExpected;
    }

    public double getGuestExpected() {
        return guestExpected;
    }

    public void setGuestExpected(double guestExpected) {
        this.guestExpected = guestExpected;
    }

    public int getCurrentSecond() {
        return currentSecond;
    }

    public void setCurrentSecond(int currentSecond) {
        this.currentSecond = currentSecond;
    }

    public int getEmptyDifference() {
        return emptyDifference;
    }

    public void setEmptyDifference(int emptyDifference) {
        this.emptyDifference = emptyDifference;
    }

    public int getEmptyDuration() {
        return emptyDuration;
    }

    public void setEmptyDuration(int emptyDuration) {
        this.emptyDuration = emptyDuration;
    }

    public int getGameDuration() {
        return gameDuration;
    }

    public void setGameDuration(int gameDuration) {
        this.gameDuration = gameDuration;
    }

    public int getGameWithOtDuration() {
        return gameWithOtDuration;
    }

    public void setGameWithOtDuration(int gameWithOtDuration) {
        this.gameWithOtDuration = gameWithOtDuration;
    }

    public double getCorrection3x3() {
        return correction3x3;
    }

    public void setCorrection3x3(double correction3x3) {
        this.correction3x3 = correction3x3;
    }

    public double getCorrection5x6() {
        return correction5x6;
    }

    public void setCorrection5x6(double correction5x6) {
        this.correction5x6 = correction5x6;
    }

    public double getCorrection6x5() {
        return correction6x5;
    }

    public void setCorrection6x5(double correction6x5) {
        this.correction6x5 = correction6x5;
    }

    public int getShootoutLength() {
        return shootoutLength;
    }

    public void setShootoutLength(int shootoutLength) {
        this.shootoutLength = shootoutLength;
    }

    public double getShootoutScoreProbability() {
        return shootoutScoreProbability;
    }

    public void setShootoutScoreProbability(double shootoutScoreProbability) {
        this.shootoutScoreProbability = shootoutScoreProbability;
    }



}
