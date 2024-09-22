import betline.core.BetUnit;
import betline.core.LineBuilder;
import betline.sport.icehockey.IceHockeyEvent;
import betline.sport.icehockey.IceHockeyGame;
import betline.sport.icehockey.IceHockeyGenerator;
<<<<<<< HEAD:src/betline/Test.java
import betline.sport.icehockey.IceHockeyLineBuilder;
=======
import java.util.stream.Stream;
>>>>>>> refs/remotes/origin/main:src/Demo.java

public class Demo {
    public static void main(String[] args) {
<<<<<<< HEAD:src/betline/Test.java

        IceHockeyLineBuilder builder = new IceHockeyLineBuilder();

        builder.addWinner();
        builder.addThreeWay();
        builder.addTotal(5.5);
        builder.addHomeHandicap(-1.5);
        builder.addGuestHandicap(-2.5);

=======
        IceHockeyGenerator generator = new IceHockeyGenerator();
        LineBuilder<IceHockeyGame> builder = new LineBuilder<>(generator);
        builder.addUnit(new BetUnit<>("Победа 1 в матче",
                game -> game.getHomeEvents().stream().unordered().anyMatch(he -> he.getEvent() == IceHockeyEvent.WIN),
                game -> game.getGuestEvents().stream().unordered().anyMatch(ge -> ge.getEvent() == IceHockeyEvent.WIN),
                "Победа 2 в матче"));
        builder.addUnit(new BetUnit<>("Будет гол в пустые",
                game -> Stream.concat(game.getHomeEvents().stream(), game.getGuestEvents().stream())
                        .unordered().anyMatch(ge -> ge.getEvent() == IceHockeyEvent.ENG)));
>>>>>>> refs/remotes/origin/main:src/Demo.java
        System.out.println(builder.buildLine(new IceHockeyGame()));
    }
}
