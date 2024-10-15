package my.betline.sport.player;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class EliteProspectParser {
    public String parse(String link) throws Exception {
        Document html = Jsoup.connect(link).get();
        return html.title();
    }
}
