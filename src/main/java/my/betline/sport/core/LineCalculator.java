package my.betline.sport.core;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/*
 * G - тип матчей (хоккей, футбол и т.д.) с которыми работает счетчик линии
 * L - уже тип самого счетчика (т.е. инстанцированный LineCalculator<G>)
 * инстанс которого создаем в билдере (поэтому билдеру передаем класс-объект)
 */

public class LineCalculator<G> {
    private final int iterations;
    private final double margin;
    private final Executor executor;
    private final GameGenerator<G> generator;
    private final List<BetUnit<G>> betUnits = new ArrayList<>();


    public LineCalculator(GameGenerator<G> generator, int iterations, double margin,
                          Executor executor) {
        this.generator = generator;
        this.iterations = iterations;
        this.margin = margin;
        this.executor = executor;
    }

    public Map<String, Double> calcLine(G game) {
        if (executor != null) try {
            //System.out.println("DEBUG: multi thread start");
            final CompletionService<G> gamesCompletionService =
                    new ExecutorCompletionService<>(executor);
            for (int i = 0; i < iterations; i++) {
                gamesCompletionService.submit(() -> generator.generate(game));
            }
            final List<G> games = new ArrayList<>(iterations);
            for (int i = 0; i < iterations; i++) {
                games.add(gamesCompletionService.take().get());
            }
            final CompletionService<Map<String, Double>> unitsCompletionService =
                    new ExecutorCompletionService<>(executor);
            for (BetUnit<G> unit : betUnits) {
                unitsCompletionService.submit(() -> unit.calc(games, margin));
            }
            Map<String, Double> result = new HashMap<>(2 * betUnits.size());
            for (int i = 0; i < betUnits.size(); i++) {
                result.putAll(unitsCompletionService.take().get());
            }
            //System.out.println("DEBUG: multi thread success finished");
            return result;
        } catch (InterruptedException | ExecutionException ignored) {};
        // executor не задан, либо мультипоток вывалился с исключением
        List<G> games = Collections.nCopies(iterations, game).stream()
                .map(generator::generate).toList();
        return betUnits.stream()
                .map(unit -> unit.calc(games, margin).entrySet())
                .flatMap(Collection::stream)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public void addUnit(BetUnit<G> betUnit) {
        if (betUnit == null) {
            throw new IllegalArgumentException("betUnit can't be a null");
        }
        betUnits.add(betUnit);
    }

    public void addUnits(List<BetUnit<G>> betUnits) {
        for (BetUnit<G> unit : betUnits) {
            addUnit(unit);
        }
    }

    public static <L> Builder<L> builder(Class<L> clazz) {
        return new Builder<>(clazz);
    }

    public static class Builder<L> {
        private final Class<L> clazz;
        private int iterations = 10_000;
        private double margin = 0.05;
        private Executor executor = null;

        public Builder(Class<L> clazz) {
            this.clazz = clazz;
        }

        public Builder<L> setIterations(int iterations) {
            this.iterations = iterations;
            return this;
        }

        public Builder<L> setMargin(double margin) {
            this.margin = margin;
            return this;
        }

        public Builder<L> setExecutor(Executor executor) {
            this.executor = executor;
            return this;
        }

        public L build() {
            try {
                return clazz.getDeclaredConstructor(new Class[]{int.class, double.class, Executor.class})
                        .newInstance(iterations, margin, executor);
            } catch (Exception exception) {
                throw new IllegalArgumentException("can't construct " + this.clazz);
            }
        }
    }
}
