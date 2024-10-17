package my.betline.controller;

import my.betline.sport.core.LineCalculator;
import my.betline.sport.team.TeamParser;
import my.betline.sport.team.TeamParserNHL;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/test")
public class TestController {

    @GetMapping
    public String test() throws Exception {
        TeamParser parser = new TeamParserNHL();

        return LineCalculator.calcPlayersLine(parser.parse("Детройт"), 2.5, 0.05).toString();
    }
}
