import betline.core.BetUnit;
import betline.core.LineBuilder;
import betline.sport.icehockey.IceHockeyEvent;
import betline.sport.icehockey.IceHockeyGame;
import betline.sport.icehockey.IceHockeyGenerator;
import java.util.stream.Stream;

public class Demo {
    public static void main(String[] args) {
        IceHockeyGenerator generator = new IceHockeyGenerator();
        LineBuilder<IceHockeyGame> builder = new LineBuilder<>(generator);
        builder.addUnit(new BetUnit<>("Победа 1 в матче",
                game -> game.getHomeEvents().stream().unordered().anyMatch(he -> he.getEvent() == IceHockeyEvent.WIN),
                game -> game.getGuestEvents().stream().unordered().anyMatch(ge -> ge.getEvent() == IceHockeyEvent.WIN),
                "Победа 2 в матче"));
        builder.addUnit(new BetUnit<>("Будет гол в пустые",
                game -> Stream.concat(game.getHomeEvents().stream(), game.getGuestEvents().stream())
                        .unordered().anyMatch(ge -> ge.getEvent() == IceHockeyEvent.ENG)));
        System.out.println(builder.buildLine(new IceHockeyGame()));
    }
}
