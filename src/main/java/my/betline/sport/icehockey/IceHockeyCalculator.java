package my.betline.sport.icehockey;

import my.betline.sport.core.BetUnit;
import my.betline.sport.core.LineCalculator;

import java.util.List;
import java.util.concurrent.Executor;

public class IceHockeyCalculator extends LineCalculator<IceHockeyGame> {
    public IceHockeyCalculator(int iterations, double margin, Executor executor) {
        super(new IceHockeyGenerator(), iterations, margin, executor);

        addUnit(new BetUnit<>(
                "П1",
                game -> game.get("homeScore").intValue() > game.get("guestScore").intValue(),
                "Х2"
        ));
        addUnit(new BetUnit<>(
                "Х",
                game -> game.get("homeScore").intValue() == game.get("guestScore").intValue(),
                "12"
        ));
        addUnit(new BetUnit<>(
                "П2",
                game -> game.get("homeScore").intValue() < game.get("guestScore").intValue(),
                "1Х"
        ));
        addUnit(new BetUnit<>(
                "Победа 1 в матче",
                game -> game.get("gameWinner").intValue() == 1,
                "Победа 2 в матче"
        ));

        // Тоталы
        for (int i = 0; i < 10; i++) {
            final int ttl = i;
            addUnit(new BetUnit<>(
                    "ТМ(" + ttl + ",5)",
                    game -> game.get("homeScore").intValue() + game.get("guestScore").intValue() < ttl + 1,
                    "ТБ(" + ttl + ",5)"
            ));
        }

    }
}
