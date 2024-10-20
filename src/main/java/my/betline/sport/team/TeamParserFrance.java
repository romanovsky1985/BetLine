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

public class TeamParserFrance extends TeamParserESPN {

    public TeamParserFrance() {
        super("LIGUE1", TEAMS);
    }

    private final static Map<String, String> TEAMS = Map.ofEntries(

            Map.entry("Ренн#2023-2024", "https://www.espn.com/soccer/team/stats/_/id/169/league/FRA.1/season/2023"),
            Map.entry("Гавр#2023-2024", "https://www.espn.com/soccer/team/stats/_/id/3236/league/FRA.1/season/2023"),
            Map.entry("Анже#2023-2024", "https://www.espn.com/soccer/team/stats/_/id/7868/league/FRA.2/season/2023"),
            Map.entry("Сент-Этьен#2023-2024", "https://www.espn.com/soccer/team/stats/_/id/178/league/FRA.2/season/2023"),
            Map.entry("Реймс#2023-2024", "https://www.espn.com/soccer/team/stats/_/id/3243/league/FRA.1/season/2023"),
            Map.entry("Брест#2023-2024", "https://www.espn.com/soccer/team/stats/_/id/6997/league/FRA.1/season/2023"),
            Map.entry("Ланс#2023-2024", "https://www.espn.com/soccer/team/stats/_/id/175/league/FRA.1/season/2023"),
            Map.entry("Лилль#2023-2024", "https://www.espn.com/soccer/team/stats/_/id/166/league/FRA.1/season/2023"),
            Map.entry("Лион#2023-2024", "https://www.espn.com/soccer/team/stats/_/id/167/league/FRA.1/season/2023"),
            Map.entry("Осер#2023-2024", "https://www.espn.com/soccer/team/stats/_/id/172/league/FRA.2/season/2023"),
            Map.entry("Страсбур#2023-2024", "https://www.espn.com/soccer/team/stats/_/id/180/league/FRA.1/season/2023"),
            Map.entry("Нант#2023-2024", "https://www.espn.com/soccer/team/stats/_/id/165/league/FRA.1/season/2023"),
            Map.entry("Ницца#2023-2024", "https://www.espn.com/soccer/team/stats/_/id/250/league/FRA.1/season/2023"),
            Map.entry("Монако#2023-2024", "https://www.espn.com/soccer/team/stats/_/id/174/league/FRA.1/season/2023"),
            Map.entry("Монпелье#2023-2024", "https://www.espn.com/soccer/team/stats/_/id/274/league/FRA.1/season/2023"),
            Map.entry("Тулуза#2023-2024", "https://www.espn.com/soccer/team/stats/_/id/179/league/FRA.2/season/2023"),
            Map.entry("Марсель#2023-2024", "https://www.espn.com/soccer/team/stats/_/id/176/league/FRA.1/season/2023"),
            Map.entry("ПСЖ#2023-2024", "https://www.espn.com/soccer/team/stats/_/id/160/league/FRA.1/season/2023"),

            Map.entry("Ренн#2024-2025", "https://www.espn.com/soccer/team/stats/_/id/169/league/FRA.1/season/2024"),
            Map.entry("Гавр#2024-2025", "https://www.espn.com/soccer/team/stats/_/id/3236/league/FRA.1/season/2024"),
            Map.entry("Анже#2024-2025", "https://www.espn.com/soccer/team/stats/_/id/7868/league/FRA.1/season/2024"),
            Map.entry("Сент-Этьен#2024-2025", "https://www.espn.com/soccer/team/stats/_/id/178/league/FRA.1/season/2024"),
            Map.entry("Реймс#2024-2025", "https://www.espn.com/soccer/team/stats/_/id/3243/league/FRA.1/season/2024"),
            Map.entry("Брест#2024-2025", "https://www.espn.com/soccer/team/stats/_/id/6997/league/FRA.1/season/2024"),
            Map.entry("Ланс#2024-2025", "https://www.espn.com/soccer/team/stats/_/id/175/league/FRA.1/season/2024"),
            Map.entry("Лилль#2024-2025", "https://www.espn.com/soccer/team/stats/_/id/166/league/FRA.1/season/2024"),
            Map.entry("Лион#2024-2025", "https://www.espn.com/soccer/team/stats/_/id/167/league/FRA.1/season/2024"),
            Map.entry("Осер#2024-2025", "https://www.espn.com/soccer/team/stats/_/id/172/league/FRA.1/season/2024"),
            Map.entry("Страсбур#2024-2025", "https://www.espn.com/soccer/team/stats/_/id/180/league/FRA.1/season/2024"),
            Map.entry("Нант#2024-2025", "https://www.espn.com/soccer/team/stats/_/id/165/league/FRA.1/season/2024"),
            Map.entry("Ницца#2024-2025", "https://www.espn.com/soccer/team/stats/_/id/250/league/FRA.1/season/2024"),
            Map.entry("Монако#2024-2025", "https://www.espn.com/soccer/team/stats/_/id/174/league/FRA.1/season/2024"),
            Map.entry("Монпелье#2024-2025", "https://www.espn.com/soccer/team/stats/_/id/274/league/FRA.1/season/2024"),
            Map.entry("Тулуза#2024-2025", "https://www.espn.com/soccer/team/stats/_/id/179/league/FRA.2/season/2024"),
            Map.entry("Марсель#2024-2025", "https://www.espn.com/soccer/team/stats/_/id/176/league/FRA.1/season/2024"),
            Map.entry("ПСЖ#2024-2025", "https://www.espn.com/soccer/team/stats/_/id/160/league/FRA.1/season/2024")
    );
}
