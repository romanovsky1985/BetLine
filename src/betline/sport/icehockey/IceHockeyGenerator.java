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
        IceHockeyConfiguration config = game.getConfiguration();
        int gameDuration = config.get("gameDuration").intValue();
        int emptyDuration = config.get("emptyDuration").intValue();
        int emptyDifference = config.get("emptyDifference").intValue();
        double correction6x5 = config.get("correction6x5").doubleValue();
        double correction5x6 = config.get("correction5x6").doubleValue();
        double correction3x3 = config.get("correction3x3").doubleValue();
        double homeExpected = config.get("homeExpected").doubleValue() / gameDuration;
        double guestExpected = config.get("guestExpected").doubleValue() / gameDuration;
        int second = config.get("currentSecond").intValue();

        // основное время
        while (second < gameDuration) {
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
            if (homeScore - guestScore > 0 && homeScore - guestScore <= emptyDifference
                && second > gameDuration - emptyDuration) {
                if (ThreadLocalRandom.current().nextDouble() < homeExpected * correction5x6) {
                    game.addHomeEvent(second, IceHockeyEvent.SCORE);
                    game.addHomeEvent(second, IceHockeyEvent.ENG);
                    ++homeScore;
                }
                if (ThreadLocalRandom.current().nextDouble() < guestExpected * correction6x5) {
                    game.addGuestEvent(second, IceHockeyEvent.SCORE);
                    ++guestScore;
                }
            }
            if (guestScore - homeScore > 0 && guestScore - guestScore <= emptyDifference
                && second > gameDuration - emptyDuration) {
                if (ThreadLocalRandom.current().nextDouble() < guestExpected * correction5x6) {
                    game.addGuestEvent(second, IceHockeyEvent.SCORE);
                    game.addGuestEvent(second, IceHockeyEvent.ENG);
                    ++guestScore;
                }
                if (ThreadLocalRandom.current().nextDouble() < homeExpected * correction6x5) {
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
        gameDuration += config.get("otDuration").intValue();
        while (second < gameDuration) {
            int ot = 0;
            if (ThreadLocalRandom.current().nextDouble() < homeExpected * correction3x3) {
                ++ot;
            }
            if (ThreadLocalRandom.current().nextDouble() < guestExpected * correction3x3) {
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
        int shootoutLength = config.get("shootoutLength").intValue();
        double shootoutProbability = config.get("shootoutProbability").doubleValue();
        while (shootoutLength > 0 || homeSh == guestSh) {
            if (ThreadLocalRandom.current().nextDouble() < shootoutProbability) {
                game.addHomeEvent(second, IceHockeyEvent.SH);
                ++homeSh;
            }
            if (ThreadLocalRandom.current().nextDouble() < shootoutProbability) {
                game.addGuestEvent(second, IceHockeyEvent.SH);
                ++guestSh;
            }
            if (shootoutLength > 0) {
                --shootoutLength;
            }
            if (homeSh > guestSh + shootoutLength) {
                game.addHomeEvent(second, IceHockeyEvent.WIN);
                return game;
            }
            if (guestSh > homeSh + shootoutLength) {
                game.addGuestEvent(second, IceHockeyEvent.WIN);
                return game;
            }
        }
        throw new RuntimeException("icehockey game reach after sh state");
    }
}
