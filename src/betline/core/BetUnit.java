package betline.core;

import java.util.List;
import java.util.function.Predicate;

/*
 * Модель для статистической оценки вероятности и подсчета кф единичной букмекерской позиции.
 */
public class BetUnit<G> {
    private final String text;
    private final Predicate<G> yesRule;
    private final Predicate<G> noRule;

    public BetUnit(String text, Predicate<G> yesRule, Predicate<G> noRule) {
        this.text = text;
        if (yesRule == null) {
            throw new IllegalArgumentException("yesRule can't be a null");
        }
        this.yesRule = yesRule;
        this.noRule = noRule;
    }

    public BetUnit(String text, Predicate<G> yesRule) {
        this(text, yesRule, null);
    }

    public LineUnit calc(List<G> games, double margin) {
        if (games == null) {
            throw new IllegalArgumentException("games can't be a null");
        }
        // раскидывать по потокам будем на уровне LineBuilder
        long yesCount = games.stream().filter(yesRule).count();
        long noCount = noRule == null ? 0
                : games.stream().filter(noRule).count();
        long totalCount = noRule != null ? yesCount + noCount : games.size();
        double yes = (double) yesCount / (double) totalCount;
        double no = 1.0 - yes;
        return new LineUnit(text, (1.0 - margin) / yes, (1.0 - margin) / no);
    }
}
