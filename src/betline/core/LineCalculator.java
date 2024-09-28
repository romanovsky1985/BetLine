package betline.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/*
 * G - тип матчей (хоккей, футбол и т.д.) с которыми работает счетчик линии
 * L - уже тип самого счетчика (т.е. инстанцированный LineCalculator<G>)
 * для которого реализуется паттерн билдера
 */

public class LineCalculator<G> {
    private final int iterations;
    private final double margin;
    private final ExecutorService executorService;
    private final GameGenerator<G> generator;
    private final List<BetUnit<G>> betUnits = new ArrayList<>();


    public LineCalculator(GameGenerator<G> generator, int iterations, double margin,
                          ExecutorService executorService) {
        this.generator = generator;
        this.iterations = iterations;
        this.margin = margin;
        this.executorService = executorService;
    }

    public List<LineUnit> calcLine(G game) {
        // вдове быстрее чем parallelStream().unordered()
        if (executorService != null) {
            try {
                List<G> games = generateGamesMultithreading(game);
                return calcUnitsMultithreading(games);
            } catch (Exception ignored) {};
        }
        List<G> games = Collections.nCopies(iterations, game).parallelStream().unordered()
                .map(generator::generate).toList();
        return betUnits.parallelStream().unordered()
                .map(unit -> unit.calc(games, margin)).toList();
    }

    private List<G> generateGamesMultithreading(G game) throws Exception {
        final int threadIterations = iterations / Runtime.getRuntime().availableProcessors();
        ArrayList<Future<List<G>>> futures = new ArrayList<>();
        for (int n = 0; n < Runtime.getRuntime().availableProcessors(); n++) {
            futures.add(executorService.submit(() -> {
                List<G> result = new ArrayList<>(threadIterations);
                for (int i = 0; i < threadIterations; i++) {
                    result.add(generator.generate(game));
                }
                return result;
            }));
        }
        final List<G> games = new ArrayList<>(iterations);
        for (Future<List<G>> future : futures) {
            games.addAll(future.get());
        }
        return games;
    }

    private List<LineUnit> calcUnitsMultithreading(List<G> games) throws Exception {
        final ArrayList<Future<LineUnit>> futures = new ArrayList<>(betUnits.size());
        for (BetUnit<G> unit : betUnits) {
            futures.add(executorService.submit(() -> unit.calc(games, margin)));
        }
        final ArrayList<LineUnit> units = new ArrayList<>(betUnits.size());
        for (Future<LineUnit> future : futures) {
            units.add(future.get());
        }
        return units;
    }

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
        private ExecutorService executorService = null;

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

        public Builder<L> useExecutorService(ExecutorService executorService) {
            this.executorService = executorService;
            return this;
        }

        public L build() {
            try {
                return clazz.getDeclaredConstructor(new Class[]{int.class, double.class, ExecutorService.class})
                        .newInstance(iterations, margin, executorService);
            } catch (Exception exception) {
                throw new IllegalArgumentException("can't construct " + this.clazz);
            }
        }
    }
}
