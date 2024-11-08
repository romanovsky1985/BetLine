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
        TEAMS.put("Динамо Москва#2023-2024", "https://www.quanthockey.com/khl/teams/dynamo-moscow-players-2023-24-khl-stats.html");
        TEAMS.put("Адмирал#2023-2024", "https://www.quanthockey.com/khl/teams/admiral-vladivostok-players-2023-24-khl-stats.html");
        TEAMS.put("Ак Барс#2023-2024", "https://www.quanthockey.com/khl/teams/ak-bars-kazan-players-2023-24-khl-stats.html");
        TEAMS.put("Амур#2023-2024", "https://www.quanthockey.com/khl/teams/amur-khabarovsk-players-2023-24-khl-stats.html");
        TEAMS.put("Авангард#2023-2024", "https://www.quanthockey.com/khl/teams/avangard-omsk-players-2023-24-khl-stats.html");
        TEAMS.put("Автомобилист#2023-2024", "https://www.quanthockey.com/khl/teams/avtomobilist-yekaterinburg-players-2023-24-khl-stats.html");
        TEAMS.put("Барыс#2023-2024", "https://www.quanthockey.com/khl/teams/barys-nur-sultan-players-2023-24-khl-stats.html");
        TEAMS.put("ЦСКА#2023-2024", "https://www.quanthockey.com/khl/teams/cska-moscow-players-2023-24-khl-stats.html");
        TEAMS.put("Динамо Минск#2023-2024", "https://www.quanthockey.com/khl/teams/dinamo-minsk-players-2023-24-khl-stats.html");
        TEAMS.put("Сочи#2023-2024", "https://www.quanthockey.com/khl/teams/hk-sochi-players-2023-24-khl-stats.html");
        TEAMS.put("Кунь Лунь#2023-2024", "https://www.quanthockey.com/khl/teams/kunlun-red-star-players-2023-24-khl-stats.html");
        TEAMS.put("Лада#2023-2024", "https://www.quanthockey.com/khl/teams/lada-togliatti-players-2023-24-khl-stats.html");
        TEAMS.put("Локомотив#2023-2024", "https://www.quanthockey.com/khl/teams/lokomotiv-yaroslavl-players-2023-24-khl-stats.html");
        TEAMS.put("Металлург#2023-2024", "https://www.quanthockey.com/khl/teams/metallurg-magnitogorsk-players-2023-24-khl-stats.html");
        TEAMS.put("Нефтехимик#2023-2024", "https://www.quanthockey.com/khl/teams/neftekhimik-nizhnekamsk-players-2023-24-khl-stats.html");
        TEAMS.put("Салават Юлаев#2023-2024", "https://www.quanthockey.com/khl/teams/salavat-yulaev-ufa-players-2023-24-khl-stats.html");
        TEAMS.put("Северсталь#2023-2024", "https://www.quanthockey.com/khl/teams/severstal-cherepovets-players-2023-24-khl-stats.html");
        TEAMS.put("Сибирь#2023-2024", "https://www.quanthockey.com/khl/teams/sibir-novosibirsk-players-2023-24-khl-stats.html");
        TEAMS.put("СКА#2023-2024", "https://www.quanthockey.com/khl/teams/ska-saint-petersburg-players-2023-24-khl-stats.html");
        TEAMS.put("Спартак#2023-2024", "https://www.quanthockey.com/khl/teams/spartak-moscow-players-2023-24-khl-stats.html");
        TEAMS.put("Торпедо#2023-2024", "https://www.quanthockey.com/khl/teams/torpedo-nizhny-novgorod-players-2023-24-khl-stats.html");
        TEAMS.put("Трактор#2023-2024", "https://www.quanthockey.com/khl/teams/traktor-chelyabinsk-players-2023-24-khl-stats.html");
        TEAMS.put("Витязь#2023-2024", "https://www.quanthockey.com/khl/teams/vityaz-chekhov-players-2023-24-khl-stats.html");

        TEAMS.put("Динамо Москва#2024-2025", "https://www.quanthockey.com/khl/teams/dynamo-moscow-players-2024-25-khl-stats.html");
        TEAMS.put("Адмирал#2024-2025", "https://www.quanthockey.com/khl/teams/admiral-vladivostok-players-2024-25-khl-stats.html");
        TEAMS.put("Ак Барс#2024-2025", "https://www.quanthockey.com/khl/teams/ak-bars-kazan-players-2024-25-khl-stats.html");
        TEAMS.put("Амур#2024-2025", "https://www.quanthockey.com/khl/teams/amur-khabarovsk-players-2024-25-khl-stats.html");
        TEAMS.put("Авангард#2024-2025", "https://www.quanthockey.com/khl/teams/avangard-omsk-players-2024-25-khl-stats.html");
        TEAMS.put("Автомобилист#2024-2025", "https://www.quanthockey.com/khl/teams/avtomobilist-yekaterinburg-players-2024-25-khl-stats.html");
        TEAMS.put("Барыс#2024-2025", "https://www.quanthockey.com/khl/teams/barys-nur-sultan-players-2024-25-khl-stats.html");
        TEAMS.put("ЦСКА#2024-2025", "https://www.quanthockey.com/khl/teams/cska-moscow-players-2024-25-khl-stats.html");
        TEAMS.put("Динамо Минск#2024-2025", "https://www.quanthockey.com/khl/teams/dinamo-minsk-players-2024-25-khl-stats.html");
        TEAMS.put("Сочи#2024-2025", "https://www.quanthockey.com/khl/teams/hk-sochi-players-2024-25-khl-stats.html");
        TEAMS.put("Кунь Лунь#2024-2025", "https://www.quanthockey.com/khl/teams/kunlun-red-star-players-2024-25-khl-stats.html");
        TEAMS.put("Лада#2024-2025", "https://www.quanthockey.com/khl/teams/lada-togliatti-players-2024-25-khl-stats.html");
        TEAMS.put("Локомотив#2024-2025", "https://www.quanthockey.com/khl/teams/lokomotiv-yaroslavl-players-2024-25-khl-stats.html");
        TEAMS.put("Металлург#2024-2025", "https://www.quanthockey.com/khl/teams/metallurg-magnitogorsk-players-2024-25-khl-stats.html");
        TEAMS.put("Нефтехимик#2024-2025", "https://www.quanthockey.com/khl/teams/neftekhimik-nizhnekamsk-players-2024-25-khl-stats.html");
        TEAMS.put("Салават Юлаев#2024-2025", "https://www.quanthockey.com/khl/teams/salavat-yulaev-ufa-players-2024-25-khl-stats.html");
        TEAMS.put("Северсталь#2024-2025", "https://www.quanthockey.com/khl/teams/severstal-cherepovets-players-2024-25-khl-stats.html");
        TEAMS.put("Сибирь#2024-2025", "https://www.quanthockey.com/khl/teams/sibir-novosibirsk-players-2024-25-khl-stats.html");
        TEAMS.put("СКА#2024-2025", "https://www.quanthockey.com/khl/teams/ska-saint-petersburg-players-2024-25-khl-stats.html");
        TEAMS.put("Спартак#2024-2025", "https://www.quanthockey.com/khl/teams/spartak-moscow-players-2024-25-khl-stats.html");
        TEAMS.put("Торпедо#2024-2025", "https://www.quanthockey.com/khl/teams/torpedo-nizhny-novgorod-players-2024-25-khl-stats.html");
        TEAMS.put("Трактор#2024-2025", "https://www.quanthockey.com/khl/teams/traktor-chelyabinsk-players-2024-25-khl-stats.html");
        TEAMS.put("Витязь#2024-2025", "https://www.quanthockey.com/khl/teams/vityaz-chekhov-players-2024-25-khl-stats.html");

    }
}

