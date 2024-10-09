package my.betline.page;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IceHockeyPage {
    private int score1;
    private int score2;
    private double expected1 = 2.5;
    private double expected2 = 2.5;
    private int currentSecond = 0;
    private int emptyDifference = 2;
    private int emptyDuration = 70;
    private double margin = 0.0;

    public int getCurrentMinute() {
        return currentSecond / 60;
    }

    public void setCurrentMinute(int minute) {
        currentSecond = minute * 60;
    }

    public double getMarginPercent() {
        return margin * 100;
    }

    public void setMarginPercent(double percent) {
        margin = percent / 100;
    }
}
