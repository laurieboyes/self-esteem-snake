package uk.co.lrnk.self_esteem_snake.bookworm;

import java.awt.*;
import java.io.IOException;
import java.util.regex.Pattern;


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
        String foodString;
        foodString = documentGetter.getDocument().getElementById("mw-content-text").select("p:not([class])").text().trim();

        if (viewFont != null) {
            String sanitisedArticle = sanitiseStringForFont(foodString, viewFont);
            sanitisedArticle = removeStatementsInRoundBrackets(sanitisedArticle);
            sanitisedArticle = removeStatementsInSquareBrackets(sanitisedArticle);
            sanitisedArticle = removeMultipleSpaces(sanitisedArticle);
            sanitisedArticle = removeErroneousSpacesBeforePunctuation(sanitisedArticle);
            return sanitisedArticle;
        } else {
            return foodString;
        }
    }

    private String sanitiseStringForFont(String inString, Font font) {
        StringBuilder sanitisedString = new StringBuilder();
        for (char c : inString.toCharArray()) {
            if (font.canDisplay(c)) {
                sanitisedString.append(c);
            }
        }
        return sanitisedString.toString();
    }

    private String removeStatementsInRoundBrackets(String inString) {
        return inString.replaceAll("\\s*" + Pattern.quote("(") + "[^()]*" + Pattern.quote(")") + "\\s*", " ");
    }

    private String removeStatementsInSquareBrackets(String inString) {
        return inString.replaceAll("\\s*\\[[^\\[\\]]\\]\\s*", "");
    }

    private String removeMultipleSpaces(String inString) {
        return inString.replaceAll("\\s(\\s)+", " ");
    }

    private String removeErroneousSpacesBeforePunctuation(String inString) {
        String result = inString.replaceAll("\\s\\.", "\\.");
        result = result.replaceAll("\\s,", ",");
        return result;
    }
}
