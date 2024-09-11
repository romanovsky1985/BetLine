package betline.sport.icehockey;

import java.util.concurrent.ThreadLocalRandom;
import java.util.function.BiFunction;

/*
 * Генератор, который и занимается моделированием матча. Передаем матч (который, возможно, уже играется
 * и имеет некоторый набор событий) и набор параметров для генерации. Возвращает случайным образом
 * смоделированный до конца матч.
 */
public class IceHockeyGenerator implements BiFunction<IceHockeyGame, IceHockeyConfig, IceHockeyGame> {
    private static final int MAIN_DURATION = 3600;
    private static final int WITH_OT_DURATION = 3900;
    private static final double CORRECTION_6X5 = 1.5;
    private static final double CORRECTION_5X6 = 8.5;
    private static final double CORRECTION_3x3 = 2.5;

    @Override
    public IceHockeyGame apply(IceHockeyGame currentGame, IceHockeyConfig gameConfig) {
        IceHockeyGame resultGame = new IceHockeyGame();
        for (var entry : currentGame.getHomeEvents().entrySet()) {
            resultGame.putHomeEvent(entry.getKey(), entry.getValue());
        }
        for (var entry : currentGame.getGuestEvents().entrySet()) {
            resultGame.putGuestEvent(entry.getKey(), entry.getValue());
        }
        int homeScore = (int) currentGame.getHomeEvents().values().stream()
                .filter(e -> e == IceHockeyEvent.SCORE).count();
        int guestScore = (int) currentGame.getGuestEvents().values().stream()
                .filter(e -> e == IceHockeyEvent.SCORE).count();
        for (int second = gameConfig.getCurrentSecond(); second < MAIN_DURATION; second++) {
            double homeExpected = gameConfig.getHomeExpected() / MAIN_DURATION;
            double guestExpected = gameConfig.getGuestExpected() / MAIN_DURATION;
            if (ThreadLocalRandom.current().nextDouble() < homeExpected) {
                resultGame.putHomeEvent(second, IceHockeyEvent.SCORE);
                ++homeScore;
            }
            if (ThreadLocalRandom.current().nextDouble() < guestExpected) {
                resultGame.putGuestEvent(second, IceHockeyEvent.SCORE);
                ++guestScore;
            }
            // Empty net goals
            if (homeScore - guestScore > 0 && homeScore - guestScore <= gameConfig.getEmptyDifference()
                    && second > MAIN_DURATION - gameConfig.getEmptyDuration()) {
                if (ThreadLocalRandom.current().nextDouble() < homeExpected * CORRECTION_5X6) {
                    resultGame.putHomeEvent(second, IceHockeyEvent.SCORE);
                    resultGame.putHomeEvent(second, IceHockeyEvent.INFO_ENG);
                    ++homeScore;
                }
                if (ThreadLocalRandom.current().nextDouble() < guestExpected * CORRECTION_6X5) {
                    resultGame.putGuestEvent(second, IceHockeyEvent.SCORE);
                    ++guestScore;
                }
            }
            if (guestScore - homeScore > 0 && guestScore - homeScore <= gameConfig.getEmptyDifference()
                    && second > MAIN_DURATION - gameConfig.getEmptyDuration()) {
                if (ThreadLocalRandom.current().nextDouble() < guestExpected * CORRECTION_5X6) {
                    resultGame.putGuestEvent(second, IceHockeyEvent.SCORE);
                    resultGame.putGuestEvent(second, IceHockeyEvent.INFO_ENG);
                    ++guestScore;
                }
                if (ThreadLocalRandom.current().nextDouble() < homeExpected * CORRECTION_6X5) {
                    resultGame.putHomeEvent(second, IceHockeyEvent.SCORE);
                    ++homeScore;
                }
            }
        }
        if (homeScore != guestScore) {
            return resultGame;
        }
        // Overtime
        for (int second = MAIN_DURATION; second < WITH_OT_DURATION; second++) {
            double homeExpected = (gameConfig.getHomeExpected() / MAIN_DURATION) * CORRECTION_3x3;
            double guestExpected = (gameConfig.getGuestExpected() / MAIN_DURATION) * CORRECTION_3x3;
            if (ThreadLocalRandom.current().nextDouble() < homeExpected) {
                resultGame.putHomeEvent(second, IceHockeyEvent.OT_SCORE);
                return resultGame;
            }
            if (ThreadLocalRandom.current().nextDouble() < guestExpected) {
                resultGame.putGuestEvent(second, IceHockeyEvent.OT_SCORE);
                return resultGame;
            }
        }
        // Shootout

        return resultGame;
    }


    public static void main(String[] args) {
        IceHockeyGenerator generator = new IceHockeyGenerator();
        IceHockeyGame game = generator.apply(new IceHockeyGame(), new IceHockeyConfig());
        int homeScore = (int) game.getHomeEvents().values().stream().filter(e -> e == IceHockeyEvent.SCORE).count();
        int guestScore = (int) game.getGuestEvents().values().stream().filter(e -> e == IceHockeyEvent.OT_SCORE).count();
        System.out.println(homeScore + " : " + guestScore);
    }
}
