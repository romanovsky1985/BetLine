package my.betline.controller;

import my.betline.page.TeamPage;
import my.betline.sport.core.LineCalculator;
import my.betline.sport.team.TeamParser;
import my.betline.utils.LineFormatter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/team")
public class TeamController {
    @GetMapping("/{parser}")
    public String get(Model model, @PathVariable String parser) {
        TeamPage page = new TeamPage();
        TeamParser teamParser = TeamParser.getParser(parser);
        page.setParser(parser);
        page.setTeams(teamParser.getTeams());
        model.addAttribute("page", page);
        return "sport/team.html";
    }

    @PostMapping("/{parser}")
    public String post(Model model, @PathVariable String parser, TeamPage page) {
        TeamParser teamParser = TeamParser.getParser(parser);
        page.setParser(parser);
        page.setTeams(teamParser.getTeams());
        Map<String, Map<String, Double>> players = LineCalculator.calcPlayersLine(
                teamParser.parse(page.getTeam()), page.getExpected(), page.getMargin());
        page.setLines(players.entrySet().stream().collect(
                Collectors.toMap(Map.Entry::getKey, mv -> LineFormatter.formatMap(mv.getValue()))));
        model.addAttribute("page", page);
        return "sport/team.html";
    }
}
