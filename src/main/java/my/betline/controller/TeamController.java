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
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.List;

@Controller
@RequestMapping("/team")
public class TeamController {
    private TeamParserManager teamParserManager = new TeamParserManager(List.of(new TeamParserNHL()));

    @GetMapping("/{parser}")
    public String get(Model model, @PathVariable String parser) {
        TeamPage page = new TeamPage();
        page.setParser(parser);
        page.setTeams(teamParserManager.getTeams(parser));
        model.addAttribute("page", page);
        return "sport/team.html";
    }

    @PostMapping("/{parser}")
    public String post(Model model, @PathVariable String parser, TeamPage page) {
        page.setParser(parser);
        page.setTeams(teamParserManager.getTeams(parser));
        Map<String, Map<String, Double>> players = LineCalculator.calcPlayersLine(
                teamParserManager.parse(page.getTeam()), page.getExpected(), page.getMargin());
        page.setLines(players.entrySet().stream().collect(
                Collectors.toMap(Map.Entry::getKey, mv -> LineFormatter.formatMap(mv.getValue()))));
        model.addAttribute("page", page);
        return "sport/team.html";
    }


}
