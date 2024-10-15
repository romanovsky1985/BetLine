package my.betline.sport.core;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.function.Predicate;

public class BetUnit<G> {
    private final String yesText;
    private final Predicate<G> yesRule;
    private final Predicate<G> noRule;
    private final String noText;

    public BetUnit(String yesText, Predicate<G> yesRule, Predicate<G> noRule, String noText) {
        if (yesText == null) {
            throw new IllegalArgumentException(("yesText can't be a null"));
        }
        this.yesText = yesText;
        if (yesRule == null) {
            throw new IllegalArgumentException("yesRule can't be a null");
        }
        this.yesRule = yesRule;
        this.noRule = noRule;
        this.noText = noText;
    }

    public BetUnit(String yesText, Predicate<G> yesRule, Predicate<G> noRule) {
        this(yesText, yesRule, noRule, null);
    }

    public BetUnit(String yesText, Predicate<G> yesRule, String noText) {
        this(yesText, yesRule, null ,noText);
    }

    public BetUnit(String yesText, Predicate<G> yesRule) {
        this(yesText, yesRule, null, null);
    }

    public Map<String, Double> calc(List<G> games, double margin) {
        if (games == null) {
            throw new IllegalArgumentException("games can't be a null");
        }
        final long yesCount = games.stream().filter(yesRule).count();
        final long noCount = noRule == null ? 0
                : games.stream().filter(noRule).count();
        final long totalCount = noRule != null ? yesCount + noCount : games.size();
        final double yes = (double) yesCount / (double) totalCount;
        final double no = 1.0 - yes;
        return noText == null ? Map.of(yesText, (1 - margin) / yes) :
                Map.of(yesText, (1 - margin) / yes, noText, (1 - margin) / no);
    }

    // фабрики создания популярных исходов

    public static <T extends AbstractGame> BetUnit<T> exactlyTotal(int total) {
        return new BetUnit<T>("ТМ(" + total + ",0)",
            game -> game.get("homeScore").intValue() + game.get("guestScore").intValue() < total,
            game -> game.get("homeScore").intValue() + game.get("guestScore").intValue() > total,
            "ТБ(" + total + ",0)");
    }

    public static <T extends AbstractGame> BetUnit<T> halfTotal(double total) {
        return new BetUnit<T>("ТМ(" + (int) total + ",5)",
            game -> game.get("homeScore").intValue() + game.get("guestScore").intValue() < total,
            "ТБ(" + (int) total + ",5)");
    }

    public static <T extends AbstractGame> List<BetUnit<T>> totals(List<Number> totals) {
        List<BetUnit<T>> units = new ArrayList<>(totals.size());
        for (Number total : totals) {
            if (total.intValue() == total.doubleValue()) {
                units.add(exactlyTotal(total.intValue()));
            } else {
                units.add(halfTotal(total.doubleValue()));
            }
        }
        return units;
    }

    public static <T extends AbstractGame> BetUnit<T> exactlyHandicap(int hcp) {
        if (hcp == 0) {
            return new BetUnit<>("Ф1(0)",
                game -> game.get("homeScore").intValue() > game.get("guestScore").intValue(),
                game -> game.get("homeScore").intValue() < game.get("guestScore").intValue(),
                "Ф2(0)");
        }
        final String home = hcp > 0 ? "+" + hcp : "-" + (-hcp);
        final String guest = hcp > 0 ? "-" + hcp : "+" + (-hcp);
        return new BetUnit<>("Ф1(" + home + ",0)",
            game -> game.get("homeScore").intValue() + hcp > game.get("guestScore").intValue(),
            game -> game.get("homeScore").intValue() + hcp < game.get("guestScore").intValue(),
            "Ф2(" + guest + ",0)"); 
    }

    public static <T extends AbstractGame> BetUnit<T> halfHandicap(double hcp) {
        final String home = hcp > 0 ? "+" + (int) hcp : "-" + (- (int) hcp);
        final String guest = hcp > 0 ? "-" + (int) hcp : "+" + (- (int) hcp);
        return new BetUnit<>("Ф1(" + home + ",5)",
            game -> game.get("homeScore").intValue() + hcp > game.get("guestScore").intValue(),
            "Ф2(" + guest + ",5)");
    }

    public static <T extends AbstractGame> List<BetUnit<T>> handicaps(List<Number> handicaps) {
        final List<BetUnit<T>> units = new ArrayList<>(handicaps.size());
        for (Number hcp : handicaps) {
            if (hcp.intValue() == hcp.doubleValue()) {
                units.add(exactlyHandicap(hcp.intValue()));
            } else {
                units.add(halfHandicap(hcp.doubleValue()));
            }
        }
        return units;
    }

    public static <T extends AbstractGame> List<BetUnit<T>> threeWay() {
        return List.of(
            new BetUnit<>("П1", game -> game.get("homeScore").intValue() > game.get("guestScore").intValue(), "Х2"),
            new BetUnit<>("Х", game -> game.get("homeScore").intValue() == game.get("guestScore").intValue(), "12"),
            new BetUnit<>("П2", game -> game.get("homeScore").intValue() < game.get("guestScore").intValue(), "1Х")
        );
    }

    public static <T extends AbstractGame> List<BetUnit<T>> nextScore() {
        return List.of(
            new BetUnit<T>("Следующий гол 1", game -> game.get("nextScore").intValue() == 1, "Следующий гол 1 нет"),
            new BetUnit<T>("Следующий гол 2", game -> game.get("nextScore").intValue() == 2, "Следующий гол 2 нет")
        );
    }

    public static <T extends  AbstractGame> List<BetUnit<T>> personalTotals(List<Number> totals) {
        List<BetUnit<T>> units = new ArrayList<>(2 * totals.size());
        for (Number total : totals) {
            if (total.intValue() == total.doubleValue()) {
                units.add(new BetUnit<>("ИТМ1(" + total.intValue() + ",0)",
                        game -> game.get("homeScore").intValue() < total.intValue(),
                        game -> game.get("homeScore").intValue() > total.intValue(),
                        "ИТБ1(" + total.intValue() + ",0)"));
                units.add(new BetUnit<>("ИТМ2(" + total.intValue() + ",0)",
                        game -> game.get("guestScore").intValue() < total.intValue(),
                        game -> game.get("guestScore").intValue() > total.intValue(),
                        "ИТБ2(" + total.intValue() + ",0)"));
            } else {
                units.add(new BetUnit<>("ИТМ1(" + total.intValue() + ",5)",
                        game -> game.get("homeScore").doubleValue() < total.doubleValue(),
                        "ИТБ1(" + total.intValue() + ",5)"));
                units.add(new BetUnit<>("ИТМ2(" + total.intValue() + ",5)",
                        game -> game.get("guestScore").doubleValue() < total.doubleValue(),
                        "ИТБ2(" + total.intValue() + ",5)"));
            }
        }
        return units;
    }
}
