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
            final double ttl = i + 0.5;
            addUnit(new BetUnit<>(
                    "ТМ(" + i + ",5)",
                    game -> game.get("homeScore").intValue() + game.get("guestScore").intValue() < ttl,
                    "ТБ(" + i + ",5)"
            ));
        }

        // Расходная фора
        addUnit(new BetUnit<>(
                "Ф1(0)",
                game -> game.get("homeScore").intValue() > game.get("guestScore").intValue(),
                game -> game.get("homeScore").intValue() < game.get("guestScore").intValue(),
                "Ф2(0)"
        ));

        // Форы
        for (int i = 1; i < 4; i++) {
            final double hcpMinus = -i - 0.5;
            final double hcpPlus = i + 0.5;
            addUnit(new BetUnit<>(
                    "Ф1(" + (-i) + ",5)",
                    game -> game.get("homeScore").intValue() + hcpMinus > game.get("guestScore").intValue(),
                    "Ф2(+" + i + ",5)"
            ));
            addUnit(new BetUnit<>(
                    "Ф1(+" + i + ",5)",
                    game -> game.get("homeScore").intValue() > game.get("guestScore").intValue() + hcpMinus,
                    "Ф2(" + (-i) + ",5)"
            ));
        }

        // Следующий гол
        addUnit(new BetUnit<>(
                "Следующий гол 1",
                game -> game.get("nextScore").intValue() == 1
        ));
        addUnit(new BetUnit<>(
                "Следующий гол 2",
                game -> game.get("nextScore").intValue() == 2
        ));


    }
}
