package my.betline.sport.core;

import java.util.List;
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
        this(noText, yesRule, null ,noText);
    }

    public BetUnit(String yesText, Predicate<G> yesRule) {
        this(yesText, yesRule, null, null);
    }

    public Map<String, Double> calc(List<G> games, double margin) {
        if (games == null) {
            throw new IllegalArgumentException("games can't be a null");
        }
        long yesCount = games.stream().filter(yesRule).count();
        long noCount = noRule == null ? 0
                : games.stream().filter(noRule).count();
        long totalCount = noRule != null ? yesCount + noCount : games.size();
        double yes = (double) yesCount / (double) totalCount;
        double no = 1.0 - yes;
        return noText == null ? Map.of(yesText, (1.0 - margin) / yes) :
                Map.of(yesText, (1.0 - margin) / yes, noText, (1.0 - margin) / no);
    }
}
