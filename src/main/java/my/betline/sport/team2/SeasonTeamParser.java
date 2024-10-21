package my.betline.sport.team2;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class SeasonTeamParser implements TeamParser {
    protected final ObjectMapper objectMapper;
    protected final String leagueName;
    protected final Map<String, String> leagueTeams;

    public SeasonTeamParser(String leagueName, Map<String, String> leagueTeams) {
        if (leagueName == null) {
            throw new IllegalArgumentException("leagueName can't be a null");
        }
        this.leagueName = leagueName;
        if (leagueTeams == null) {
            throw new IllegalArgumentException("leagueTeams can't be a null");
        }
        this.leagueTeams = leagueTeams;
        objectMapper = new ObjectMapper();
    }

    @Override
    public String getLeague() {
        return leagueName;
    }

    @Override
    public List<String> getTeams() {
        return leagueTeams.keySet().stream()
                .map(team -> team.replaceAll("#.*", ""))
                .distinct()
                .sorted()
                .toList();
    }

    private record CachedTeam(Map<String, Map<String, Double>> team, LocalDateTime expired) {};
    private final ConcurrentHashMap<String, CachedTeam> cache = new ConcurrentHashMap<>();

    public Map<String, Map<String, Double>> parse(String teamName, String teamSeason) {
        if (teamName == null || teamSeason == null) {
            throw new IllegalArgumentException("can't be null");
        }
        String team = teamName + "#" + teamSeason;
        if (!leagueTeams.containsKey(team)) {
            throw new RuntimeException("Парсер не может обработать команду");
        }
        CachedTeam cachedTeam = cache.get(team);
        if (cachedTeam != null && cachedTeam.expired().isAfter(LocalDateTime.now())) {
            return cachedTeam.team();
        }
        Map<String, Map<String, Double>> result = parse(team);
        String CURRENT_SEASON = "2024-2025";
        int CACHE_HOURS = 25;
        LocalDateTime expired = CURRENT_SEASON.equals(teamSeason) ?
                LocalDateTime.now().plusHours(CACHE_HOURS) : LocalDateTime.MAX;
        cache.put(team, new CachedTeam(result, expired));
        return result;
    }
}
