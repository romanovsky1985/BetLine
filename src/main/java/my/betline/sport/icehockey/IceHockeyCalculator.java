package my.betline.sport.icehockey;

import my.betline.sport.core.BetUnit;
import my.betline.sport.core.LineCalculator;

import java.util.List;
import java.util.concurrent.Executor;

public class IceHockeyCalculator extends LineCalculator<IceHockeyGame> {
    public IceHockeyCalculator(int iterations, double margin, Executor executor) {
        super(new IceHockeyGenerator(), iterations, margin, executor);

        addUnits(BetUnit.<IceHockeyGame>threeWay());
        // победа в матче
        addUnit(new BetUnit<>(
                "Победа 1 в матче",
                game -> game.get("gameWinner").intValue() == 1,
                "Победа 2 в матче"
        ));
        addUnits(BetUnit.<IceHockeyGame>totals(
                List.of(0.5, 1.5, 2.5, 3.5, 4.5, 5.5, 6.5, 7.5, 8.5, 9.5, 10.5)));
        addUnits(BetUnit.<IceHockeyGame>personalTotals(
                List.of(0.5, 1.5, 2.5, 3.5, 4.5)));
        addUnits(BetUnit.<IceHockeyGame>handicaps(
                List.of(-5.5, -4.5, -3.5, -2.5, -1.5, 0, 1.5, 2.5, 3.5, 4.5, 5.5)));
        addUnits(BetUnit.<IceHockeyGame>nextScore());
        // овертайм и буллиты
        addUnit(new BetUnit<>(
                "Победа 1 в овертайме",
                game -> game.get("otWinner").intValue() == 1,
                "Победа 1 в овертайме нет"
        ));
        addUnit(new BetUnit<>(
                "Победа 2 в овертайме",
                game -> game.get("otWinner").intValue() == 2,
                "Победа 2 в овертайме нет"
        ));
        addUnit(new BetUnit<>(
                "Будут буллиты",
                game -> game.get("otWinner").intValue() == 3,
                "Будут буллиты нет"
        ));
        addUnit(new BetUnit<>(
                "Победа 1 по буллитам",
                game -> game.get("shWinner").intValue() == 1,
                "Победа 1 по буллитам нет"
        ));
        addUnit(new BetUnit<>(
                "Победа 2 по буллитам",
                game -> game.get("shWinner").intValue() == 2,
                "Победа 2 по буллитам нет"
        ));
    }
}
