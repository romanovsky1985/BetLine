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
        IceHockeyConfig config = game.getConfig();
        double homeExpected = config.getHomeExpected() / config.getGameDuration();
        double guestExpected = config.getGuestExpected() / config.getGameDuration();
        int second = game.getConfig().getCurrentSecond();
        // основное время
        while (second < config.getGameDuration()) {
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
            if (homeScore - guestScore > 0 && homeScore - guestScore <= config.getEmptyDifference()
                && second > config.getGameDuration() - config.getEmptyDuration()) {
                if (ThreadLocalRandom.current().nextDouble() < homeExpected * game.getConfig().getCorrection5x6()) {
                    game.addHomeEvent(second, IceHockeyEvent.SCORE);
                    game.addHomeEvent(second, IceHockeyEvent.ENG);
                    ++homeScore;
                }
                if (ThreadLocalRandom.current().nextDouble() < guestExpected * config.getCorrection6x5()) {
                    game.addGuestEvent(second, IceHockeyEvent.SCORE);
                    ++guestScore;
                }
            }
            if (guestScore - homeScore > 0 && guestScore - guestScore <= config.getEmptyDifference()
                && second > config.getGameDuration() - config.getEmptyDuration()) {
                if (ThreadLocalRandom.current().nextDouble() < guestExpected * config.getCorrection5x6()) {
                    game.addGuestEvent(second, IceHockeyEvent.SCORE);
                    game.addGuestEvent(second, IceHockeyEvent.ENG);
                    ++guestScore;
                }
                if (ThreadLocalRandom.current().nextDouble() < homeExpected * config.getCorrection6x5()) {
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
        while (second < config.getGameWithOtDuration()) {
            int ot = 0;
            if (ThreadLocalRandom.current().nextDouble() < homeExpected * config.getCorrection3x3()) {
                ++ot;
            }
            if (ThreadLocalRandom.current().nextDouble() < guestExpected * config.getCorrection3x3()) {
                --ot;
            }
            if (ot == 1) {
                game.addHomeEvent(second, IceHockeyEvent.OT);
                game.addHomeEvent(second, IceHockeyEvent.WIN);
                return game;
            }
            if (ot == -1) {
                game.addGuestEvent(second, IceHockeyEvent.OT);
                game.addGuestEvent(second, IceHockeyEvent.WIN);
                return game;
            }

            ++second;
        }
        // буллиты
        int homeSh = 0;
        int guestSh = 0;
        int shLength = config.getShootoutLength();
        while (shLength > 0 || homeSh == guestSh) {
            if (ThreadLocalRandom.current().nextDouble() < config.getShootoutScoreProbability()) {
                game.addHomeEvent(second, IceHockeyEvent.SH);
                ++homeSh;
            }
            if (ThreadLocalRandom.current().nextDouble() < config.getShootoutScoreProbability()) {
                game.addGuestEvent(second, IceHockeyEvent.SH);
                ++guestSh;
            }
            if (shLength > 0) {
                --shLength;
            }
            if (homeSh > guestSh + shLength) {
                game.addHomeEvent(second, IceHockeyEvent.WIN);
                return game;
            }
            if (guestSh > homeSh + shLength) {
                game.addGuestEvent(second, IceHockeyEvent.WIN);
                return game;
            }
        }
        throw new RuntimeException("icehockey game reach after sh state");
    }
}
