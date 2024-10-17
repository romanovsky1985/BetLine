package my.betline.sport.team;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public class TeamParserNHL implements TeamParser {
    private final static double MIN_PLAYED = 0.4;

    @Override
    public Map<String, Map<String, Double>> parse(String team) {
        try {
            JsonNode mainNode = new ObjectMapper().readTree(new URI(TEAMS.get(team)).toURL());
            int totalGoals = 0;
            int maxPlayed = 0;
            for (JsonNode skater : mainNode.get("skaters")) {
                totalGoals += skater.get("goals").intValue();
                if (skater.get("gamesPlayed").intValue() > maxPlayed) {
                    maxPlayed = skater.get("gamesPlayed").intValue();
                }
            }
            Map<String, Map<String, Double>> players = new HashMap<>();
            for (JsonNode skater : mainNode.get("skaters")) {
                int played = skater.get("gamesPlayed").intValue();
                int goals = skater.get("goals").intValue();
                int assists = skater.get("assists").intValue();
                if (played > maxPlayed * MIN_PLAYED) {
                    String name = skater.get("firstName").get("default").textValue() + " " +
                            skater.get("lastName").get("default").textValue();
                    double proportion = (double) maxPlayed / (double) played;
                    double goalsByScore = (proportion * goals) / totalGoals;
                    double assistsByScore = (proportion * assists) / totalGoals;
                    players.put(name, Map.of("Шайба", goalsByScore,
                            "Передача", assistsByScore, "Очко", goalsByScore + assistsByScore));
                }
            }
            return players;
        } catch (URISyntaxException e) {
            throw new TeamParserException("Команда не найдена");
        } catch (JsonProcessingException e) {
            throw new TeamParserException("Ошибка в разборе json");
        } catch (IOException e) {
            throw new RuntimeException("Ошибка загрузки страницы");
        }
    }

    @Override
    public Map<String, String> getTeams() {
        return TEAMS;
    }
    private final static Map<String, String> TEAMS = Map.ofEntries(
            Map.entry("Бостон", "https://api-web.nhle.com/v1/club-stats/BOS/20232024/2"),
            Map.entry("Баффало","https://api-web.nhle.com/v1/club-stats/BUF/20232024/2"),
            Map.entry("Оттава","https://api-web.nhle.com/v1/club-stats/OTT/20232024/2"),
            Map.entry("Детройт","https://api-web.nhle.com/v1/club-stats/DET/20232024/2"),
            Map.entry("Монреаль","https://api-web.nhle.com/v1/club-stats/MON/20232024/2")

    );
}
