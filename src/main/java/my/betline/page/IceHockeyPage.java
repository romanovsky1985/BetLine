package my.betline.page;

import lombok.Getter;
import lombok.Setter;
import my.betline.sport.icehockey.IceHockeyGame;

@Getter
@Setter
public class IceHockeyPage extends TimeSportPage {
    private int emptyDifference = 2;
    private int emptyDuration = 70;

    public IceHockeyPage() {
      setExpected1(2.5);
      setExpected2(2.5);
    }

    public IceHockeyGame getGame() {
        IceHockeyGame game = new IceHockeyGame();
        game.set("homeScore", getScore1());
        game.set("guestScore", getScore2());
        game.set("homeExpected", getExpected1());
        game.set("guestExpected", getExpected2());
        game.set("currentSecond", getCurrentSecond());
        game.set("emptyDifference", emptyDifference);
        game.set("emptyDuration", emptyDuration);
        return game;
    }
}
