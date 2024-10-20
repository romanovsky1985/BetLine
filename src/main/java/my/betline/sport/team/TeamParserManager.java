package my.betline.sport.team;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TeamParserManager {
    private final List<TeamParser> parsers;

    public TeamParserManager(List<TeamParser> parsers) {
        if (parsers == null) {
            throw new TeamParserException("Не задан ни один парсер");
        }
        this.parsers = parsers;
    }

    public Map<String, Map<String, Double>> parse(String team, String season) {
        String teamWithSeason = team + "#" + season;
        Map<String, Map<String, Double>> result = readFromCache(teamWithSeason);
        if (result != null) {
            return result;
        }
        for (TeamParser parser : parsers) {
            if (parser.canParse(teamWithSeason)) {
                result = parser.parse(teamWithSeason);
                writeToCache(teamWithSeason, result);
                return result;
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



    private record CacheRecord(LocalDateTime time, Map<String, Map<String, Double>> value) {};
    private final ConcurrentHashMap<String, CacheRecord> cacheMap = new ConcurrentHashMap<>();

    private Map<String, Map<String, Double>> readFromCache(String team) {
        final int cacheTtlHours = 10;
        CacheRecord result = cacheMap.get(team);
        if (result == null || LocalDateTime.now().minusHours(cacheTtlHours).isAfter(result.time())) {
            return null;
        } else {
            return result.value();
        }
    }

    private void writeToCache(String team, Map<String, Map<String, Double>> value) {
        cacheMap.put(team, new CacheRecord(LocalDateTime.now(), value));
    }


}
