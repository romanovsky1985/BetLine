package betline.sport.icehockey;

import betline.core.AbstractGame;

public class IceHockeyGame extends AbstractGame {

    public IceHockeyGame() {
        set("homeScore", 0);
        set("guestScore", 0);
        set("homeExpected", 2.5);
        set("guestExpected", 2.5);
        set("currentSecond", 0);
        set("gameDuration", 3600);
        set("emptyDuration", 70);
        set("emptyDifference", 2);
        set("correction5x6", 7.5);
        set("correction6x5", 1.5);
        set("correction3x3", 2.5);
        set("gameStep", 10);
        set("otDuration", 300);
        set("shootoutLength", 5);
        set("shootoutProbability", 0.35);
    }

    public IceHockeyGame(IceHockeyGame iceHockeyGame) {
        for (String param : iceHockeyGame.params()) {
            set(param, iceHockeyGame.get(param));
        }
    }
}
