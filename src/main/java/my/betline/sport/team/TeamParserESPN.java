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

public class TeamParserESPN implements TeamParser {
    private final String leagueName;
    private final Map<String, String> leagueTeams;

    public TeamParserESPN(String league) {
        this.leagueName = league;
        this.leagueTeams = switch (league) {
            case "APL" -> APL_TEAMS;
            default -> throw new TeamParserException("Лига на найдена: " + league);
        };
    }

    @Override
    public Set<String> getTeams() {
        return leagueTeams.keySet().stream()
                .map(team -> team.replaceAll("#\\d\\d\\d\\d-\\d\\d\\d\\d", ""))
                .collect(Collectors.toSet());
    }

    @Override
    public boolean canParse(String teamWithSeason) {
        return leagueTeams.containsKey(teamWithSeason);
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
                int assists = assistsNode.get(i).get(3).get("value").intValue();
                if (played > maxPlayed * MIN_PLAYED) {
                    String name = scoresNode.get(i).get(1).get("name").textValue();
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

    private final static Map<String, String> APL_TEAMS = Map.ofEntries(
            Map.entry("Ливерпуль#2023-2024", "https://www.espn.com/soccer/team/stats/_/id/364/league/ENG.1/season/2023"),
            Map.entry("Манчестер Сити#2023-2024", "https://www.espn.com/soccer/team/stats/_/id/382/league/ENG.1/season/2023"),
            Map.entry("Арсенал#2023-2024", "https://www.espn.com/soccer/team/stats/_/id/359/league/ENG.1/season/2023"),
            Map.entry("Брайтон#2023-2024", "https://www.espn.com/soccer/team/stats/_/id/331/league/ENG.1/season/2023"),
            Map.entry("Челси#2023-2024", "https://www.espn.com/soccer/team/stats/_/id/363/league/ENG.1/season/2023"),
            Map.entry("Тоттенхем#2023-2024", "https://www.espn.com/soccer/team/stats/_/id/367/league/ENG.1/season/2023"),
            Map.entry("Ньюкасл#2023-2024", "https://www.espn.com/soccer/team/stats/_/id/361/league/ENG.1/season/2023"),
            Map.entry("Фулхэм#2023-2024", "https://www.espn.com/soccer/team/stats/_/id/370/league/ENG.1/season/2023"),
            Map.entry("Борнмут#2023-2024", "https://www.espn.com/soccer/team/stats/_/id/349/league/ENG.1/season/2023"),
            Map.entry("Манчестер Юнайтед#2023-2024", "https://www.espn.com/soccer/team/stats/_/id/360/league/ENG.1/season/2023"),
            Map.entry("Ноттингем#2023-2024", "https://www.espn.com/soccer/team/stats/_/id/393/league/ENG.1/season/2023"),
            Map.entry("Брентфорд#2023-2024", "https://www.espn.com/soccer/team/stats/_/id/337/league/ENG.1/season/2023"),
            Map.entry("Лестер#2023-2024", "https://www.espn.com/soccer/team/stats/_/id/375/league/ENG.2/season/2023"),
            Map.entry("Вест Хэм#2023-2024", "https://www.espn.com/soccer/team/stats/_/id/371/league/ENG.1/season/2023"),
            Map.entry("Эвертон#2023-2024", "https://www.espn.com/soccer/team/stats/_/id/368/league/ENG.1/season/2023"),
            Map.entry("Ипсвич#2023-2024", "https://www.espn.com/soccer/team/stats/_/id/373/league/ENG.2/season/2023"),
            Map.entry("Кристал Пэлас#2023-2024", "https://www.espn.com/soccer/team/stats/_/id/384/league/ENG.1/season/2023"),
            Map.entry("Вульверхемптон#2023-2024", "https://www.espn.com/soccer/team/stats/_/id/380/league/ENG.1/season/2023"),
            Map.entry("Саутхемптон#2023-2024", "https://www.espn.com/soccer/team/stats/_/id/376/league/ENG.2/season/2023"),

            Map.entry("Ливерпуль#2024-2025", "https://www.espn.com/soccer/team/stats/_/id/364/league/ENG.1/season/2024"),
            Map.entry("Манчестер Сити#2024-2025", "https://www.espn.com/soccer/team/stats/_/id/382/league/ENG.1/season/2024"),
            Map.entry("Арсенал#2024-2025", "https://www.espn.com/soccer/team/stats/_/id/359/league/ENG.1/season/2024"),
            Map.entry("Брайтон#2024-2025", "https://www.espn.com/soccer/team/stats/_/id/331/league/ENG.1/season/2024"),
            Map.entry("Челси#2024-2025", "https://www.espn.com/soccer/team/stats/_/id/363/league/ENG.1/season/2024"),
            Map.entry("Тоттенхем#2024-2025", "https://www.espn.com/soccer/team/stats/_/id/367/league/ENG.1/season/2024"),
            Map.entry("Ньюкасл#2024-2025", "https://www.espn.com/soccer/team/stats/_/id/361/league/ENG.1/season/2024"),
            Map.entry("Фулхэм#2024-2025", "https://www.espn.com/soccer/team/stats/_/id/370/league/ENG.1/season/2024"),
            Map.entry("Борнмут#2024-2025", "https://www.espn.com/soccer/team/stats/_/id/349/league/ENG.1/season/2024"),
            Map.entry("Манчестер Юнайтед#2024-2025", "https://www.espn.com/soccer/team/stats/_/id/360/league/ENG.1/season/2024"),
            Map.entry("Ноттингем#2024-2025", "https://www.espn.com/soccer/team/stats/_/id/393/league/ENG.1/season/2024"),
            Map.entry("Брентфорд#2024-2025", "https://www.espn.com/soccer/team/stats/_/id/337/league/ENG.1/season/2024"),
            Map.entry("Лестер#2024-2025", "https://www.espn.com/soccer/team/stats/_/id/375/league/ENG.1/season/2024"),
            Map.entry("Вест Хэм#2024-2025", "https://www.espn.com/soccer/team/stats/_/id/371/league/ENG.1/season/2024"),
            Map.entry("Эвертон#2024-2025", "https://www.espn.com/soccer/team/stats/_/id/368/league/ENG.1/season/2024"),
            Map.entry("Ипсвич#2024-2025", "https://www.espn.com/soccer/team/stats/_/id/373/league/ENG.1/season/2024"),
            Map.entry("Кристал Пэлас#2024-2025", "https://www.espn.com/soccer/team/stats/_/id/384/league/ENG.1/season/2024"),
            Map.entry("Вульверхемптон#2024-2025", "https://www.espn.com/soccer/team/stats/_/id/380/league/ENG.1/season/2024"),
            Map.entry("Саутхемптон#2024-2025", "https://www.espn.com/soccer/team/stats/_/id/376/league/ENG.1/season/2024")
    );
}
