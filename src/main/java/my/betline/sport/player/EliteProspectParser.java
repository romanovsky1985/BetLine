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

                PrintWriter out = new PrintWriter("/tmp/test.txt");
                out.println(document.toString());
                out.close();


        return document.body().toString();
    }


    public String parse(String link) throws Exception {
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(link))
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

//        PrintWriter out = new PrintWriter("/tmp/test.txt");
//        out.println(response.body());
//        out.close();

        final String teamPatter = "2023-2024<\\/a><\\/td><[^<]*<[^<]*<\\/a><\\/td><[^>]*>(\\d+)<\\/td>";
        //Pattern teamPattern = Pattern.compile("2023-2024</a></td><[^<]*<[^<]*</a></td><[^>]*>(\\d+)</td>");
        Pattern teamPattern = Pattern.compile("2023-2024</a></td><[^<]*<[^<]*</a></td>[^>]*>(\\d+)</td><[^<]*</td><[^<]*</td><[^<]*</td><[^<]*</td><[^<]*</td>[^>]*>(\\d+)</td>");
        Matcher teamMatcher = teamPattern.matcher(response.body());

        if (!teamMatcher.find()) {
            return "Can't parse team";
        }

        int teamGames = Integer.parseInt(teamMatcher.group(1));
        int teamScores = Integer.parseInt(teamMatcher.group(2));

        Pattern jsonPattern = Pattern.compile("type=\"application/json\">([^<]*)<");
        Matcher jsonMatcher = jsonPattern.matcher(response.body());
        if (!jsonMatcher.find()) {
            return "Can't parse json";
        }

        String test = "";
        test += "teamGames = " + teamGames + "\n";
        test += "teamScores = " + teamScores + "\n";

        JsonNode mainNode = new ObjectMapper().readTree(jsonMatcher.group(1));

        for (JsonNode player : mainNode.get("props").get("pageProps").get("rosterList").get("tableData").get("edges")) {
            //System.out.println(player.get("player").get("name").textValue());
            System.out.println(player.get("player").get("eliteprospectsUrlPath"));
        }



        return test;
    }
}
