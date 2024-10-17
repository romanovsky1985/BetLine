package my.betline.utils;

import java.text.DecimalFormat;
import java.util.Map;
import java.util.stream.Collectors;

public class LineFormatter {

    public static String formatEntry(Map.Entry<String, Double> entry) {
        return (entry.getValue() > 99 || entry.getValue() < 1.01 || Double.isNaN(entry.getValue())) ? ""
                : new DecimalFormat(".##").format(entry.getValue());
    }

    public static Map<String, String> formatMap(Map<String, Double> line) {
        return line.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, LineFormatter::formatEntry));
    }

}
