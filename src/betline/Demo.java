package betline;

import betline.core.LineUnit;
import betline.sport.icehockey.IceHockeyCalculator;
import betline.sport.icehockey.IceHockeyGame;
import betline.sport.icehockey.IceHockeyGenerator;

import java.util.List;

public class Demo {
    public static void main(String[] args) {
        System.out.println("Start betline demo...");

        IceHockeyCalculator iceCalc = new IceHockeyCalculator();
        List<LineUnit> units = iceCalc.calcLine(
                new IceHockeyGenerator(), new IceHockeyGame());
        units.forEach(System.out::println);
    }
}
