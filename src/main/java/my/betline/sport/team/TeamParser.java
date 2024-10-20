package my.betline.sport.team;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;
import java.util.Set;

public abstract interface TeamParser {

    Map<String, Map<String, Double>> parse(String team);

    Set<String> getTeams();

    String getLeague();

}
