package my.betline.sport.core;

import java.util.HashMap;
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

    // фабрики создания популярных исходов в линии

    public static <T extends AbstractGame> BetUnit<T> exactlyTotal(int total) {
        return new BetUnit<T>("ТМ(" + total + ",0)",
            game -> game.get("homeScore").intValue() + game.get("guestScore").intValue() < total,
            game -> game.get("homeScore").intValue() + game.get("guestScore").intValue() > total,
            "ТБ(" + total + ",0)");
    }

    public static <T extends AbstractGame> List<BetUnit<T>> exactlyTotals(List<Integer> totals) {
        List<BetUnit<T>> units = new ArrayList<>(totals.size());
        for (int total : totals) {
            units.add(exactlyTotal(total));
        }
        return units;
    }

    public static <T extends AbstractGame> BetUnit<T> halfTotal(double total) {
        return new BetUnit<T>("ТМ(" + (int) total + ",5)",
            game -> game.get("homeScore").intValue() + game.get("guestScore").intValue() < total,
            "ТБ(" + (int) total + ",5)");
    }

    public static <T extends AbstractGame> List<BetUnit<T>> halfTotals(List<Double> totals) {
        List<BetUnit<T>> units = new ArrayList<>(totals.size());
        for (double total : totals) {
            units.add(halfTotal(total));
        }
        return units;
    }
}
