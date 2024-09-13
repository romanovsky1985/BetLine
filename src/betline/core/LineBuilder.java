package betline.core;
/*
 * Класс для формирования линии - списка единичных позиций
 * G - тип матчей для которых строим линию (например, хоккей)
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

public class LineBuilder<G> {
    private final int iterations;
    private final double margin;
    private final List<BetUnit<G>> betUnits = new ArrayList<>();
    private final Function<G, G> gameGenerator;

    public LineBuilder(Function<G, G> gameGenerator, int iterations, double margin) {
        if (gameGenerator == null) {
            throw new IllegalArgumentException("gameGenerator can't be a null");
        }
        this.gameGenerator = gameGenerator;
        this.iterations = iterations;
        this.margin = margin;
    }

    public LineBuilder(Function<G, G> gameGenerator) {
        this(gameGenerator, 10_000, 0.05);
    }

    public List<LineUnit> buildLine(G currentGame) {
        List<G> games = Collections.nCopies(iterations, currentGame)
                .parallelStream().unordered().map(gameGenerator).toList();
        return betUnits.parallelStream().unordered()
                .flatMap(u -> u.calc(games , margin).stream()).toList();
    }

    public void addUnit(BetUnit<G> betUnit) {
        if (betUnit == null) {
            throw new IllegalArgumentException("betUnit can't be a null");
        }
        betUnits.add(betUnit);
    }
}
