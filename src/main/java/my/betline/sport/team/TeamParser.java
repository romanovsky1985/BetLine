package my.betline.sport.team;

import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class TeamParser {

    public abstract Map<String, Map<String, Double>> parse(String team);

    public abstract Set<String> getTeams();

    public abstract String getLeague();


}
