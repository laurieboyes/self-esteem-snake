package uk.co.lrnk.self_esteem_snake.bookworm;

import java.io.IOException;


public class FoodStringFetcher {

    RandomWikipediaDocumentGetter documentGetter = new RandomWikipediaDocumentGetter();

    public void setDocumentGetter(RandomWikipediaDocumentGetter documentGetter) {
        this.documentGetter = documentGetter;
    }

    public String getFoodString() throws IOException {
        return documentGetter.getDocument().getElementById("mw-content-text").select("p:not([class])").text();
    }
}
