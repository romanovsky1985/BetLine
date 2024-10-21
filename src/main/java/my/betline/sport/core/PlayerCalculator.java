package my.betline.sport.core;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class PlayerCalculator {
    private final double margin;
    private final double total;
    private final double expected;
    public PlayerCalculator(double expected, double total, double margin) {
        this.expected = expected;
        this.margin = margin;
        this.total = total;
    }

    public Map<String, Double> calc(Map<String, Double> stats) {
        Map<String, Double> line = new HashMap<>();
        for (var stat : stats.entrySet()) {
            double under = calcUnder(expected * stat.getValue(), total);
            String underText = String.format("%s М(%d,5)", stat.getKey(), (int) total);
            line.put(underText, (1 - margin) / under);
            double over = 1. - under;
            String overText = String.format("%s Б(%d,5)", stat.getKey(), (int) total);
            line.put(overText, (1 - margin) / over);
        }
        return line;
    }

    public Map<String, Map<String, Double>> calcPlayers(Map<String, Map<String, Double>> players) {
        return players.entrySet().stream().collect(
                Collectors.toMap(Map.Entry::getKey, entry -> calc(entry.getValue())));
    }

    private double calcUnder(double e, double t) {
        double under = .0;
        double fact = 1.;
        for (int i = 0; i < t; i++) {
            under += (Math.exp(-e) * Math.pow(e, i)) / fact;
            fact *= i + 1.;
        }
        return under;
    }
}
