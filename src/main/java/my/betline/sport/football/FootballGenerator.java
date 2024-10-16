package my.betline.sport.football;

import my.betline.sport.core.GameGenerator;

import java.util.concurrent.ThreadLocalRandom;

public class FootballGenerator implements GameGenerator<FootballGame> {
    @Override
    public FootballGame generate(FootballGame initGame) {
        FootballGame game = new FootballGame(initGame);
        int homeScore = game.get("homeScore").intValue();
        int guestScore = game.get("guestScore").intValue();
        int gameDuration = game.get("gameDuration").intValue();
        int firstDuration = game.get("firstDuration").intValue();
        double homeExpectedPerGame = game.get("homeExpected").doubleValue();
        double guestExpectedPerGame = game.get("guestExpected").doubleValue();
        int second = game.get("currentSecond").intValue();
        int timeStep = game.get("timeStep").intValue();
        int drawCorrectionStart = game.get("drawCorrectionStart").intValue();
        double drawCorrectionFinal = game.get("drawCorrectionFinal").doubleValue();
        int firstAdditional = second < firstDuration ? game.get("firstAdditional").intValue() : 0;
        int gameAdditional = game.get("gameAdditional").intValue();
        int nextScoreNumber = homeScore + guestScore + 1;
        int homeTeamSize = game.get("homeTeamSize").intValue();
        int guestTeamSize = game.get("guestTeamSize").intValue();
        double correction10x11 = game.get("correction10x11").doubleValue();
        double correction11x10 = game.get("correction11x10").doubleValue();

        game.set("nextScore", 0);

        while (second < firstDuration + firstAdditional || second < gameDuration + gameAdditional) {
            // мат ожидания на интервал
            double homeExpectedPerStep = (homeExpectedPerGame / gameDuration) * timeStep;
            double guestExpectedPerStep = (guestExpectedPerGame / gameDuration) * timeStep;

            // корректировка мат ожиданий от составов
            if (homeTeamSize == 10) {
                homeExpectedPerStep *= correction10x11;
                guestExpectedPerStep *= correction11x10;
            }
            if (guestTeamSize == 10) {
                homeExpectedPerStep *= correction11x10;
                guestExpectedPerStep *= correction10x11;
            }

            // корректировка мат ожиданий при ничейном счете
            if (homeScore == guestScore && second > drawCorrectionStart) {
                double fraction = second - drawCorrectionStart;
                fraction /= gameDuration - drawCorrectionStart;
                double correction = (1. - drawCorrectionFinal * fraction);
                homeExpectedPerStep *= correction;
                guestExpectedPerStep *= correction;
            }

            // взятие ворот
            if (ThreadLocalRandom.current().nextDouble() < homeExpectedPerStep) {
                ++homeScore;
                // фиксируем гол
                game.set("homeScore", homeScore);
                if (homeScore + guestScore == nextScoreNumber) {
                    game.set("nextScore", 1);
                }
            }
            if (ThreadLocalRandom.current().nextDouble() < guestExpectedPerStep) {
                ++guestScore;
                // фиксируем гол
                game.set("guestScore", guestScore);
                if (homeScore + guestScore == nextScoreNumber) {
                    game.set("nextScore", 2);
                }
            }

            // сброс доп времени после первого периода
            if (firstAdditional > 0 && second > firstDuration + firstAdditional) {
                firstAdditional = 0;
                second = firstDuration;
            }

            second += timeStep;
        }


        return game;
    }
}
