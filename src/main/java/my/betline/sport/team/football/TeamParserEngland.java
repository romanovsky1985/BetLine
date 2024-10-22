package my.betline.sport.team.football;

import java.util.HashMap;
import java.util.Map;

public class TeamParserEngland extends TeamParserESPN {

    public TeamParserEngland() {
        super(TEAMS);
    }

    private static final Map<String, String> TEAMS = new HashMap<>();

    static {

        TEAMS.put("Ливерпуль#2023-2024", "https://www.espn.com/soccer/team/stats/_/id/364/league/ENG.1/season/2023");
        TEAMS.put("Манчестер Сити#2023-2024", "https://www.espn.com/soccer/team/stats/_/id/382/league/ENG.1/season/2023");
        TEAMS.put("Арсенал#2023-2024", "https://www.espn.com/soccer/team/stats/_/id/359/league/ENG.1/season/2023");
        TEAMS.put("Брайтон#2023-2024", "https://www.espn.com/soccer/team/stats/_/id/331/league/ENG.1/season/2023");
        TEAMS.put("Челси#2023-2024", "https://www.espn.com/soccer/team/stats/_/id/363/league/ENG.1/season/2023");
        TEAMS.put("Тоттенхем#2023-2024", "https://www.espn.com/soccer/team/stats/_/id/367/league/ENG.1/season/2023");
        TEAMS.put("Ньюкасл#2023-2024", "https://www.espn.com/soccer/team/stats/_/id/361/league/ENG.1/season/2023");
        TEAMS.put("Фулхэм#2023-2024", "https://www.espn.com/soccer/team/stats/_/id/370/league/ENG.1/season/2023");
        TEAMS.put("Борнмут#2023-2024", "https://www.espn.com/soccer/team/stats/_/id/349/league/ENG.1/season/2023");
        TEAMS.put("Манчестер Юнайтед#2023-2024", "https://www.espn.com/soccer/team/stats/_/id/360/league/ENG.1/season/2023");
        TEAMS.put("Ноттингем#2023-2024", "https://www.espn.com/soccer/team/stats/_/id/393/league/ENG.1/season/2023");
        TEAMS.put("Брентфорд#2023-2024", "https://www.espn.com/soccer/team/stats/_/id/337/league/ENG.1/season/2023");
        TEAMS.put("Лестер#2023-2024", "https://www.espn.com/soccer/team/stats/_/id/375/league/ENG.2/season/2023");
        TEAMS.put("Вест Хэм#2023-2024", "https://www.espn.com/soccer/team/stats/_/id/371/league/ENG.1/season/2023");
        TEAMS.put("Эвертон#2023-2024", "https://www.espn.com/soccer/team/stats/_/id/368/league/ENG.1/season/2023");
        TEAMS.put("Ипсвич#2023-2024", "https://www.espn.com/soccer/team/stats/_/id/373/league/ENG.2/season/2023");
        TEAMS.put("Кристал Пэлас#2023-2024", "https://www.espn.com/soccer/team/stats/_/id/384/league/ENG.1/season/2023");
        TEAMS.put("Вульверхемптон#2023-2024", "https://www.espn.com/soccer/team/stats/_/id/380/league/ENG.1/season/2023");
        TEAMS.put("Саутхемптон#2023-2024", "https://www.espn.com/soccer/team/stats/_/id/376/league/ENG.2/season/2023");

        TEAMS.put("Ливерпуль#2024-2025", "https://www.espn.com/soccer/team/stats/_/id/364/league/ENG.1/season/2024");
        TEAMS.put("Манчестер Сити#2024-2025", "https://www.espn.com/soccer/team/stats/_/id/382/league/ENG.1/season/2024");
        TEAMS.put("Арсенал#2024-2025", "https://www.espn.com/soccer/team/stats/_/id/359/league/ENG.1/season/2024");
        TEAMS.put("Брайтон#2024-2025", "https://www.espn.com/soccer/team/stats/_/id/331/league/ENG.1/season/2024");
        TEAMS.put("Челси#2024-2025", "https://www.espn.com/soccer/team/stats/_/id/363/league/ENG.1/season/2024");
        TEAMS.put("Тоттенхем#2024-2025", "https://www.espn.com/soccer/team/stats/_/id/367/league/ENG.1/season/2024");
        TEAMS.put("Ньюкасл#2024-2025", "https://www.espn.com/soccer/team/stats/_/id/361/league/ENG.1/season/2024");
        TEAMS.put("Фулхэм#2024-2025", "https://www.espn.com/soccer/team/stats/_/id/370/league/ENG.1/season/2024");
        TEAMS.put("Борнмут#2024-2025", "https://www.espn.com/soccer/team/stats/_/id/349/league/ENG.1/season/2024");
        TEAMS.put("Манчестер Юнайтед#2024-2025", "https://www.espn.com/soccer/team/stats/_/id/360/league/ENG.1/season/2024");
        TEAMS.put("Ноттингем#2024-2025", "https://www.espn.com/soccer/team/stats/_/id/393/league/ENG.1/season/2024");
        TEAMS.put("Брентфорд#2024-2025", "https://www.espn.com/soccer/team/stats/_/id/337/league/ENG.1/season/2024");
        TEAMS.put("Лестер#2024-2025", "https://www.espn.com/soccer/team/stats/_/id/375/league/ENG.1/season/2024");
        TEAMS.put("Вест Хэм#2024-2025", "https://www.espn.com/soccer/team/stats/_/id/371/league/ENG.1/season/2024");
        TEAMS.put("Эвертон#2024-2025", "https://www.espn.com/soccer/team/stats/_/id/368/league/ENG.1/season/2024");
        TEAMS.put("Ипсвич#2024-2025", "https://www.espn.com/soccer/team/stats/_/id/373/league/ENG.1/season/2024");
        TEAMS.put("Кристал Пэлас#2024-2025", "https://www.espn.com/soccer/team/stats/_/id/384/league/ENG.1/season/2024");
        TEAMS.put("Вульверхемптон#2024-2025", "https://www.espn.com/soccer/team/stats/_/id/380/league/ENG.1/season/2024");
        TEAMS.put("Саутхемптон#2024-2025", "https://www.espn.com/soccer/team/stats/_/id/376/league/ENG.1/season/2024");
    }
}
