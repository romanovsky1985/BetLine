package my.betline.sport.team;

import java.util.Map;
import java.util.Set;

public interface TeamParser {

    Map<String, Map<String, Double>> parse(String team);

    Set<String> getTeams();

    String getLeague();

}
