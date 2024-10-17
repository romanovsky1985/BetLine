package my.betline.controller;

import my.betline.page.IceHockeyPage;
import my.betline.sport.core.LineCalculator;
import my.betline.sport.icehockey.IceHockeyCalculator;
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
@RequestMapping(path = "/sport/icehockey")
public class IceHockeyController {
    @Autowired
    private TaskExecutor executor;

    @GetMapping
    public String get(Model model) {
        IceHockeyPage page = new IceHockeyPage();
        model.addAttribute("page", page);
        return "sport/icehockey.html";
    }

    @PostMapping
    public String post(Model model, IceHockeyPage page) {
        IceHockeyCalculator calculator = LineCalculator.builder(IceHockeyCalculator.class)
                .setMargin(page.getMargin())
                .setExecutor(executor)
                .setIterations(30_000)
                .build();
        Map<String, Double> line = calculator.calcLine(page.getGame());
        page.setLine(LineFormatter.formatMap(line));
        model.addAttribute("page", page);
        return "sport/icehockey.html";
    }
}
