package my.betline.controller;

import my.betline.sport.team.icehockey.TeamParserKHL;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/test")
public class TestController {

    @GetMapping
    public String test() throws Exception {
        
        TeamParserKHL parser = new TeamParserKHL();

        return "test";
    }
}
