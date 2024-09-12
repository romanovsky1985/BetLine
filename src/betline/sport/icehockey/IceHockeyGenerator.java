package betline.sport.icehockey;

import betline.core.TimeEvent;

import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;

/*
 * Генератор, который и занимается моделированием матча. Получает на вход матч, который надо
 * догенерировать (возможно, не сначала) и возвращает догенерированный матч.
 */

public class IceHockeyGenerator implements Function<IceHockeyGame, IceHockeyGame> {

    @Override
    public IceHockeyGame apply(IceHockeyGame start) {
        IceHockeyGame game = new IceHockeyGame(start);
        int homeScore = (int) game.getHomeEvents().parallelStream().unordered()
                .filter(he -> he.getEvent() == IceHockeyEvent.SCORE).count();
        int guestScore = (int) game.getGuestEvents().parallelStream().unordered()
                .filter(ge -> ge.getEvent() == IceHockeyEvent.SCORE).count();
        int second = game.getConfig().getCurrentSecond();
        while (second < game.getConfig().getGameDuration()) {
            double homeExpected = game.getConfig().getHomeExpected() / game.getConfig().getGameDuration();
            double guestExpected = game.getConfig().getGuestExpected() / game.getConfig().getGameDuration();
            if (ThreadLocalRandom.current().nextDouble() < homeExpected) {
                game.addHomeEvent(second, IceHockeyEvent.SCORE);
                ++homeScore;
            }
            if (ThreadLocalRandom.current().nextDouble() < guestExpected) {
                game.addGuestEvent(second, IceHockeyEvent.SCORE);
                ++guestScore;
            }
            ++second;
        }
        if (homeScore > guestScore) {
            game.addHomeEvent(second, IceHockeyEvent.WIN);
            return game;
        }
        if (homeScore < guestScore) {
            game.addGuestEvent(second, IceHockeyEvent.WIN);
            return game;
        }

        return game;
    }
}
