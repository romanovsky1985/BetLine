package my.betline.sport.player;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.PrintWriter;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Map;
import java.util.HashMap;

public class EliteProspectParser {
    public String parsePlayer(String link) throws Exception {
//        HttpClient httpClient = HttpClient.newHttpClient();
//        HttpRequest request = HttpRequest.newBuilder()
//                .uri(URI.create(link))
//                .build();
//        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

//                PrintWriter out = new PrintWriter("/tmp/test.txt");
//                out.println(response.body());
//                out.close();

//        Pattern jsonPattern = Pattern.compile("type=\"application/json\">([^<]*)<");
//        Matcher jsonMatcher = jsonPattern.matcher(response.body());
//        if (!jsonMatcher.find()) {
//            return "Can't parse json";
//        }
//
//        return jsonMatcher.group(1);
        Document document = Jsoup.connect(link).get();

//                PrintWriter out = new PrintWriter("/tmp/test.txt");
//                out.println(document.toString());
//                out.close();


        return document.body().toString();
    }


    public String parse(String link) throws Exception {
        String test = "";
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(link))
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        JsonNode mainNode = new ObjectMapper().readTree(response.body());
        int totalGoals = 0;
        int maxPlayed = 0;
        for (JsonNode skater : mainNode.get("skaters")) {
            totalGoals += skater.get("goals").intValue();
            if (skater.get("gamesPlayed").intValue() > maxPlayed) {
                maxPlayed = skater.get("gamesPlayed").intValue();
            } 
        }
        Map<String, Map<String, Double>> result = new HashMap<>();
        //List<Player> players = new ArrayList<>();
        for (JsonNode skater : mainNode.get("skaters")) {
            int played = skater.get("gamesPlayed").intValue();
            int goals = skater.get("goals").intValue();
            int assists = skater.get("assists").intValue();
            String name = skater.get("firstName").get("default").textValue() + " " + skater.get("lastName").get("default").textValue();
            if (played > maxPlayed / 3) {
                double proportion = (double) maxPlayed / (double) played;
                double goalsByScore = (proportion * goals) / totalGoals;
                double assistsByScore = (proportion * assists) / totalGoals;
                //players.add(new Player(name, goalsByScore, assistsByScore));
                result.put(name, Map.of("goalsByScore", goalsByScore, "assistsByScore", assistsByScore,
                "Гол ДА", 1 - Math.exp(-goalsByScore), 
                "Передача ДА", 1 - Math.exp(-assistsByScore),
                "Очко ДА", 1 - Math.exp(-(goalsByScore + assistsByScore))
));
            }
        }


        test += result;

        return test;
    }
}
