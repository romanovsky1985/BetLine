package my.betline.sport.team.icehockey;

import com.fasterxml.jackson.databind.JsonNode;
import my.betline.sport.team.SeasonTeamParser;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public class TeamParserNHL extends SeasonTeamParser {

    public TeamParserNHL() {
        super(TEAMS);
    }

    @Override
    public double getDefaultExpected() {
        return 3.0;
    }

    @Override
    protected Map<String, Map<String, Double>> parse(String team) {
        try {
            URI uri = new URI(leagueTeams.get(team));
            JsonNode mainNode = objectMapper.readTree(uri.toURL());
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
                String name = skater.get("firstName").get("default").textValue() + " " +
                        skater.get("lastName").get("default").textValue();
                double proportion = (double) maxPlayed / (double) played;
                double goalsByScore = (proportion * goals) / totalGoals;
                double assistsByScore = (proportion * assists) / totalGoals;
                players.put(name, Map.of("Шайбы", goalsByScore, "Передачи", assistsByScore,
                        "Очки", goalsByScore + assistsByScore));
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

        TEAMS.put("Бостон#2023-2024", "https://api-web.nhle.com/v1/club-stats/BOS/20232024/2");
        TEAMS.put("Баффало#2023-2024", "https://api-web.nhle.com/v1/club-stats/BUF/20232024/2");
        TEAMS.put("Оттава#2023-2024", "https://api-web.nhle.com/v1/club-stats/OTT/20232024/2");
        TEAMS.put("Флорида#2023-2024", "https://api-web.nhle.com/v1/club-stats/FLA/20232024/2");
        TEAMS.put("Монреаль#2023-2024", "https://api-web.nhle.com/v1/club-stats/MTL/20232024/2");
        TEAMS.put("Тампа#2023-2024", "https://api-web.nhle.com/v1/club-stats/TBL/20232024/2");
        TEAMS.put("Торонто#2023-2024", "https://api-web.nhle.com/v1/club-stats/TOR/20232024/2");
        TEAMS.put("Детройт#2023-2024", "https://api-web.nhle.com/v1/club-stats/DET/20232024/2");
        TEAMS.put("Каролина#2023-2024", "https://api-web.nhle.com/v1/club-stats/CAR/20232024/2");
        TEAMS.put("Коламбус#2023-2024", "https://api-web.nhle.com/v1/club-stats/CBJ/20232024/2");
        TEAMS.put("Нью-Джерси#2023-2024", "https://api-web.nhle.com/v1/club-stats/NJD/20232024/2");
        TEAMS.put("Айлендерс#2023-2024", "https://api-web.nhle.com/v1/club-stats/NYI/20232024/2");
        TEAMS.put("Рейнджерс#2023-2024", "https://api-web.nhle.com/v1/club-stats/NYR/20232024/2");
        TEAMS.put("Филадельфия#2023-2024", "https://api-web.nhle.com/v1/club-stats/PHI/20232024/2");
        TEAMS.put("Питтсбург#2023-2024", "https://api-web.nhle.com/v1/club-stats/PIT/20232024/2");
        TEAMS.put("Вашингтон#2023-2024", "https://api-web.nhle.com/v1/club-stats/WSH/20232024/2");
        TEAMS.put("Чикаго#2023-2024", "https://api-web.nhle.com/v1/club-stats/CHI/20232024/2");
        TEAMS.put("Колорадо#2023-2024", "https://api-web.nhle.com/v1/club-stats/COL/20232024/2");
        TEAMS.put("Даллас#2023-2024", "https://api-web.nhle.com/v1/club-stats/DAL/20232024/2");
        TEAMS.put("Миннесота#2023-2024", "https://api-web.nhle.com/v1/club-stats/MIN/20232024/2");
        TEAMS.put("Нэшвилл#2023-2024", "https://api-web.nhle.com/v1/club-stats/NSH/20232024/2");
        TEAMS.put("Сент-Луис#2023-2024", "https://api-web.nhle.com/v1/club-stats/STL/20232024/2");
        TEAMS.put("Юта#2023-2024", "https://api-web.nhle.com/v1/club-stats/ARI/20232024/2");
        TEAMS.put("Виннипег#2023-2024", "https://api-web.nhle.com/v1/club-stats/WPG/20232024/2");
        TEAMS.put("Вегас#2023-2024", "https://api-web.nhle.com/v1/club-stats/VGK/20232024/2");
        TEAMS.put("Ванкувер#2023-2024", "https://api-web.nhle.com/v1/club-stats/VAN/20232024/2");
        TEAMS.put("Сиэтл#2023-2024", "https://api-web.nhle.com/v1/club-stats/SEA/20232024/2");
        TEAMS.put("Сан-Хосе#2023-2024", "https://api-web.nhle.com/v1/club-stats/SJS/20232024/2");
        TEAMS.put("Лос-Анджелес#2023-2024", "https://api-web.nhle.com/v1/club-stats/LAK/20232024/2");
        TEAMS.put("Эдмонтон#2023-2024", "https://api-web.nhle.com/v1/club-stats/EDM/20232024/2");
        TEAMS.put("Калгари#2023-2024", "https://api-web.nhle.com/v1/club-stats/CGY/20232024/2");
        TEAMS.put("Анахайм#2023-2024", "https://api-web.nhle.com/v1/club-stats/ANA/20232024/2");

        TEAMS.put("Бостон#2024-2025", "https://api-web.nhle.com/v1/club-stats/BOS/20242025/2");
        TEAMS.put("Баффало#2024-2025", "https://api-web.nhle.com/v1/club-stats/BUF/20242025/2");
        TEAMS.put("Оттава#2024-2025", "https://api-web.nhle.com/v1/club-stats/OTT/20242025/2");
        TEAMS.put("Флорида#2024-2025", "https://api-web.nhle.com/v1/club-stats/FLA/20242025/2");
        TEAMS.put("Монреаль#2024-2025", "https://api-web.nhle.com/v1/club-stats/MTL/20242025/2");
        TEAMS.put("Тампа#2024-2025", "https://api-web.nhle.com/v1/club-stats/TBL/20242025/2");
        TEAMS.put("Торонто#2024-2025", "https://api-web.nhle.com/v1/club-stats/TOR/20242025/2");
        TEAMS.put("Детройт#2024-2025", "https://api-web.nhle.com/v1/club-stats/DET/20242025/2");
        TEAMS.put("Каролина#2024-2025", "https://api-web.nhle.com/v1/club-stats/CAR/20242025/2");
        TEAMS.put("Коламбус#2024-2025", "https://api-web.nhle.com/v1/club-stats/CBJ/20242025/2");
        TEAMS.put("Нью-Джерси#2024-2025", "https://api-web.nhle.com/v1/club-stats/NJD/20242025/2");
        TEAMS.put("Айлендерс#2024-2025", "https://api-web.nhle.com/v1/club-stats/NYI/20242025/2");
        TEAMS.put("Рейнджерс#2024-2025", "https://api-web.nhle.com/v1/club-stats/NYR/20242025/2");
        TEAMS.put("Филадельфия#2024-2025", "https://api-web.nhle.com/v1/club-stats/PHI/20242025/2");
        TEAMS.put("Питтсбург#2024-2025", "https://api-web.nhle.com/v1/club-stats/PIT/20242025/2");
        TEAMS.put("Вашингтон#2024-2025", "https://api-web.nhle.com/v1/club-stats/WSH/20242025/2");
        TEAMS.put("Чикаго#2024-2025", "https://api-web.nhle.com/v1/club-stats/CHI/20242025/2");
        TEAMS.put("Колорадо#2024-2025", "https://api-web.nhle.com/v1/club-stats/COL/20242025/2");
        TEAMS.put("Даллас#2024-2025", "https://api-web.nhle.com/v1/club-stats/DAL/20242025/2");
        TEAMS.put("Миннесота#2024-2025", "https://api-web.nhle.com/v1/club-stats/MIN/20242025/2");
        TEAMS.put("Нэшвилл#2024-2025", "https://api-web.nhle.com/v1/club-stats/NSH/20242025/2");
        TEAMS.put("Сент-Луис#2024-2025", "https://api-web.nhle.com/v1/club-stats/STL/20242025/2");
        TEAMS.put("Юта#2024-2025", "https://api-web.nhle.com/v1/club-stats/UTA/20242025/2");
        TEAMS.put("Виннипег#2024-2025", "https://api-web.nhle.com/v1/club-stats/WPG/20242025/2");
        TEAMS.put("Вегас#2024-2025", "https://api-web.nhle.com/v1/club-stats/VGK/20242025/2");
        TEAMS.put("Ванкувер#2024-2025", "https://api-web.nhle.com/v1/club-stats/VAN/20242025/2");
        TEAMS.put("Сиэтл#2024-2025", "https://api-web.nhle.com/v1/club-stats/SEA/20242025/2");
        TEAMS.put("Сан-Хосе#2024-2025", "https://api-web.nhle.com/v1/club-stats/SJS/20242025/2");
        TEAMS.put("Лос-Анджелес#2024-2025", "https://api-web.nhle.com/v1/club-stats/LAK/20242025/2");
        TEAMS.put("Эдмонтон#2024-2025", "https://api-web.nhle.com/v1/club-stats/EDM/20242025/2");
        TEAMS.put("Калгари#2024-2025", "https://api-web.nhle.com/v1/club-stats/CGY/20242025/2");
        TEAMS.put("Анахайм#2024-2025", "https://api-web.nhle.com/v1/club-stats/ANA/20242025/2");
    }
}
