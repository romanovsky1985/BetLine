import betline.core.BetUnit;
import betline.core.LineBuilder;
import betline.sport.icehockey.IceHockeyEvent;
import betline.sport.icehockey.IceHockeyGame;
import betline.sport.icehockey.IceHockeyGenerator;
import betline.sport.icehockey.IceHockeyLineBuilder;

public class Demo {
    public static void main(String[] args) {

        IceHockeyLineBuilder builder = new IceHockeyLineBuilder(1_000, 0.0);

        builder.addWinner();
        builder.addThreeWay();
        builder.addTotal(5.5);
        builder.addAdditional();
        builder.addOvertime();
        builder.addShootouts();

        builder.buildLine(new IceHockeyGame()).stream().unordered()
            .forEach(System.out::println);
    }
}
