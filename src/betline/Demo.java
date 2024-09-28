package betline;

import betline.core.LineCalculator;
import betline.core.LineUnit;
import betline.sport.icehockey.IceHockeyCalculator;
import betline.sport.icehockey.IceHockeyGame;
import betline.sport.icehockey.IceHockeyGenerator;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Demo {
    public static void main(String[] args) {
        System.out.println("Start betline demo...");


        IceHockeyCalculator iceCalc = LineCalculator.builder(IceHockeyCalculator.class)
                .setIterations(50_000)
                .setMargin(0)
                .build();
        long time = System.nanoTime();
        List<LineUnit> units = iceCalc.calcLine(new IceHockeyGame());
        time = System.nanoTime() - time;
        System.out.println("Current thread time millis: " + time / 1000000);
        units.forEach(System.out::println);

        ExecutorService executorService = Executors.newCachedThreadPool();
        IceHockeyCalculator iceCalcMulti = LineCalculator.builder(IceHockeyCalculator.class)
                .setIterations(50_000)
                .setMargin(0)
                .useExecutorService(executorService)
                .build();
        time = System.nanoTime();
        units = iceCalcMulti.calcLine(new IceHockeyGame());
        time = System.nanoTime() - time;
        System.out.println("Multi threads time millis:" + time / 1000000);
        executorService.shutdown();
    }
}
