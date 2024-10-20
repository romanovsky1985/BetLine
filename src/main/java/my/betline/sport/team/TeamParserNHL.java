package my.betline.sport.team;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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
    public String getLeague() {
       return "NHL";
    }

    @Override
    public Set<String> getTeams() {
            return TEAMS.keySet();
    }

    private final static Map<String, String> TEAMS = Map.ofEntries(
            Map.entry("Бостон#2023-2024", "https://api-web.nhle.com/v1/club-stats/BOS/20232024/2"),
            Map.entry("Баффало#2023-2024","https://api-web.nhle.com/v1/club-stats/BUF/20232024/2"),
            Map.entry("Оттава#2023-2024","https://api-web.nhle.com/v1/club-stats/OTT/20232024/2"),
            Map.entry("Флорида#2023-2024","https://api-web.nhle.com/v1/club-stats/FLA/20232024/2"),
            Map.entry("Монреаль#2023-2024","https://api-web.nhle.com/v1/club-stats/MTL/20232024/2"),
            Map.entry("Тампа#2023-2024","https://api-web.nhle.com/v1/club-stats/TBL/20232024/2"),
            Map.entry("Торонто#2023-2024","https://api-web.nhle.com/v1/club-stats/TOR/20232024/2"),
            Map.entry("Детройт#2023-2024","https://api-web.nhle.com/v1/club-stats/DET/20232024/2"),
            Map.entry("Каролина#2023-2024", "https://api-web.nhle.com/v1/club-stats/CAR/20232024/2"),
            Map.entry("Коламбус#2023-2024","https://api-web.nhle.com/v1/club-stats/CBJ/20232024/2"),
            Map.entry("Нью-Джерси#2023-2024","https://api-web.nhle.com/v1/club-stats/NJD/20232024/2"),
            Map.entry("Айлендерс#2023-2024","https://api-web.nhle.com/v1/club-stats/NYI/20232024/2"),
            Map.entry("Рейнджерс#2023-2024","https://api-web.nhle.com/v1/club-stats/NYR/20232024/2"),
            Map.entry("Филадельфия#2023-2024","https://api-web.nhle.com/v1/club-stats/PHI/20232024/2"),
            Map.entry("Питтсбург#2023-2024","https://api-web.nhle.com/v1/club-stats/PIT/20232024/2"),
            Map.entry("Вашингтон#2023-2024","https://api-web.nhle.com/v1/club-stats/WSH/20232024/2"),
            Map.entry("Чикаго#2023-2024", "https://api-web.nhle.com/v1/club-stats/CHI/20232024/2"),
            Map.entry("Колорадо#2023-2024","https://api-web.nhle.com/v1/club-stats/COL/20232024/2"),
            Map.entry("Даллас#2023-2024","https://api-web.nhle.com/v1/club-stats/DAL/20232024/2"),
            Map.entry("Миннесота#2023-2024","https://api-web.nhle.com/v1/club-stats/MIN/20232024/2"),
            Map.entry("Нэшвилл#2023-2024","https://api-web.nhle.com/v1/club-stats/NSH/20232024/2"),
            Map.entry("Сент-Луис#2023-2024","https://api-web.nhle.com/v1/club-stats/STL/20232024/2"),
            Map.entry("Юта#2023-2024","https://api-web.nhle.com/v1/club-stats/ARI/20232024/2"),
            Map.entry("Виннипег#2023-2024","https://api-web.nhle.com/v1/club-stats/WPG/20232024/2"),
            Map.entry("Вегас#2023-2024", "https://api-web.nhle.com/v1/club-stats/VGK/20232024/2"),
            Map.entry("Ванкувер#2023-2024","https://api-web.nhle.com/v1/club-stats/VAN/20232024/2"),
            Map.entry("Сиэтл#2023-2024","https://api-web.nhle.com/v1/club-stats/SEA/20232024/2"),
            Map.entry("Сан-Хосе#2023-2024","https://api-web.nhle.com/v1/club-stats/SJS/20232024/2"),
            Map.entry("Лос-Анджелес#2023-2024","https://api-web.nhle.com/v1/club-stats/LAK/20232024/2"),
            Map.entry("Эдмонтон#2023-2024","https://api-web.nhle.com/v1/club-stats/EDM/20232024/2"),
            Map.entry("Калгари#2023-2024","https://api-web.nhle.com/v1/club-stats/CGY/20232024/2"),
            Map.entry("Анахайм#2023-2024","https://api-web.nhle.com/v1/club-stats/ANA/20232024/2"),

            Map.entry("Бостон#2024-2025", "https://api-web.nhle.com/v1/club-stats/BOS/20242025/2"),
            Map.entry("Баффало#2024-2025","https://api-web.nhle.com/v1/club-stats/BUF/20242025/2"),
            Map.entry("Оттава#2024-2025","https://api-web.nhle.com/v1/club-stats/OTT/20242025/2"),
            Map.entry("Флорида#2024-2025","https://api-web.nhle.com/v1/club-stats/FLA/20242025/2"),
            Map.entry("Монреаль#2024-2025","https://api-web.nhle.com/v1/club-stats/MTL/20242025/2"),
            Map.entry("Тампа#2024-2025","https://api-web.nhle.com/v1/club-stats/TBL/20242025/2"),
            Map.entry("Торонто#2024-2025","https://api-web.nhle.com/v1/club-stats/TOR/20242025/2"),
            Map.entry("Детройт#2024-2025","https://api-web.nhle.com/v1/club-stats/DET/20242025/2"),
            Map.entry("Каролина#2024-2025", "https://api-web.nhle.com/v1/club-stats/CAR/20242025/2"),
            Map.entry("Коламбус#2024-2025","https://api-web.nhle.com/v1/club-stats/CBJ/20242025/2"),
            Map.entry("Нью-Джерси#2024-2025","https://api-web.nhle.com/v1/club-stats/NJD/20242025/2"),
            Map.entry("Айлендерс#2024-2025","https://api-web.nhle.com/v1/club-stats/NYI/20242025/2"),
            Map.entry("Рейнджерс#2024-2025","https://api-web.nhle.com/v1/club-stats/NYR/20242025/2"),
            Map.entry("Филадельфия#2024-2025","https://api-web.nhle.com/v1/club-stats/PHI/20242025/2"),
            Map.entry("Питтсбург#2024-2025","https://api-web.nhle.com/v1/club-stats/PIT/20242025/2"),
            Map.entry("Вашингтон#2024-2025","https://api-web.nhle.com/v1/club-stats/WSH/20242025/2"),
            Map.entry("Чикаго#2024-2025", "https://api-web.nhle.com/v1/club-stats/CHI/20242025/2"),
            Map.entry("Колорадо#2024-2025","https://api-web.nhle.com/v1/club-stats/COL/20242025/2"),
            Map.entry("Даллас#2024-2025","https://api-web.nhle.com/v1/club-stats/DAL/20242025/2"),
            Map.entry("Миннесота#2024-2025","https://api-web.nhle.com/v1/club-stats/MIN/20242025/2"),
            Map.entry("Нэшвилл#2024-2025","https://api-web.nhle.com/v1/club-stats/NSH/20242025/2"),
            Map.entry("Сент-Луис#2024-2025","https://api-web.nhle.com/v1/club-stats/STL/20242025/2"),
            Map.entry("Юта#2024-2025","https://api-web.nhle.com/v1/club-stats/UTA/20242025/2"),
            Map.entry("Виннипег#2024-2025","https://api-web.nhle.com/v1/club-stats/WPG/20242025/2"),
            Map.entry("Вегас#2024-2025", "https://api-web.nhle.com/v1/club-stats/VGK/20242025/2"),
            Map.entry("Ванкувер#2024-2025","https://api-web.nhle.com/v1/club-stats/VAN/20242025/2"),
            Map.entry("Сиэтл#2024-2025","https://api-web.nhle.com/v1/club-stats/SEA/20242025/2"),
            Map.entry("Сан-Хосе#2024-2025","https://api-web.nhle.com/v1/club-stats/SJS/20242025/2"),
            Map.entry("Лос-Анджелес#2024-2025","https://api-web.nhle.com/v1/club-stats/LAK/20242025/2"),
            Map.entry("Эдмонтон#2024-2025","https://api-web.nhle.com/v1/club-stats/EDM/20242025/2"),
            Map.entry("Калгари#2024-2025","https://api-web.nhle.com/v1/club-stats/CGY/20242025/2"),
            Map.entry("Анахайм#2024-2025","https://api-web.nhle.com/v1/club-stats/ANA/20242025/2")

    );
}
