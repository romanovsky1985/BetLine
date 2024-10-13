package my.betline.sport.football;

import my.betline.sport.core.AbstractGame;

public class FootballGame extends AbstractGame {

    public FootballGame() {
        super();
        set("homeScore", 0);
        set("guestScore", 0);
        set("homeExpected", 1.25);
        set("guestExpected", 1.25);
        set("currentSecond", 0);
        set("gameDuration", 5400);
        set("firstDuration", 2700);
        set("gameAdditional", 300);
        set("firstAdditional", 120);

        set("additionalTime1", 2);
        set("additionalTime2", 5);
        set("homeTeamSize", 11);
        set("guestTeamSize", 11);

        set("correction11x10", 1.5);
        set("correction10x11", 0.75);
        set("timeStep", 30);
        set("drawCorrectionStart", 3600);
        set("drawCorrectionFinal", 0.25);
    }

    public FootballGame(FootballGame footballGame) {
        super(footballGame);
    }
}
