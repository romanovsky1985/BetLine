package my.betline.sport.football;

import my.betline.sport.core.BetUnit;
import my.betline.sport.core.LineCalculator;

import java.util.concurrent.Executor;

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
        // Тоталы
        for (int i = 0; i < 6; i++) {
            final double ttl = i + 0.5;
            addUnit(new BetUnit<>(
                    "ТМ(" + i + ",5)",
                    game -> game.get("homeScore").intValue() + game.get("guestScore").intValue() < ttl,
                    "ТБ(" + i + ",5)"
            ));
            final int ittl = i;
            addUnit(new BetUnit<>(
                    "ТМ(" + i + ",0)",
                    game -> game.get("homeScore").intValue() + game.get("guestScore").intValue() < ittl,
                    game -> game.get("homeScore").intValue() + game.get("guestScore").intValue() > ittl,
                    "ТБ(" + i + ",0)"
            ));
        }
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

        // Расходная фора
        addUnit(new BetUnit<>(
                "Ф1(0)",
                game -> game.get("homeScore").intValue() > game.get("guestScore").intValue(),
                game -> game.get("homeScore").intValue() < game.get("guestScore").intValue(),
                "Ф2(0)"
        ));

    }
     */
