package betline.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LineCalculator<G> {
    private final int iterations;
    private final double margin;
    private final List<BetUnit<G>> betUnits = new ArrayList<>();


    public LineCalculator(int iterations, double margin) {
        this.iterations = iterations;
        this.margin = margin;
    }

    public List<LineUnit> calcLine(GameGenerator<G> generator, G game) {
        List<G> games = Collections.nCopies(iterations, game)
                .stream().map(generator::generate).toList();
        return betUnits.stream().map(u -> u.calc(games , margin)).toList();
    }

    public void addUnit(BetUnit<G> betUnit) {
        if (betUnit == null) {
            throw new IllegalArgumentException("betUnit can't be a null");
        }
        betUnits.add(betUnit);
    }
}
