package my.betline.sport.team2;

import java.util.List;
import java.util.Map;

public interface TeamParser {

    Map<String, Map<String, Double>> parse(String team);

    List<String> getTeams();

    String getLeague();

}
