package my.betline.page;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class TimeSportPage {
    private int score1 = 0;
    private int score2 = 0;
    private double expected1 = 0;
    private double expected2 = 0;
    private int currentSecond = 0;
    private double margin = 0.0;
    private Map<String, String> line;

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
