package my.betline.utils;

import java.text.DecimalFormat;
import java.util.Map;
import java.util.function.Function;

public class LineEntryFormatter implements Function<Map.Entry<String, Double>, String> {

    @Override
    public String apply(Map.Entry<String, Double> entry) {
        if (entry.getValue() >= 100.0 || entry.getValue() <= 1) {
            return "";
        }
        return new DecimalFormat(".##").format(entry.getValue());
    }
}
