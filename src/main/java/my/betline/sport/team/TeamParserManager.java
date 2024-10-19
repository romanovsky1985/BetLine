package my.betline.sport.team;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;

public class TeamParserManager {
    private final List<TeamParser> parsers;
    public TeamParserManager(List<TeamParser> parsers) {
        if (parsers == null) {
            throw new TeamParserException("Не задан ни один парсер");
        }
        this.parsers = parsers;
    }

    public Map<String, Map<String, Double>> parse(String team) {
        for (TeamParser parser : parsers) {
            if (parser.getTeams().contains(team)) {
                return parser.parse(team);
            }
        }
        throw new TeamParserException("Подходящий парсер не найден: " + team);
    }

    public List<String> getTeams(String league) {
        if (league == null) {
            throw new TeamParserException("Не задано название лиги");
        }
        for (TeamParser parser : parsers) {
            if (league.toUpperCase().equals(parser.getLeague())) {
                return parser.getTeams().stream().sorted().toList();
            }
        }
        throw new TeamParserException("Парсер для заданной лиги не найден");
    }

}
