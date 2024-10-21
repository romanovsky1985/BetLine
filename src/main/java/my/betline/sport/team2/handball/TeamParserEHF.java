package my.betline.sport.team2.handball;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import my.betline.sport.team2.PlayerStat;
import my.betline.sport.team2.SeasonTeamParser;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class TeamParserEHF extends SeasonTeamParser {

    public TeamParserEHF(String leagueName, Map<String, String> leagueTeams) {
        super(leagueName, leagueTeams);
    }

    @Override
    public Map<String, Map<String, Double>> parse(String team) {
        try {
            URI uri = new URI(leagueTeams.get(team));
            JsonNode mainNode = objectMapper.readTree(uri.toURL());
            int teamGoals = 0;
            for (JsonNode playerNode : mainNode.get("players")) {
                teamGoals += playerNode.get("score").get("goals").intValue();
            }
            Map<String, Map<String, Double>> result = new HashMap<>();
            for (JsonNode playerNode : mainNode.get("players")) {
                String name = String.format("%s %s",
                        playerNode.get("person").get("firstName").textValue(), playerNode.get("person").get("lastName").textValue());
                double goals = playerNode.get("score").get("goals").intValue() / (double) teamGoals;
                if (goals > 0) {
                    result.put(name, Map.of("G", goals));
                }
            }
            return result;
        } catch (URISyntaxException | IOException e) {
            throw new RuntimeException("Ошибка загрузки");
        } catch (Exception e) {
            throw new RuntimeException("Ошибка разбора");
        }
    }

}
