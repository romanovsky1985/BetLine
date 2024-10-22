package my.betline.sport.team.handball;

import com.fasterxml.jackson.databind.JsonNode;
import my.betline.sport.team.SeasonTeamParser;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public abstract class TeamParserEHF extends SeasonTeamParser {

    public TeamParserEHF(Map<String, String> leagueTeams) {
        super(leagueTeams);
    }

    @Override
    public double getDefaultExpected() {
        return 29.5;
    }

    @Override
    protected Map<String, Map<String, Double>> parse(String team) {
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
                    result.put(name, Map.of("Голы", goals));
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
