package my.betline.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/sport")
public class SportController {
    @GetMapping(path = "/icehockey")
    public String icehockey() {
        return "sport/icehockey.html";
    }
}
