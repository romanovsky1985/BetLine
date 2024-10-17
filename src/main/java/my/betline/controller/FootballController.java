package my.betline.controller;

import my.betline.page.FootballPage;
import my.betline.sport.core.LineCalculator;
import my.betline.sport.football.FootballCalculator;
import my.betline.utils.LineFormatter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.core.task.TaskExecutor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping(path = "/sport/football")
public class FootballController {
    @Autowired
    private TaskExecutor executor;

    @GetMapping
    public String get(Model model) {
        FootballPage page = new FootballPage();
        model.addAttribute("page", page);
        return "sport/football.html";
    }

    @PostMapping
    public String post(Model model, FootballPage page) {
        FootballCalculator calculator = LineCalculator.builder(FootballCalculator.class)
                .setMargin(page.getMargin())
                .setExecutor(executor)
                .build();
        Map<String, Double> line = calculator.calcLine(page.getGame());
        page.setLine(LineFormatter.formatMap(line));
        model.addAttribute("page", page);
        return "sport/football.html";
    }
}
