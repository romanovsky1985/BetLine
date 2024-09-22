import betline.core.BetUnit;
import betline.core.LineBuilder;
import betline.sport.icehockey.IceHockeyEvent;
import betline.sport.icehockey.IceHockeyGame;
import betline.sport.icehockey.IceHockeyGenerator;
import betline.sport.icehockey.IceHockeyLineBuilder;

public class Demo {
    public static void main(String[] args) {

        IceHockeyLineBuilder builder = new IceHockeyLineBuilder();

        builder.addWinner();
        builder.addThreeWay();
        builder.addTotal(5.5);

        builder.buildLine(new IceHockeyGame()).stream().unordered()
            .forEach(System.out::println);
    }
}
