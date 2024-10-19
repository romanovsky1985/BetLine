package my.betline.controller;

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
        Document document = Jsoup.connect("https://www.espn.com/soccer/team/stats/_/id/364/league/ENG.1/season/2023").get();
        Pattern espnPattern = Pattern.compile("\"tableRows\":.*]]]");
        Matcher espnMatcher = espnPattern.matcher(document.body().data());
        espnMatcher.find();
        JsonNode mainNode = new ObjectMapper().readTree("{" + espnMatcher.group() + "}");
        JsonNode sArray = mainNode.get("tableRows").get(0);
        JsonNode aArray = mainNode.get("tableRows").get(1);
        //аналогичный путь в джсоне к передачам
        String test = "";
        for (JsonNode player : sArray) {
            String name = player.get(1).get("name").textValue();
            int played = player.get(2).get("value").intValue();
            int scored = player.get(3).get("value").intValue();
            test += name + ", " + played + ", " + scored + "<br>";
        }
        return test;
    }
}
