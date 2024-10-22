package my.betline.controller;

import my.betline.page.TeamPage;
import my.betline.sport.core.LineCalculator;
import my.betline.sport.core.PlayerCalculator;
import my.betline.sport.team.*;
import my.betline.sport.team2.SeasonTeamParser;
import my.betline.sport.team2.handball.TeamParserChampionsLeagueMen;
import my.betline.utils.LineFormatter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.List;

@Controller
public class TeamController {
    private final TeamParserManager teamParserManager = new TeamParserManager(List.of(
            new TeamParserNHL(), 
            new TeamParserEngland(),
            new TeamParserFrance()));

    @GetMapping("/team/{league}")
    public String get(Model model, @PathVariable String league) {
        TeamPage page = new TeamPage();
        page.setParser(league);
        page.setTeams(teamParserManager.getTeams(league));
        model.addAttribute("page", page);
        return "sport/team.html";
    }

    @PostMapping("/team/{league}")
    public String post(Model model, @PathVariable String league, TeamPage page) {
        page.setParser(league);
        page.setTeams(teamParserManager.getTeams(league));
        Map<String, Map<String, Double>> players = LineCalculator.calcPlayersLine(
                teamParserManager.parse(page.getTeam(), page.getSeason()), page.getExpected(), page.getMargin());
        page.setLines(players.entrySet().stream().collect(
                Collectors.toMap(Map.Entry::getKey, mv -> LineFormatter.formatMap(mv.getValue()))));
        model.addAttribute("page", page);
        return "sport/team.html";
    }

    private final Map<String, SeasonTeamParser> parsers = Map.ofEntries(
            Map.entry("EHF-CHAMPIONS-LEAGUE-MEN", new TeamParserChampionsLeagueMen()),
            Map.entry("NHL", new my.betline.sport.team2.icehockey.TeamParserNHL()),
            Map.entry("PREMIER", new my.betline.sport.team2.football.TeamParserEngland()),
            Map.entry("LIGUE1", new my.betline.sport.team2.football.TeamParserFrance())
    );

    @GetMapping("/team2/{league}")
    public String get2(Model model, @PathVariable String league) {
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
        return "sport/team2.html";
    }

    @PostMapping("/team2/{league}")
    public String post2(Model model, @PathVariable String league, TeamPage page) {
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
        return "sport/team2.html";
    }


}
