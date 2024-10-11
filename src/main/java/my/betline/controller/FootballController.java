package my.betline.controller;

import my.betline.page.FootballPage;
import my.betline.sport.football.FootballCalculator;
import my.betline.utils.LineEntryFormatter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping(path = "/sport/football")
public class FootballController {
    @GetMapping
    public String get(Model model) {
        FootballPage page = new FootballPage();
        model.addAttribute("page", page);
        return "sport/football.html";
    }

    @PostMapping
    public String post(Model model, FootballPage page) {
        FootballCalculator calculator = new FootballCalculator(10_000, page.getMargin(), null);
        Map<String, Double> line = calculator.calcLine(page.getGame());
        page.setLine(line.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, LineEntryFormatter::format)));
        model.addAttribute("page", page);
        return "sport/football.html";
    }
}
