package my.betline.utils;

import java.text.DecimalFormat;
import java.util.Map;
import java.util.TreeMap;

public class LineFormatter {

    private static final DecimalFormat formatter = new DecimalFormat(".##");

    public static String formatCf(Double cf) {
        return (cf > 99 || cf < 1.01 || cf.isNaN()) ? "" : formatter.format(cf);
    }

    public static String formatEntry(Map.Entry<String, Double> entry) {
        return (entry.getValue() > 99 || entry.getValue() < 1.01 || Double.isNaN(entry.getValue())) ? ""
                : new DecimalFormat(".##").format(entry.getValue());
    }

    public static Map<String, String> formatMap(Map<String, Double> line) {
        Map<String, String> result = new TreeMap<>();
        for (var entry : line.entrySet()) {
            result.put(entry.getKey(), formatCf(entry.getValue()));
        }
        return result;
    }

}
