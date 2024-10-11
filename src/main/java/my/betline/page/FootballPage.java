package my.betline.page;

import lombok.Getter;
import lombok.Setter;
import my.betline.sport.icehockey.FootballGame;
import my.betline.sport.icehockey.IceHockeyGame;

@Getter
@Setter
public class FootballPage {
    private int score1;
    private int score2;
    private double expected1 = 2.5;
    private double expected2 = 2.5;
    private int currentSecond = 0;
    private int additionalTime1 = 2;
    private int additionalTime2 = 5;
    private String teams = "11x11";
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

    public FootballGame getGame() {
        FootballGame game = new FootballGame();
        game.set("homeScore", score1);
        game.set("guestScore", score2);
        game.set("homeExpected", expected1);
        game.set("guestExpected", expected2);
        game.set("currentSecond", currentSecond);
        game.set("additionalTime1", additionalTime1);
        game.set("additionalTime2", additionalTime2);
        return game;
    }
}
