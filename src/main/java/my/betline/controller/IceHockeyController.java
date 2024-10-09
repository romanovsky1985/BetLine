package my.betline.controller;

import my.betline.page.IceHockeyPage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/sport/icehockey")
public class IceHockeyController {
    @GetMapping
    public String get(Model model) {
        IceHockeyPage page = new IceHockeyPage();
        model.addAttribute("page", page);
        return "sport/icehockey.html";
    }

    @PostMapping
    public String post(Model model, IceHockeyPage page) {
        model.addAttribute("page", page);
        return "sport/icehockey.html";
    }
}
