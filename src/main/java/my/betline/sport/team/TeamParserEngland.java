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

public class TeamParserEngland extends TeamParserESPN {

    public TeamParserEngland() {
        super("PREMIER", TEAMS);
    }

    private final static Map<String, String> TEAMS = Map.ofEntries(
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
