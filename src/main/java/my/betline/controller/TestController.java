package my.betline.controller;

import my.betline.sport.core.PlayerCalculator;
import my.betline.sport.team2.handball.TeamParserChampionsLeagueMen;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

@RestController
@RequestMapping(path = "/test")
public class TestController {

    @GetMapping
    public String test() throws Exception {
        TeamParserChampionsLeagueMen parser = new TeamParserChampionsLeagueMen();
        PlayerCalculator calculator = new PlayerCalculator(35, 3.5, 0.075);
        String test = calculator.calcPlayers(parser.parse("Барселона", "2024-2025")).toString();
        return test;
    }
}
