package betline.sport.icehockey;

import betline.core.BetUnit;
import betline.core.LineBuilder;

public class IceHockeyLineBuilder extends LineBuilder<IceHockeyGame> {
    public IceHockeyLineBuilder() {
        super(new IceHockeyGenerator());
    }

    public void addWinner() {
        addUnit(new BetUnit<IceHockeyGame>("Матч1",
                game -> game.getHomeEvents().parallelStream().unordered()
                        .anyMatch(he -> he.getEvent() == IceHockeyEvent.WIN),
                null,
                "Матч2"));
    }

    public void addThreeWay() {
        addUnit(new BetUnit<IceHockeyGame>("П1",
                game -> game.getHomeEvents().parallelStream().unordered()
                        .filter(he -> he.getEvent() == IceHockeyEvent.SCORE).count() >
                game.getGuestEvents().parallelStream().unordered()
                        .filter(ge -> ge.getEvent() == IceHockeyEvent.SCORE).count(),
                null,
                "Х2"));

        addUnit(new BetUnit<IceHockeyGame>("П2",
                game -> game.getHomeEvents().parallelStream().unordered()
                        .filter(he -> he.getEvent() == IceHockeyEvent.SCORE).count() <
                        game.getGuestEvents().parallelStream().unordered()
                                .filter(ge -> ge.getEvent() == IceHockeyEvent.SCORE).count(),
                null,
                "1Х"));

        addUnit(new BetUnit<IceHockeyGame>("Х",
                game -> game.getHomeEvents().parallelStream().unordered()
                        .filter(he -> he.getEvent() == IceHockeyEvent.SCORE).count() ==
                        game.getGuestEvents().parallelStream().unordered()
                                .filter(ge -> ge.getEvent() == IceHockeyEvent.SCORE).count(),
                null,
                "12"));
    }

    public void addTotal(double ttl) {
        Double totalValue = ttl;
        String underText = "TM(" + ttl + ")";
        String overText = "ТБ(" + ttl + ")";

        addUnit(new BetUnit<IceHockeyGame>(underText,
                game -> game.getHomeEvents().parallelStream().unordered()
                        .filter(he -> he.getEvent() == IceHockeyEvent.SCORE).count() +
                        game.getGuestEvents().parallelStream().unordered()
                                .filter(ge -> ge.getEvent() == IceHockeyEvent.SCORE).count() < ttl,
                game -> game.getHomeEvents().parallelStream().unordered()
                        .filter(he -> he.getEvent() == IceHockeyEvent.SCORE).count() +
                        game.getGuestEvents().parallelStream().unordered()
                                .filter(ge -> ge.getEvent() == IceHockeyEvent.SCORE).count() > ttl,
                overText));
    }

    public void addHomeHandicap(double hcp) {
        Double hcpValue = hcp;
        String leftText = "Ф1(" + hcp + ")";
        String rightText = "Ф2(" + (-hcp) + ")";

        addUnit(new BetUnit<IceHockeyGame>(leftText,
                game -> game.getHomeEvents().parallelStream().unordered()
                        .filter(he -> he.getEvent() == IceHockeyEvent.SCORE).count() + hcp >
                        game.getGuestEvents().parallelStream().unordered()
                                .filter(ge -> ge.getEvent() == IceHockeyEvent.SCORE).count(),
                game -> game.getHomeEvents().parallelStream().unordered()
                        .filter(he -> he.getEvent() == IceHockeyEvent.SCORE).count() + hcp <
                        game.getGuestEvents().parallelStream().unordered()
                                .filter(ge -> ge.getEvent() == IceHockeyEvent.SCORE).count(),
                rightText));
    }

    public void addGuestHandicap(double hcp) {
        Double hcpValue = hcp;
        String leftText = "Ф1(" + (-hcp) + ")";
        String rightText = "Ф2(" + hcp + ")";

        addUnit(new BetUnit<IceHockeyGame>(rightText,
                game -> game.getGuestEvents().parallelStream().unordered()
                        .filter(he -> he.getEvent() == IceHockeyEvent.SCORE).count() + hcp >
                        game.getHomeEvents().parallelStream().unordered()
                                .filter(ge -> ge.getEvent() == IceHockeyEvent.SCORE).count(),
                game -> game.getGuestEvents().parallelStream().unordered()
                        .filter(he -> he.getEvent() == IceHockeyEvent.SCORE).count() + hcp <
                        game.getHomeEvents().parallelStream().unordered()
                                .filter(ge -> ge.getEvent() == IceHockeyEvent.SCORE).count(),
                leftText));
    }
}
