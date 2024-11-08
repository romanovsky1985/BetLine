package my.betline.controller;

import my.betline.page.TeamPage;
import my.betline.sport.core.PlayerCalculator;
import my.betline.sport.team.SeasonTeamParser;
import my.betline.sport.team.football.TeamParserEngland;
import my.betline.sport.team.football.TeamParserFrance;
import my.betline.sport.team.handball.TeamParserChampionsLeagueMen;
import my.betline.sport.team.icehockey.TeamParserKHL;
import my.betline.sport.team.icehockey.TeamParserNHL;
import my.betline.utils.LineFormatter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/team")
public class TeamController {
    private final Map<String, SeasonTeamParser> parsers = Map.ofEntries(
            Map.entry("EHF-CHAMPIONS-LEAGUE-MEN", new TeamParserChampionsLeagueMen()),
            Map.entry("NHL", new TeamParserNHL()),
            Map.entry("KHL", new TeamParserKHL()),
            Map.entry("PREMIER", new TeamParserEngland()),
            Map.entry("LIGUE1", new TeamParserFrance())
    );

    @GetMapping("/{league}")
    public String get(Model model, @PathVariable String league) {
        if (!parsers.containsKey(league.toUpperCase())) {
            throw new RuntimeException("Нет парсера для лиги: " + league);
        }
        SeasonTeamParser parser = parsers.get(league.toUpperCase());
        TeamPage page = new TeamPage();
        page.setParser(league);
        page.setTeams(parser.getTeams());
        page.setSeasons(parser.getSeasons());
        page.setExpected(parser.getDefaultExpected());
        model.addAttribute("page", page);
        return "sport/team.html";
    }

    @PostMapping("/{league}")
    public String post(Model model, @PathVariable String league, TeamPage page) {
        if (!parsers.containsKey(league.toUpperCase())) {
            throw new RuntimeException("Нет парсера для лиги: " + league);
        }
        SeasonTeamParser parser = parsers.get(league.toUpperCase());
        page.setParser(league);
        page.setTeams(parser.getTeams());
        page.setSeasons(parser.getSeasons());
        PlayerCalculator calculator = new PlayerCalculator(page.getExpected(), page.getTotal(), page.getMargin());
        var lines = calculator.calcPlayers(parser.parse(page.getTeam(), page.getSeason()));
        page.setLines(lines.entrySet().stream().collect(
                Collectors.toMap(Map.Entry::getKey, e -> LineFormatter.formatMap(e.getValue()))));
        model.addAttribute("page", page);
        return "sport/team.html";
    }

    @PostMapping("/{league}/expect")
    public String postExpect(Model model, @PathVariable String league, TeamPage page) {
        if (!parsers.containsKey(league.toUpperCase())) {
            throw new RuntimeException("Нет парсера для лиги: " + league);
        }
        SeasonTeamParser parser = parsers.get(league.toUpperCase());
        page.setParser(league);
        page.setTeams(parser.getTeams());
        page.setSeasons(parser.getSeasons());
        Map<String, Map<String, Double>> players = parser.parse(page.getTeam(), page.getSeason());
        Map<String, Map<String, String>> results = players.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey,
                e1 -> e1.getValue().entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, e2 -> e2.getValue().toString()))));
        page.setLines(results);
        model.addAttribute("page", page);
        return "sport/team.html";
    }


}
