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
        return "test";
    }
}
