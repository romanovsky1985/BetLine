package my.betline.controller;

import my.betline.page.TeamPage;
import my.betline.sport.core.LineCalculator;
import my.betline.sport.team.*;
import my.betline.utils.LineFormatter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.List;

@Controller
@RequestMapping("/team")
public class TeamController {
    private final TeamParserManager teamParserManager = new TeamParserManager(List.of(
            new TeamParserNHL(), 
            new TeamParserEngland(),
            new TeamParserFrance()));

    @GetMapping("/{league}")
    public String get(Model model, @PathVariable String league) {
        TeamPage page = new TeamPage();
        page.setParser(league);
        page.setTeams(teamParserManager.getTeams(league));
        model.addAttribute("page", page);
        return "sport/team.html";
    }

    @PostMapping("/{league}")
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


}
