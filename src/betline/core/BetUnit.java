package betline.core;
/*
 * Модель для статистической оценки вероятности и подсчета кф единичной букмекерской позиции.
 * Если не задается предикат для обсчета НЕТ, то вероятности считаются от общего количества.
 * Если задается, то от количества ДА и НЕТ (что важно для расходных значенией).
 * Параметр G - тип матчей, из массива которых оценивается вероятность и считается кф
 */

import java.util.List;
import java.util.function.Predicate;

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

    public LineUnit calcLineUnit(List<G> games, double margin) {
        if (games == null) {
            throw new IllegalArgumentException("games can't be a null");
        }
        long yesCount = games.parallelStream().unordered()
                .filter(yesRule).count();
        long noCount = noRule == null ? 0 : games.parallelStream().unordered()
                .filter(noRule).count();
        long totalCount = noRule != null ? yesCount + noCount : games.size();
        double yes = (double) yesCount / (double) totalCount;
        double no = 1.0 - yes;
        return new LineUnit(text, (1.0 - margin) / yes, (1.0 - margin) / no);
    }
}
