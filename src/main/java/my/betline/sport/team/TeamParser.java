package my.betline.sport.team;

import java.util.List;
import java.util.Map;

public interface TeamParser {
    Map<String, Map<String, Double>> parse(String team);

    List<String> getTeams();

    static TeamParser getParser(String parser) {
        return switch (parser.toUpperCase()) {
            case "NHL" -> new TeamParserNHL();
            default -> throw new TeamParserException("Чемпионат не найден");
        };
    }
}
