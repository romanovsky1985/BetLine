package my.betline.controller;

import my.betline.sport.player.EliteProspectParser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/test")
public class TestController {

    @GetMapping
    public String test() throws Exception {
        EliteProspectParser parser = new EliteProspectParser();
        return parser.parse("https://www.eliteprospects.com/player/339555/artyom-galimov");
    }
}
