package my.betline.page;

import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
public class TeamPage {
    private String team;
    private List<String> teams = List.of();
    private Map<String, Map<String, String>> lines = Map.of();
    private double margin = 0.075;
    private double expected = 2.5;
    private String parser;

    public double getMarginPercent() {
        return margin * 100;
    }

    public void setMarginPercent(double percent) {
        margin = percent / 100;
    }
}
