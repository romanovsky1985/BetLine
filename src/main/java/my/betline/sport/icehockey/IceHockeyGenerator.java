package my.betline.sport.icehockey;

import my.betline.sport.core.GameGenerator;

import java.util.concurrent.ThreadLocalRandom;

public class IceHockeyGenerator implements GameGenerator<IceHockeyGame> {

    @Override
    public IceHockeyGame generate(IceHockeyGame initGame) {
        IceHockeyGame game = new IceHockeyGame(initGame);
        int homeScore = game.get("homeScore").intValue();
        int guestScore = game.get("guestScore").intValue();
        int home2nd = game.get("home2nd").intValue();
        int guest2nd = game.get("guest2nd").intValue();
        int periodDuration = game.get("periodDuration").intValue();
        int gameDuration = game.get("gameDuration").intValue();
        double homeExpectedPerGame = game.get("homeExpected").doubleValue();
        double guestExpectedPerGame = game.get("guestExpected").doubleValue();
        int second = game.get("currentSecond").intValue();
        int timeStep = game.get("timeStep").intValue();
        int emptyDifference = game.get("emptyDifference").intValue();
        int emptyDuration = game.get("emptyDuration").intValue();
        int drawCorrectionStart = game.get("drawCorrectionStart").intValue();
        double drawCorrectionFinal = game.get("drawCorrectionFinal").doubleValue();
        double correction5x6 = game.get("correction5x6").doubleValue();
        double correction6x5 = game.get("correction6x5").doubleValue();
        double correctionP1 = game.get("correctionP1").doubleValue();
        double correctionP2 = game.get("correctionP2").doubleValue();
        double correctionP3 = game.get("correctionP3").doubleValue();
        int nextScoreNumber = homeScore + guestScore + 1;

        game.set("nextScore", 0);
        game.set("otWinner", 0);
        game.set("shWinner", 0);
        game.set("emptyNetScore", 0);

        //основное время
        while (second < gameDuration) {
            // мат ожидания на интервал
            double homeExpectedPerStep = (homeExpectedPerGame / gameDuration) * timeStep;
            double guestExpectedPerStep = (guestExpectedPerGame / gameDuration) * timeStep;

            // корректировка мат ожиданий по периодам
            double correctionP = switch (second / 1200) {
                case 0 -> correctionP1;
                case 1 -> correctionP2;
                case 2 -> correctionP3;
                default -> throw new RuntimeException();
            };
            homeExpectedPerStep *= correctionP;
            guestExpectedPerStep *= correctionP;

            // корректировка мат ожиданий при ничейном счете
            if (homeScore == guestScore && second > drawCorrectionStart) {
                double fraction = second - drawCorrectionStart;
                fraction /= gameDuration - drawCorrectionStart;
                double correction = (1. - drawCorrectionFinal * fraction);
                homeExpectedPerStep *= correction;
                guestExpectedPerStep *= correction;
            }

            // пустые ворота и корректировка мат ожиданий
            int emptyNet = 0;
            if (homeScore - guestScore > 0 && homeScore - guestScore <= emptyDifference
                    && second > gameDuration - emptyDuration) {
                homeExpectedPerStep *= correction5x6;
                guestExpectedPerStep *= correction6x5;
                emptyNet = 2;
            }
            if (guestScore - homeScore > 0 && guestScore - homeScore <= emptyDifference
                    && second > gameDuration - emptyDuration) {
                homeExpectedPerStep *= correction6x5;
                guestExpectedPerStep *= correction5x6;
                emptyNet = 1;
            }

            // взятие ворот
            if (ThreadLocalRandom.current().nextDouble() < homeExpectedPerStep) {
                ++homeScore;
                // по периода
                if (second >= periodDuration && second < 2 * periodDuration) {
                    ++home2nd;
                }

                // фиксируем гол в пустые
                if (emptyNet == 2) {
                    game.set("emptyNetScore", 1);
                }
                // фиксируем следующий гол
                if (homeScore + guestScore == nextScoreNumber) {
                    game.set("nextScore", 1);
                }
            }
            if (ThreadLocalRandom.current().nextDouble() < guestExpectedPerStep) {
                ++guestScore;
                // фиксируем гол в пустые
                // по периода
                if (second >= periodDuration && second < 2 * periodDuration) {
                    ++guest2nd;
                }
                if (emptyNet == 1) {
                    game.set("emptyNetScore", 2);
                }
                // фиксируем следующий гол
                if (homeScore + guestScore == nextScoreNumber) {
                    game.set("nextScore", 2);
                }
            }

            second += timeStep;
        }

        game.set("home2nd", home2nd);
        game.set("guest2nd", guest2nd);
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
        double correctionOt = game.get("correctionOt").doubleValue();
        double homeExpectedPerStep = (homeExpectedPerGame / gameDuration) * timeStep;
        homeExpectedPerStep *= correctionOt;
        double guestExpectedPerStep = (guestExpectedPerGame / gameDuration) * timeStep;
        guestExpectedPerStep *= correctionOt;
        gameDuration += game.get("otDuration").intValue();
        while (second < gameDuration) {
            int ot = 0;
            if (ThreadLocalRandom.current().nextDouble() < homeExpectedPerStep) {
                ++ot;
                  //test:
                  //game.set("gameWinner", 1);
                  //game.set("otWinner", 1);
                  //return game;
            }
            if (ThreadLocalRandom.current().nextDouble() < guestExpectedPerStep) {
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

            second += timeStep;
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
                game.set("shWinner", 1);
                return game;
            }
            if (guestSh > homeSh + shootoutLength) {
                game.set("guestShootout", guestSh);
                game.set("gameWinner", 2);
                game.set("shWinner", 2);
                return game;
            }
        }

        throw new RuntimeException("icehockey game reach after sh state");
    }
}
