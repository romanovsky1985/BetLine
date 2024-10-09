package my.betline.sport.icehockey;

import my.betline.sport.core.GameGenerator;

import java.util.concurrent.ThreadLocalRandom;

public class IceHockeyGenerator implements GameGenerator<IceHockeyGame> {

    @Override
    public IceHockeyGame generate(IceHockeyGame initGame) {
        IceHockeyGame game = new IceHockeyGame(initGame);
        int homeScore = game.get("homeScore").intValue();
        int guestScore = game.get("guestScore").intValue();
        int gameDuration = game.get("gameDuration").intValue();
        double homeExpected = game.get("homeExpected").doubleValue() / gameDuration;
        double guestExpected = game.get("guestExpected").doubleValue() / gameDuration;
        int second = game.get("currentSecond").intValue();
        int emptyDifference = game.get("emptyDifference").intValue();
        int emptyDuration = game.get("emptyDuration").intValue();
        double correction5x6 = game.get("correction5x6").doubleValue();
        double correction6x5 = game.get("correction6x5").doubleValue();
        double correction3x3 = game.get("correction3x3").doubleValue();

        //основное время
        while (second < gameDuration) {
            // взятие ворот
            if (ThreadLocalRandom.current().nextDouble() < homeExpected) {
                ++homeScore;
            }
            if (ThreadLocalRandom.current().nextDouble() < guestExpected) {
                ++guestScore;
            }
            // гол в пустые
            if (homeScore - guestScore > 0 && homeScore - guestScore <= emptyDifference
                    && second > gameDuration - emptyDuration) {
                if (ThreadLocalRandom.current().nextDouble() < homeExpected * correction5x6) {
                    ++homeScore;
                }
                if (ThreadLocalRandom.current().nextDouble() < guestExpected * correction6x5) {
                    ++guestScore;
                }
            }
            if (guestScore - homeScore > 0 && guestScore - homeScore <= emptyDifference
                    && second > gameDuration - emptyDuration) {
                if (ThreadLocalRandom.current().nextDouble() < guestExpected * correction5x6) {
                    ++guestScore;
                }
                if (ThreadLocalRandom.current().nextDouble() < homeExpected * correction6x5) {
                    ++homeScore;
                }
            }

            ++second;
        }
        game.set("currentSecond", 3600);
        game.set("homeScore", homeScore);
        game.set("guestScore", guestScore);
        if (homeScore > guestScore) {
            game.set("gameWinner", 1);
            return game;
        }
        if (homeScore < guestScore) {
            game.set("gameWinner", 2);
            return game;
        }

        // овертайм
        gameDuration += game.get("otDuration").intValue();
        while (second < gameDuration) {
            int ot = 0;
            if (ThreadLocalRandom.current().nextDouble() < homeExpected * correction3x3) {
                ++ot;
            }
            if (ThreadLocalRandom.current().nextDouble() < guestExpected * correction3x3) {
                --ot;
            }
            if (ot == 1) {
                game.set("gameWinner", 1);
                game.set("otWinner", 1);
                return game;
            }
            if (ot == -1) {
                game.set("gameWinner", 2);
                game.set("otWinner", 2);
                return game;
            }

            ++second;
        }
        game.set("otWinner", 3);
        game.set("currentSecond", second);

        // буллиты
        int homeSh = 0;
        int guestSh = 0;
        int shootoutLength = game.get("shootoutLength").intValue();
        double shootoutProbability = game.get("shootoutProbability").doubleValue();
        while (shootoutLength > 0 || homeSh == guestSh) {
            if (ThreadLocalRandom.current().nextDouble() < shootoutProbability) {
                ++homeSh;
            }
            if (ThreadLocalRandom.current().nextDouble() < shootoutProbability) {
                ++guestSh;
            }
            if (shootoutLength > 0) {
                --shootoutLength;
            }
            if (homeSh > guestSh + shootoutLength) {
                game.set("homeShootout", homeSh);
                game.set("gameWinner", 1);
                return game;
            }
            if (guestSh > homeSh + shootoutLength) {
                game.set("guestShootout", guestSh);
                game.set("gameWinner", 2);
                return game;
            }
        }

        throw new RuntimeException("icehockey game reach after sh state");
    }
}
