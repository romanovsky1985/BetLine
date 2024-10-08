package my.betline.sport.core;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/*
 * G - тип матчей (хоккей, футбол и т.д.) с которыми работает счетчик линии
 * L - уже тип самого счетчика (т.е. инстанцированный LineCalculator<G>)
 * для которого реализуется паттерн билдера
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
//        if (executor != null) {
//            try {
//                List<G> games = generateGamesMultithreading(game);
//                return calcUnitsMultithreading(games);
//            } catch (Exception ignored) {};
//        }
        //System.out.println(betUnits);
        List<G> games = Collections.nCopies(iterations, game).parallelStream().unordered()
                .map(generator::generate).toList();
        return betUnits.parallelStream().unordered()
                .map(unit -> unit.calc(games, margin).entrySet())
                .flatMap(Collection::stream)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

//    private List<G> generateGamesMultithreading(G game) throws Exception {
//        final int threadsCount = Runtime.getRuntime().availableProcessors();
//        final int threadIterations = iterations / threadsCount;
//        final CompletionService<List<G>> completionService =
//                new ExecutorCompletionService<>(executor);
//        for (int i = 0; i < threadsCount; i++) {
//            completionService.submit(() -> {
//                List<G> result = new ArrayList<>(threadIterations);
//                for (int j = 0; j < threadIterations; j++) {
//                    result.add(generator.generate(game));
//                }
//                return result;
//            });
//        }
//        final List<G> games = new ArrayList<>(iterations);
//        for (int i = 0; i < threadsCount; i++) {
//            games.addAll(completionService.take().get());
//        }
//        return games;
//    }
//
//    private List<LineUnit> calcUnitsMultithreading(List<G> games) throws Exception {
//        final CompletionService<LineUnit> completionService =
//                new ExecutorCompletionService<>(executor);
//        for (BetUnit<G> unit : betUnits) {
//            completionService.submit(() -> unit.calc(games, margin));
//        }
//        final int threadsCount = betUnits.size();
//        final ArrayList<LineUnit> units = new ArrayList<>(threadsCount);
//        for (int i = 0; i < threadsCount; i++) {
//            units.add(completionService.take().get());
//        }
//        return units;
//    }


    public void addUnit(BetUnit<G> betUnit) {
        if (betUnit == null) {
            throw new IllegalArgumentException("betUnit can't be a null");
        }
        betUnits.add(betUnit);
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

        public Builder<L> useExecutor(Executor executor) {
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
