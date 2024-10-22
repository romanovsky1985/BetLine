package my.betline.sport.team.handball;

import java.util.Map;

public class TeamParserChampionsLeagueMen extends TeamParserEHF {

    public TeamParserChampionsLeagueMen() {
        super(TEAMS);
    }

    private static final Map<String, String> TEAMS = Map.ofEntries(
            Map.entry("Барселона#2024-2025", "https://ehfcl.eurohandball.com/umbraco/api/clubdetailsapi/GetPlayers?competitionId=dddNZFVk1jT5WO1phIlREg&clubId=caQUSFvFaPlT7EN8zrPZaA&roundId=Xg2Ubz1_S9dxy0p0unxCBg&culture=en-US&contentId=109248"),
            Map.entry("Динамо Бухарест#2024-2025", "https://ehfcl.eurohandball.com/umbraco/api/clubdetailsapi/GetPlayers?competitionId=dddNZFVk1jT5WO1phIlREg&clubId=ZkfCu81QMPee22wu_nX_jQ&roundId=Xg2Ubz1_S9dxy0p0unxCBg&culture=en-US&contentId=109248"),
            Map.entry("Спортинг#2024-2025", "https://ehfcl.eurohandball.com/umbraco/api/clubdetailsapi/GetPlayers?competitionId=dddNZFVk1jT5WO1phIlREg&clubId=_q2ySU3AKuzidiJ8KmSvhw&roundId=Xg2Ubz1_S9dxy0p0unxCBg&culture=en-US&contentId=109248"),
            Map.entry("Фредерисия#2024-2025", "https://ehfcl.eurohandball.com/umbraco/api/clubdetailsapi/GetPlayers?competitionId=dddNZFVk1jT5WO1phIlREg&clubId=7TEUyarPrgtD-hkwAREsnQ&roundId=Xg2Ubz1_S9dxy0p0unxCBg&culture=en-US&contentId=109248"),
            Map.entry("Висла Плоцк#2024-2025", "https://ehfcl.eurohandball.com/umbraco/api/clubdetailsapi/GetPlayers?competitionId=dddNZFVk1jT5WO1phIlREg&clubId=Qg5-SUEq-U2qV3euQZ48RQ&roundId=Xg2Ubz1_S9dxy0p0unxCBg&culture=en-US&contentId=109248"),
            Map.entry("Нант#2024-2025", "https://ehfcl.eurohandball.com/umbraco/api/clubdetailsapi/GetPlayers?competitionId=dddNZFVk1jT5WO1phIlREg&clubId=UlvtLsTlg8pJWiC7dxqtTg&roundId=Xg2Ubz1_S9dxy0p0unxCBg&culture=en-US&contentId=109248"),
            Map.entry("Загреб#2024-2025", "https://ehfcl.eurohandball.com/umbraco/api/clubdetailsapi/GetPlayers?competitionId=dddNZFVk1jT5WO1phIlREg&clubId=fPt471s6HJshvKo2LhvMJQ&roundId=Xg2Ubz1_S9dxy0p0unxCBg&culture=en-US&contentId=109248"),
            Map.entry("Виве Кельце#2024-2025", "https://ehfcl.eurohandball.com/umbraco/api/clubdetailsapi/GetPlayers?competitionId=dddNZFVk1jT5WO1phIlREg&clubId=puFBDRyrok_Gc2X-z9Jq0w&roundId=Xg2Ubz1_S9dxy0p0unxCBg&culture=en-US&contentId=109248"),
            Map.entry("Ольборг#2024-2025", "https://ehfcl.eurohandball.com/umbraco/api/clubdetailsapi/GetPlayers?competitionId=dddNZFVk1jT5WO1phIlREg&clubId=r3kx-fSDDsxclIR8-Kt8bA&roundId=Xg2Ubz1_S9dxy0p0unxCBg&culture=en-US&contentId=109248"),
            Map.entry("Пик Сегед#2024-2025", "https://ehfcl.eurohandball.com/umbraco/api/clubdetailsapi/GetPlayers?competitionId=dddNZFVk1jT5WO1phIlREg&clubId=o6ldhiKKGS3pSYfUOHfy-Q&roundId=Xg2Ubz1_S9dxy0p0unxCBg&culture=en-US&contentId=109248"),
            Map.entry("Кольстад#2024-2025", "https://ehfcl.eurohandball.com/umbraco/api/clubdetailsapi/GetPlayers?competitionId=dddNZFVk1jT5WO1phIlREg&clubId=kZVHhs7nxEhEqyCf6iK_Sw&roundId=Xg2Ubz1_S9dxy0p0unxCBg&culture=en-US&contentId=109248"),
            Map.entry("Фюксе Берлин#2024-2025", "https://ehfcl.eurohandball.com/umbraco/api/clubdetailsapi/GetPlayers?competitionId=dddNZFVk1jT5WO1phIlREg&clubId=djTz_stKMDorjkgRSoN_-A&roundId=Xg2Ubz1_S9dxy0p0unxCBg&culture=en-US&contentId=109248"),
            Map.entry("ПСЖ#2024-2025", "https://ehfcl.eurohandball.com/umbraco/api/clubdetailsapi/GetPlayers?competitionId=dddNZFVk1jT5WO1phIlREg&clubId=djTz_stKMDorjkgRSoN_-A&roundId=Xg2Ubz1_S9dxy0p0unxCBg&culture=en-US&contentId=109248"),
            Map.entry("Еврофарм Пелистер#2024-2025", "https://ehfcl.eurohandball.com/umbraco/api/clubdetailsapi/GetPlayers?competitionId=dddNZFVk1jT5WO1phIlREg&clubId=9AOyqB6NalZpkivglwHdJg&roundId=Xg2Ubz1_S9dxy0p0unxCBg&culture=en-US&contentId=109248"),
            Map.entry("Веспрем#2024-2025", "https://ehfcl.eurohandball.com/umbraco/api/clubdetailsapi/GetPlayers?competitionId=dddNZFVk1jT5WO1phIlREg&clubId=jV5CzKP5W_-TGMWEwA-jVQ&roundId=Xg2Ubz1_S9dxy0p0unxCBg&culture=en-US&contentId=109248"),
            Map.entry("Магдебург#2024-2025", "https://ehfcl.eurohandball.com/umbraco/api/clubdetailsapi/GetPlayers?competitionId=dddNZFVk1jT5WO1phIlREg&clubId=zKttAiKCyYIIy_T_l4gBLQ&roundId=Xg2Ubz1_S9dxy0p0unxCBg&culture=en-US&contentId=109248")
    );
}
