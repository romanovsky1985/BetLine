package my.betline.sport.team;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

public interface TeamParser {
    Map<String, Map<String, Double>> parse(String team);
    Map<String, String> getTeams();
}
