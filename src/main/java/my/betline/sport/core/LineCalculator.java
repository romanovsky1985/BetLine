package my.betline.sport.core;

import my.betline.sport.icehockey.IceHockeyGame;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;
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
        if (executor == null) {
            // считаем в один поток
            List<G> games = Collections.nCopies(iterations, game).stream()
                    .map(generator::generate).toList();
            Map<String, Double> units = betUnits.stream()
                    .map(unit -> unit.calc(games, margin).entrySet())
                    .flatMap(Collection::stream)
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
            return units;
        }
        try {
            // считаем мультипоточно
            long time = System.nanoTime();
            ReentrantLock lock = new ReentrantLock();
            List<G> games = new ArrayList<>(iterations);
            CountDownLatch gamesLatch = new CountDownLatch(iterations);
            for (int i = 0; i < iterations; i++) {
                executor.execute(() -> {
                    G variant = generator.generate(game);
                    lock.lock();
                    games.add(variant);
                    lock.unlock();
                    gamesLatch.countDown();
                });
            }
            gamesLatch.await();
            Map<String, Double> units = new HashMap<>(2 * betUnits.size());
            CountDownLatch unitsLatch = new CountDownLatch(betUnits.size());
            for (BetUnit<G> betUnit : betUnits) {
                executor.execute(() -> {
                    Map<String, Double> unit = betUnit.calc(games, margin);
                    lock.lock();
                    units.putAll(unit);
                    lock.unlock();
                    unitsLatch.countDown();
                });
            }
            unitsLatch.await();
            return units;
        } catch (InterruptedException exception) {
            return new HashMap<>();
        }
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

    public static Map<String, Map<String, Double>> calcPlayersLine(
            Map<String, Map<String, Double>> players, double expected, double margin) {
        Map<String, Map<String, Double>> result = new HashMap<>();
        for (String playerName : players.keySet()) {
            Map<String, Double> line = new HashMap<>();
            for (String lineName : players.get(playerName).keySet()) {
                double lineExpected = expected * players.get(playerName).get(lineName);
                double noProbability = Math.exp(- lineExpected);
                double yesProbability = 1 - noProbability;
                line.put(lineName + " Да", (1 - margin) / yesProbability);
                line.put(lineName + " Нет", (1 - margin) / noProbability);
            }
            result.put(playerName, line);
        }
        return result;
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
