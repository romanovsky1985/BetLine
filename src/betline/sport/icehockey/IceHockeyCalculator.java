package betline.sport.icehockey;

import betline.core.BetUnit;
import betline.core.LineCalculator;

import java.util.concurrent.Executor;

public class IceHockeyCalculator extends LineCalculator<IceHockeyGame> {
    public IceHockeyCalculator(int iterations, double margin, Executor executor) {
        super(new IceHockeyGenerator(), iterations, margin, executor);

        addUnit(new BetUnit<>(
                "П1",
                game -> game.get("homeScore").intValue() > game.get("guestScore").intValue()
        ));
        addUnit(new BetUnit<>(
                "Х",
                game -> game.get("homeScore").intValue() == game.get("guestScore").intValue()
        ));
        addUnit(new BetUnit<>(
                "П2",
                game -> game.get("homeScore").intValue() < game.get("guestScore").intValue()
        ));
        addUnit(new BetUnit<>(
                "Победа в 1 матче",
                game -> game.get("gameWinner").intValue() == 1
        ));
        addUnit(new BetUnit<>(
                "Победа 2 в матче",
                game -> game.get("gameWinner").intValue() == 2
        ));
    }
}
