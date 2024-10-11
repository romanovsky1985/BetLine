package my.betline.page;

import lombok.Getter;
import lombok.Setter;
import my.betline.sport.football.FootballGame;

@Getter
@Setter
public class FootballPage extends TimeSportPage {
    private int additionalTime1 = 2;
    private int additionalTime2 = 5;
    private String teamsSize = "11x11";

    public FootballPage() {
        setExpected1(1.25);
        setExpected2(1.25);
    }

    public FootballGame getGame() {
        FootballGame game = new FootballGame();
        game.set("homeScore", getScore1());
        game.set("guestScore", getScore2());
        game.set("homeExpected", getExpected1());
        game.set("guestExpected", getExpected2());
        game.set("additionalTime1", additionalTime1);
        game.set("additionalTime2", additionalTime2);
        switch (teamsSize) {
            case "10x11":
                game.set("homeTeamSize", 10);
                break;
            case "11x10":
                game.set("guestTeamSize", 10);
                break;
            case "10x10":
                game.set("homeTeamSize", 10);
                game.set("guestTeamSize", 10);
                break;
            default:
                break;
        }
        return game;
    }
}
