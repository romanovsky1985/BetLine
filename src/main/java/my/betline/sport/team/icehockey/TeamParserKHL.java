package my.betline.sport.team.icehockey;

import com.fasterxml.jackson.databind.JsonNode;
import my.betline.sport.team.SeasonTeamParser;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class TeamParserKHL extends SeasonTeamParser {

    public TeamParserKHL() {
        super(TEAMS);
    }

    @Override
    public double getDefaultExpected() {
        return 2.5;
    }

    public String test() {
        return this.parse("Адмирал#2023-2024").toString();
    }

    private final static HttpClient httpClient = HttpClient.newHttpClient();

    @Override
    protected Map<String, Map<String, Double>> parse(String team) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(leagueTeams.get(team)))
                    .build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            Pattern playerPattern = Pattern.compile("player=\\d+\">([\\w\\s]+)</a></th><[^<]+</td><[^<]+</td><td>(\\d+)</td><td>(\\d+)</td><td>(\\d+)</td>");
            Matcher playerMatcher = playerPattern.matcher(response.body());
            int totalGoals = 0;
            int maxPlayed = 0;
            while (playerMatcher.find()) {
                totalGoals += Integer.parseInt(playerMatcher.group(3));
                int played = Integer.parseInt(playerMatcher.group(2));
                if (played > maxPlayed) {
                    maxPlayed = played;
                }
            }
            playerMatcher.reset();
            Map<String, Map<String, Double>> players = new HashMap<>();
            while (playerMatcher.find()) {
                int played = Integer.parseInt(playerMatcher.group(2));
                int goals = Integer.parseInt(playerMatcher.group(3));
                int assists = Integer.parseInt(playerMatcher.group(4));
                String name = playerMatcher.group(1);
                double proportion = (double) maxPlayed / (double) played;
                double goalsByScore = (proportion * goals) / totalGoals;
                double assistsByScore = (proportion * assists) / totalGoals;
                if (goalsByScore != 0. && assistsByScore != 0. && played > maxPlayed / 2) {
                   players.put(name, Map.of("Шайбы", goalsByScore, "Передачи", assistsByScore,
                           "Очки", goalsByScore + assistsByScore));
                }
            }
            return players;
        } catch (URISyntaxException | IOException e) {
            throw new RuntimeException("Ошибка загрузки");
        } catch (Exception e) {
            throw new RuntimeException("Ошибка разбора");
        }
    }

    private final static Map<String, String> TEAMS = new HashMap<>();

    static {

        TEAMS.put("Адмирал#2023-2024", "https://www.quanthockey.com/khl/teams/dynamo-moscow-players-2023-24-khl-stats.html");

    }
}

