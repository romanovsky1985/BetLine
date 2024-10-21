package my.betline.sport.team;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public abstract class TeamParserESPN implements TeamParser {
    private final String leagueName;
    private final Map<String, String> leagueTeams;

    public TeamParserESPN(String leagueName, Map<String, String> leagueTeams) {
        this.leagueName = leagueName;
        this.leagueTeams = leagueTeams;
    }

    @Override
    public Set<String> getTeams() {
        return leagueTeams.keySet();
    }

    @Override
    public String getLeague() {
        return leagueName;
    }

    private final static double MIN_PLAYED = 0.4;
    private final static ObjectMapper objectMapper = new ObjectMapper();
    private final static HttpClient httpClient = HttpClient.newHttpClient();
    @Override
    public Map<String, Map<String, Double>> parse(String teamWithSeason) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(leagueTeams.get(teamWithSeason)))
                    .build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            Pattern jsonPattern = Pattern.compile("\"tableRows\":.*]]]");
            Matcher jsonMatcher = jsonPattern.matcher(response.body());
            if (!jsonMatcher.find()) {
                throw new TeamParserException("Ошибка при разборе json");
            }
            JsonNode mainNode = objectMapper.readTree("{" + jsonMatcher.group() + "}");
            JsonNode scoresNode = mainNode.get("tableRows").get(0);
            JsonNode assistsNode = mainNode.get("tableRows").get(1);
            int totalGoals = 0;
            int maxPlayed = 0;
            for (JsonNode scoreNode : scoresNode) {
                totalGoals += scoreNode.get(3).get("value").intValue();
                if (scoreNode.get(2).get("value").intValue() > maxPlayed) {
                    maxPlayed = scoreNode.get(2).get("value").intValue();
                }
            }
            Map<String, Map<String, Double>> players = new HashMap<>();
            for (int i = 0; i < scoresNode.size(); i++) {
                int played = scoresNode.get(i).get(2).get("value").intValue();
                int goals = scoresNode.get(i).get(3).get("value").intValue();
                String name = scoresNode.get(i).get(1).get("name").textValue();
                int assists = 0;
                for (int j = 0; j < assistsNode.size(); j++) {
                    if (name != null && name.equals(assistsNode.get(j).get(1).get("name").textValue())) {
                        assists = assistsNode.get(j).get(3).get("value").intValue();
                        break;
                    }
                }

                if (played > maxPlayed * MIN_PLAYED) {
                    double proportion = (double) maxPlayed / (double) played;
                    double goalsByScore = (proportion * goals) / totalGoals;
                    double assistsByScore = (proportion * assists) / totalGoals;
                    players.put(name, Map.of("Шайба", goalsByScore,
                            "Передача", assistsByScore, "Очко", goalsByScore + assistsByScore));
                }
            }
            return players;
        } catch (URISyntaxException e) {
            throw new TeamParserException("Ошибка при поиске команды");
        } catch (JsonProcessingException e) {
            throw new TeamParserException("Ошибка при разборе json");
        } catch (IOException | InterruptedException e) {
            throw new TeamParserException("Ошибка при загрузке страницы");
        }
    }
}
