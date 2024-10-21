package my.betline.sport.team2.handball;

import java.util.Map;

public class TeamParserChampionsLeagueMen extends TeamParserEHF {

    public TeamParserChampionsLeagueMen() {
        super("EHF-CHAMPIONS-LEAGUE-M", TEAMS);
    }

    public static final Map<String, String> TEAMS = Map.ofEntries(
            Map.entry("Барселона#2024-2025", "https://ehfcl.eurohandball.com/umbraco/api/clubdetailsapi/GetPlayers?competitionId=dddNZFVk1jT5WO1phIlREg&clubId=caQUSFvFaPlT7EN8zrPZaA&roundId=Xg2Ubz1_S9dxy0p0unxCBg&culture=en-US&contentId=109248"),
            Map.entry("Динамо Бухарест#2024-2025", "https://ehfcl.eurohandball.com/umbraco/api/clubdetailsapi/GetPlayers?competitionId=dddNZFVk1jT5WO1phIlREg&clubId=ZkfCu81QMPee22wu_nX_jQ&roundId=Xg2Ubz1_S9dxy0p0unxCBg&culture=en-US&contentId=109248"),
            Map.entry("Спортинг", ""),
            Map.entry("Фредерисия", ""),
            Map.entry("Висла Плоцк", ""),
            Map.entry("Нант", ""),
            Map.entry("Загреб", ""),
            Map.entry("Виве Кельце", ""),
            Map.entry("Ольборг", ""),
            Map.entry("Пик Сегед", ""),
            Map.entry("Кольстад", ""),
            Map.entry("Фюксе Берлин", ""),
            Map.entry("ПСЖ", ""),
            Map.entry("Еврофарм Пелистер", ""),
            Map.entry("Веспрем", ""),
            Map.entry("Магдебург", "")
    );
}
