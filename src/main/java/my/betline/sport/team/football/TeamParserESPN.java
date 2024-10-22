package my.betline.sport.team.football;

import com.fasterxml.jackson.databind.JsonNode;
import my.betline.sport.team.SeasonTeamParser;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class TeamParserESPN extends SeasonTeamParser {

    public TeamParserESPN(Map<String, String> leagueTeams) {
        super(leagueTeams);
    }

    @Override
    public double getDefaultExpected() {
        return 1.25;
    }

    private static final HttpClient httpClient = HttpClient.newHttpClient();

    @Override
    public Map<String, Map<String, Double>> parse(String team) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(leagueTeams.get(team)))
                    .build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            Pattern jsonPattern = Pattern.compile("\"tableRows\":.*]]]");
            Matcher jsonMatcher = jsonPattern.matcher(response.body());
            if (!jsonMatcher.find()) {
                throw new RuntimeException("Ошибка разбора");
            }
            JsonNode mainNode = objectMapper.readTree(String.format("{%s}", jsonMatcher.group()));
            JsonNode scoresNode = mainNode.get("tableRows").get(0);
            int totalGoals = 0;
            int maxPlayed = 0;
            for (JsonNode scoreNode : scoresNode) {
                totalGoals += scoreNode.get(3).get("value").intValue();
                if (scoreNode.get(2).get("value").intValue() > maxPlayed) {
                    maxPlayed = scoreNode.get(2).get("value").intValue();
                }
            }
            Map<String, Map<String, Double>> players = new HashMap<>();
            for (JsonNode scoreNode : scoresNode) {
                int played = scoreNode.get(2).get("value").intValue();
                int goals = scoreNode.get(3).get("value").intValue();
                String name = scoreNode.get(1).get("name").textValue();
                if (goals > 0 && played >= maxPlayed / 2) {
                    double proportion = (double) maxPlayed / (double) played;
                    players.put(name, Map.of("Голы", (proportion * goals) / totalGoals));
                }
            }
            return players;
        } catch (URISyntaxException | IOException e) {
            throw new RuntimeException("Ошибка загрузки");
        } catch (Exception e) {
            throw new RuntimeException("Ошибка разбора");
        }
    }
}
