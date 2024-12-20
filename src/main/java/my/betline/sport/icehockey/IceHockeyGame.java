package my.betline.sport.icehockey;

import my.betline.sport.core.AbstractGame;

public class IceHockeyGame extends AbstractGame {

    public IceHockeyGame() {
        super();
        set("homeScore", 0);
        set("guestScore", 0);
        set("homeExpected", 2.5);
        set("guestExpected", 2.5);
        set("currentSecond", 0);
        set("gameDuration", 3600);
        set("periodDuration", 1200);
        set("emptyDuration", 70);
        set("emptyDifference", 2);
        set("correction5x6", 8.5);
        set("correction6x5", 1.5);
        set("correction3x3", 2.5);
        set("correctionOt", 2.2);
        set("timeStep", 10);
        set("drawCorrectionStart", 2400);
        set("drawCorrectionFinal", 0.27);
        set("otDuration", 300);
        set("shootoutLength", 5);
        set("shootoutProbability", 0.35);
        set("correctionP1", 0.29 / 0.333);
        set("correctionP2", 0.36 / 0.333);
        set("correctionP3", 0.35 / 0.333);
        // шайбы второго периода
        set("home2nd", 0);
        set("guest2nd", 0);
    }

    public IceHockeyGame(IceHockeyGame iceHockeyGame) {
        super(iceHockeyGame);
    }
}
