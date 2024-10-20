package my.betline.sport.team;

import java.util.Map;
import java.util.Set;

public interface TeamParser {

    Map<String, Map<String, Double>> parse(String teamWithSeason);

    Set<String> getTeams();

    boolean canParse(String teamWithSeason);

    String getLeague();


}
