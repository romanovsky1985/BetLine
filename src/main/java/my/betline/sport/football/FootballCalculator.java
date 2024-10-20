package my.betline.sport.football;

import my.betline.sport.core.BetUnit;
import my.betline.sport.core.LineCalculator;

import java.util.concurrent.Executor;
import java.util.List;

public class FootballCalculator  extends LineCalculator<FootballGame> {

    public FootballCalculator(int iterations, double margin, Executor executor) {
        super(new FootballGenerator(), iterations, margin, executor);

        addUnits(BetUnit.<FootballGame>threeWay());
        addUnits(BetUnit.<FootballGame>totals(
                List.of(0.5, 1, 1.5, 2, 2.5, 3, 3.5, 4, 4.5, 5, 5.5)));
        addUnits(BetUnit.<FootballGame>nextScore());
        addUnits(BetUnit.<FootballGame>handicaps(
                List.of(-3.5, -3, -2.5, -2, -1.5, -1, 0, 1, 1.5, 2, 2.5, 3, 3.5)));
        addUnits(BetUnit.<FootballGame>personalTotals(
                List.of(0.5, 1, 1.5, 2, 2.5)));
    }
}

