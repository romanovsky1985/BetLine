package betline.sport.icehockey;

import betline.core.BetUnit;
import betline.core.LineBuilder;

import java.util.stream.Stream;

public class IceHockeyLineBuilder extends LineBuilder<IceHockeyGame> {
    public IceHockeyLineBuilder() {
        super(new IceHockeyGenerator());
    }

    public void addWinner() {
        addUnit(new BetUnit<IceHockeyGame>("Матч1",
                game -> game.getHomeEvents().stream().unordered()
                        .anyMatch(he -> he.getEvent() == IceHockeyEvent.WIN),
                null,
                "Матч2"));
    }

    public void addThreeWay() {
        addUnit(new BetUnit<IceHockeyGame>("П1",
                game -> game.getHomeEvents().stream().unordered()
                        .filter(he -> he.getEvent() == IceHockeyEvent.SCORE).count() >
                game.getGuestEvents().stream().unordered()
                        .filter(ge -> ge.getEvent() == IceHockeyEvent.SCORE).count(),
                null,
                "Х2"));

        addUnit(new BetUnit<IceHockeyGame>("П2",
                game -> game.getHomeEvents().stream().unordered()
                        .filter(he -> he.getEvent() == IceHockeyEvent.SCORE).count() <
                        game.getGuestEvents().stream().unordered()
                                .filter(ge -> ge.getEvent() == IceHockeyEvent.SCORE).count(),
                null,
                "1Х"));

        addUnit(new BetUnit<IceHockeyGame>("Х",
                game -> game.getHomeEvents().stream().unordered()
                        .filter(he -> he.getEvent() == IceHockeyEvent.SCORE).count() ==
                        game.getGuestEvents().stream().unordered()
                                .filter(ge -> ge.getEvent() == IceHockeyEvent.SCORE).count(),
                null,
                "12"));
    }

    public void addTotal(double ttl) {
        Double totalValue = ttl;
        String underText = "TM(" + ttl + ")";
        String overText = "ТБ(" + ttl + ")";

        addUnit(new BetUnit<IceHockeyGame>(underText,
                game -> game.getHomeEvents().stream().unordered()
                        .filter(he -> he.getEvent() == IceHockeyEvent.SCORE).count() +
                        game.getGuestEvents().stream().unordered()
                                .filter(ge -> ge.getEvent() == IceHockeyEvent.SCORE).count() < ttl,
                game -> game.getHomeEvents().stream().unordered()
                        .filter(he -> he.getEvent() == IceHockeyEvent.SCORE).count() +
                        game.getGuestEvents().stream().unordered()
                                .filter(ge -> ge.getEvent() == IceHockeyEvent.SCORE).count() > ttl,
                overText));
    }

    public void addHomeHandicap(double hcp) {
        Double hcpValue = hcp;
        String leftText = "Ф1(" + hcp + ")";
        String rightText = "Ф2(" + (-hcp) + ")";

        addUnit(new BetUnit<IceHockeyGame>(leftText,
                game -> game.getHomeEvents().stream().unordered()
                        .filter(he -> he.getEvent() == IceHockeyEvent.SCORE).count() + hcp >
                        game.getGuestEvents().stream().unordered()
                                .filter(ge -> ge.getEvent() == IceHockeyEvent.SCORE).count(),
                game -> game.getHomeEvents().stream().unordered()
                        .filter(he -> he.getEvent() == IceHockeyEvent.SCORE).count() + hcp <
                        game.getGuestEvents().stream().unordered()
                                .filter(ge -> ge.getEvent() == IceHockeyEvent.SCORE).count(),
                rightText));
    }

    public void addGuestHandicap(double hcp) {
        Double hcpValue = hcp;
        String leftText = "Ф1(" + (-hcp) + ")";
        String rightText = "Ф2(" + hcp + ")";

        addUnit(new BetUnit<IceHockeyGame>(rightText,
                game -> game.getGuestEvents().stream().unordered()
                        .filter(he -> he.getEvent() == IceHockeyEvent.SCORE).count() + hcp >
                        game.getHomeEvents().stream().unordered()
                                .filter(ge -> ge.getEvent() == IceHockeyEvent.SCORE).count(),
                game -> game.getGuestEvents().stream().unordered()
                        .filter(he -> he.getEvent() == IceHockeyEvent.SCORE).count() + hcp <
                        game.getHomeEvents().stream().unordered()
                                .filter(ge -> ge.getEvent() == IceHockeyEvent.SCORE).count(),
                leftText));
    }

   public void addAdditional() {
       addUnit(new BetUnit<IceHockeyGame>("Будет гол в пустые ворота",
               game -> Stream.concat(game.getHomeEvents().stream(), game.getGuestEvents().stream()).unordered()
                       .anyMatch(ev -> ev.getEvent() == IceHockeyEvent.ENG)));

       addUnit(new BetUnit<IceHockeyGame>("Будет гол на первой минуте",
               game -> Stream.concat(game.getHomeEvents().stream(), game.getGuestEvents().stream()).unordered()
                       .anyMatch(ev -> ev.getEvent() == IceHockeyEvent.SCORE && ev.getTime() < 60)));
   }

  public void addOvertime() {
      addUnit(new BetUnit<IceHockeyGame>("Команда 1 выиграет в овертайме",
              game -> game.getHomeEvents().stream().unordered()
                      .anyMatch(he -> he.getEvent() == IceHockeyEvent.OT)));

      addUnit(new BetUnit<IceHockeyGame>("Команда 2 выиграет в овертайме",
              game -> game.getGuestEvents().stream().unordered()
                      .anyMatch(ge -> ge.getEvent() == IceHockeyEvent.OT)));
  }

  public void addShootouts() {
      addUnit(new BetUnit<IceHockeyGame>("Будут послематчевые буллиты",
              game -> Stream.concat(game.getHomeEvents().stream(), game.getGuestEvents().stream()).unordered()
                      .anyMatch(ev -> ev.getEvent() == IceHockeyEvent.SH)));

      addUnit(new BetUnit<IceHockeyGame>("Команда 1 выиграет по буллитам",
              game -> game.getHomeEvents().stream().unordered()
                      .filter(he -> he.getEvent() == IceHockeyEvent.SH).count() >
                      game.getGuestEvents().stream().unordered()
                      .filter(ge -> ge.getEvent() == IceHockeyEvent.SH).count()));

     addUnit(new BetUnit<IceHockeyGame>("Команда 2 выиграет по буллитам",
             game -> game.getGuestEvents().stream().unordered()
                     .filter(ge -> ge.getEvent() == IceHockeyEvent.SH).count() >
                     game.getHomeEvents().stream().unordered()
                     .filter(he -> he.getEvent() == IceHockeyEvent.SH).count()));
  }
}
