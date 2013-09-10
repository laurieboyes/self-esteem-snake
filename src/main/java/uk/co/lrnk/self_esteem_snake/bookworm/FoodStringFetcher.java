package uk.co.lrnk.self_esteem_snake.bookworm;

import java.awt.*;
import java.io.IOException;


public class FoodStringFetcher {

    private Font viewFont = null;
    RandomWikipediaDocumentGetter documentGetter = new RandomWikipediaDocumentGetter();

    public void setDocumentGetter(RandomWikipediaDocumentGetter documentGetter) {
        this.documentGetter = documentGetter;
    }

    public void setViewFont(Font viewFont) {
        this.viewFont = viewFont;
    }

    public String getFoodString() throws IOException {
        String articleString =  documentGetter.getDocument().getElementById("mw-content-text").select("p:not([class])").text().trim();

        if(viewFont != null) {
            String sanitisedArticle = sanitiseStringForFont(articleString, viewFont);
            sanitisedArticle = removeMultipleSpaces(sanitisedArticle);
            return sanitisedArticle;
        } else {
            return articleString;
        }
    }

    private String sanitiseStringForFont(String inString, Font font) {
        StringBuilder sanitisedString = new StringBuilder();
        for(char c: inString.toCharArray()) {
            if(font.canDisplay(c)) {
                sanitisedString.append(c);
            }
        }
        return sanitisedString.toString();
    }

    private String removeMultipleSpaces(String inString) {
        return inString.replaceAll("\\s(\\s)+", " ");
    }
}
