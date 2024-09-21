package betline.sport.icehockey;

import betline.core.TimeEvent;

import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;

/*
 * Генератор, который собственно и занимается моделированием матча.
 * Получает на вход матч, который надо догенерировать (возможно,
 * уже имеющий некоторые случившиеся события) и возвращает окончившийся
 * матч. То есть, на основе заданных параметров матча осуществляется
 * моделиование события на оставшееся время матча.
 */

public class IceHockeyGenerator implements Function<IceHockeyGame, IceHockeyGame> {

    @Override
    public IceHockeyGame apply(IceHockeyGame start) {
        IceHockeyGame game = new IceHockeyGame(start);
        int homeScore = (int) game.getHomeEvents().stream().unordered()
                .filter(he -> he.getEvent() == IceHockeyEvent.SCORE).count();
        int guestScore = (int) game.getGuestEvents().stream().unordered()
                .filter(ge -> ge.getEvent() == IceHockeyEvent.SCORE).count();
        int second = game.getConfig().getCurrentSecond();
        // основное время
        while (second < game.getConfig().getGameDuration()) {
            double homeExpected = game.getConfig().getHomeExpected() / game.getConfig().getGameDuration();
            double guestExpected = game.getConfig().getGuestExpected() / game.getConfig().getGameDuration();
            // взятие ворот
            if (ThreadLocalRandom.current().nextDouble() < homeExpected) {
                game.addHomeEvent(second, IceHockeyEvent.SCORE);
                ++homeScore;
            }
            if (ThreadLocalRandom.current().nextDouble() < guestExpected) {
                game.addGuestEvent(second, IceHockeyEvent.SCORE);
                ++guestScore;
            }
            // гол в пустые
            if (homeScore - guestScore > 0 && homeScore - guestScore <= game.getConfig().getEmptyDifference()
                && second > game.getConfig().getGameDuration() - game.getConfig().getEmptyDuration()) {
                if (ThreadLocalRandom.current().nextDouble() < homeExpected * game.getConfig().getCorrection5x6()) {
                    game.addHomeEvent(second, IceHockeyEvent.SCORE);
                    game.addHomeEvent(second, IceHockeyEvent.ENG);
                    ++homeScore;
                }
                if (ThreadLocalRandom.current().nextDouble() < guestExpected * game.getConfig().getCorrection6x5()) {
                    game.addGuestEvent(second, IceHockeyEvent.SCORE);
                    ++guestScore;
                }
            }
            if (guestScore - homeScore > 0 && guestScore - guestScore <= game.getConfig().getEmptyDifference()
                && second > game.getConfig().getGameDuration() - game.getConfig().getEmptyDuration()) {
                if (ThreadLocalRandom.current().nextDouble() < guestExpected * game.getConfig().getCorrection5x6()) {
                    game.addGuestEvent(second, IceHockeyEvent.SCORE);
                    game.addGuestEvent(second, IceHockeyEvent.ENG);
                    ++guestScore;
                }
                if (ThreadLocalRandom.current().nextDouble() < homeExpected * game.getConfig().getCorrection6x5()) {
                    game.addHomeEvent(second, IceHockeyEvent.SCORE);
                    ++homeScore;
                }
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
        // овертайм

        return game;
    }
}
