package my.betline.utils;

import java.text.DecimalFormat;
import java.util.Map;
import java.util.function.Function;

public class LineEntryFormatter implements Function<Map.Entry<String, Double>, String> {

    public static String format(Map.Entry<String, Double> entry) {
        return (entry.getValue() > 99 || entry.getValue() < 1.01 || Double.isNaN(entry.getValue())) ? ""
                : new DecimalFormat(".##").format(entry.getValue());
    }

    @Override
    public String apply(Map.Entry<String, Double> entry) {
        if (entry.getValue() > 99 || entry.getValue() < 1.005 || Double.isNaN(entry.getValue())) {
            return "";
        }
        return new DecimalFormat(".##").format(entry.getValue());
    }
}
