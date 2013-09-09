package uk.co.lrnk.self_esteem_snake.bookworm;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class RandomWikipediaDocumentGetter {

    public Document getDocument() throws IOException {
        return Jsoup.connect("http://en.wikipedia.org/wiki/Special:Random").get();
    }

}
