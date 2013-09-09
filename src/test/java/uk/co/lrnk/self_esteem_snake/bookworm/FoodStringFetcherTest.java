package uk.co.lrnk.self_esteem_snake.bookworm;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import org.jsoup.Jsoup;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FoodStringFetcherTest {

    @Test
    public void testGetFoodStringFromWikipediaArticle() throws IOException {
        FoodStringFetcher fetcher = new FoodStringFetcher();

        RandomWikipediaDocumentGetter mockDocumentGetter = mock(RandomWikipediaDocumentGetter.class);
        String documentHTMLString = Resources.toString(Resources.getResource("uk/co/lrnk/self_esteem_snake/bookworm/sampleWikipediaArticleHTML.txt"), Charsets.UTF_8);
        when(mockDocumentGetter.getDocument()).thenReturn(Jsoup.parse(documentHTMLString));

        fetcher.setDocumentGetter(mockDocumentGetter);

        String expectedResult = Resources.toString(Resources.getResource("uk/co/lrnk/self_esteem_snake/bookworm/sampleWikipediaArticleExtractedText.txt"), Charsets.UTF_8);

        assertEquals(expectedResult, fetcher.getFoodString());
    }

}
