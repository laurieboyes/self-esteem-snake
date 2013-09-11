package uk.co.lrnk.self_esteem_snake.bookworm;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import org.jsoup.Jsoup;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;
import uk.co.lrnk.self_esteem_snake.ui.GamePanel;

import java.awt.*;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FoodStringFetcherTest {

    @Test
    public void testGetFoodStringFromWikipediaArticle() throws IOException {
        FoodStringFetcher fetcher = getFetcherToWithMockDocument("testArticleHTML.txt");
        String expectedResult = getStringFromResourceFile("testArticleExtractedText.txt");

        assertEquals(expectedResult, fetcher.getFoodString());
    }

    @Test
    public void testEscapeWeirdCharacters() throws IOException {

        FoodStringFetcher fetcher = getFetcherToWithMockDocument("testArticleWeirdCharsHTML.txt");
        String expectedResult = getStringFromResourceFile("testArticleWeirdCharsExtractedText.txt");

        assertEquals(expectedResult, fetcher.getFoodString());

    }

    @Test
    public void testRemoveStatementsInBrackets() throws IOException {

        FoodStringFetcher fetcher = getFetcherToWithMockDocument("testArticleWithParentheticalStatementsHTML.txt");
        String expectedResult = getStringFromResourceFile("testArticleWithParentheticalStatementsText.txt");

        assertEquals(expectedResult, fetcher.getFoodString());

    }

    private FoodStringFetcher getFetcherToWithMockDocument(String fileName) throws IOException {
        FoodStringFetcher fetcher = new FoodStringFetcher();

        RandomWikipediaDocumentGetter mockDocumentGetter = mock(RandomWikipediaDocumentGetter.class);
        String documentHTMLString = Resources.toString(Resources.getResource("uk/co/lrnk/self_esteem_snake/bookworm/" + fileName), Charsets.UTF_8);
        when(mockDocumentGetter.getDocument()).thenReturn(Jsoup.parse(documentHTMLString));

        fetcher.setDocumentGetter(mockDocumentGetter);
        fetcher.setViewFont((Font) ReflectionTestUtils.getField(new GamePanel(), "font"));

        return fetcher;
    }

    private String getStringFromResourceFile(String fileName) throws IOException {
        return Resources.toString(Resources.getResource("uk/co/lrnk/self_esteem_snake/bookworm/" + fileName), Charsets.UTF_8);
    }


}
