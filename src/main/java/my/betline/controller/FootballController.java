package my.betline.controller;

import my.betline.page.FootballPage;
import my.betline.page.IceHockeyPage;
import my.betline.sport.icehockey.IceHockeyCalculator;
import my.betline.utils.LineEntryFormatter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.DecimalFormat;
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
        IceHockeyCalculator calculator = new IceHockeyCalculator(10_000, page.getMargin(), null);
//        DecimalFormat formatter = new DecimalFormat(".##");
//        System.out.println(page.getGame());
//        Map<String, String> line = calculator.calcLine(page.getGame()).entrySet().stream()
//                        .collect(Collectors.toMap(Map.Entry::getKey, new LineEntryFormatter()));
        model.addAttribute("page", page);
//        model.addAttribute("line", line);
        return "sport/football.html";
    }
}
