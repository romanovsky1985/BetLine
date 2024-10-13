package my.betline.sport.football;

import my.betline.sport.core.BetUnit;
import my.betline.sport.core.LineCalculator;

import java.util.concurrent.Executor;
import java.util.List;

public class FootballCalculator  extends LineCalculator<FootballGame> {

    public FootballCalculator(int iterations, double margin, Executor executor) {
        super(new FootballGenerator(), iterations, margin, executor);

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
        // тоталы
        addUnits(BetUnit.<FootballGame>halfTotals(List.of(0.5, 1.5, 2.5, 3.5, 4.5, 5.5)));
        addUnits(BetUnit.<FootballGame>exactlyTotals(List.of(1, 2, 3, 4, 5)));
        // инд тоталы
        // следующий гол
        addUnit(new BetUnit<>(
                "Следующий гол 1",
                game -> game.get("nextScore").intValue() == 1,
                "Следующий гол 1 нет"
        ));
        addUnit(new BetUnit<>(
                "Следующий гол 2",
                game -> game.get("nextScore").intValue() == 2,
                "Следующий гол 2 нет"
        ));
        // форы
        for (int i = 1; i < 4; i++) {
            final double hcp = -i - 0.5;
            addUnit(new BetUnit<>(
                    "Ф1(" + (-i) + ",5)",
                    game -> game.get("homeScore").intValue() + hcp > game.get("guestScore").intValue(),
                    "Ф2(+" + i + ",5)"
            ));
            addUnit(new BetUnit<>(
                    "Ф1(+" + i + ",5)",
                    game -> game.get("homeScore").intValue() > game.get("guestScore").intValue() + hcp,
                    "Ф2(" + (-i) + ",5)"
            ));
            final int ihcp = -i;
            addUnit(new BetUnit<>(
                    "Ф1(" + (-i) + ",0)",
                    game -> game.get("homeScore").intValue() + ihcp > game.get("guestScore").intValue(),
                    game -> game.get("homeScore").intValue() + ihcp < game.get("guestScore").intValue(),
                    "Ф2(+" + i + ",0)"
            ));
            addUnit(new BetUnit<>(
                    "Ф1(+" + i + ",0)",
                    game -> game.get("homeScore").intValue() > game.get("guestScore").intValue() + ihcp,
                    game -> game.get("homeScore").intValue() < game.get("guestScore").intValue() + ihcp,
                    "Ф2(" + (-i) + ",0)"
            ));
        }
        addUnit(new BetUnit<>(
                "Ф1(0)",
                game -> game.get("homeScore").intValue() > game.get("guestScore").intValue(),
                game -> game.get("homeScore").intValue() < game.get("guestScore").intValue(),
                "Ф2(0)"
        ));

    }
}

    /*
        // инд тоталы
        for (int i = 0; i < 5; i++) {
            final double ttl = i + 0.5;
            addUnit(new BetUnit<>(
                    "ИТМ1(" + i + ",5)",
                    game -> game.get("homeScore").intValue() < ttl,
                    "ИТБ1(" + i + ",5)"
            ));
            addUnit(new BetUnit<>(
                    "ИТМ2(" + i + ",5)",
                    game -> game.get("guestScore").intValue() < ttl,
                    "ИТБ2(" + i + ",5)"
            ));
        }


    }
     */
