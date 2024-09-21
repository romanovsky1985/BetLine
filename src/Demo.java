import betline.core.BetUnit;
import betline.core.LineBuilder;
import betline.sport.icehockey.IceHockeyEvent;
import betline.sport.icehockey.IceHockeyGame;
import betline.sport.icehockey.IceHockeyGenerator;

public class Demo {
    public static void main(String[] args) {
        IceHockeyGenerator generator = new IceHockeyGenerator();
        LineBuilder<IceHockeyGame> builder = new LineBuilder<>(generator);
        builder.addUnit(new BetUnit<>("Победа 1 в матче",
                game -> game.getHomeEvents().parallelStream().unordered().anyMatch(he -> he.getEvent() == IceHockeyEvent.WIN),
                game -> game.getGuestEvents().parallelStream().unordered().anyMatch(ge -> ge.getEvent() == IceHockeyEvent.WIN),
                "Победа 2 в матче"));

        System.out.println(builder.buildLine(new IceHockeyGame()));
    }
}
