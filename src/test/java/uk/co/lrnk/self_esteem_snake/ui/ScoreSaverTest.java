package uk.co.lrnk.self_esteem_snake.ui;

import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;
import uk.co.lrnk.self_esteem_snake.ScoreSaver;

import java.io.*;

import static junit.framework.Assert.assertEquals;

public class ScoreSaverTest {

    @Test
    public void testSaveScore() throws IOException {
        ScoreSaver scoreSaver = new ScoreSaver();
        String scoreFilePath = File.createTempFile("ScoreSaverTest.testSaveScore", "tmp").getPath();
        ReflectionTestUtils.setField(scoreSaver, "scoreFilePath", scoreFilePath);

        scoreSaver.saveScore(10);

        assertEquals("10", IOUtils.toString(new FileInputStream(scoreFilePath)));

    }

    @Test
    public void testGetScore() throws IOException {
        ScoreSaver scoreSaver = new ScoreSaver();
        String scoreFilePath = File.createTempFile("ScoreSaverTest.testGetScore", "tmp").getPath();
        ReflectionTestUtils.setField(scoreSaver, "scoreFilePath", scoreFilePath);

        IOUtils.write("10", new FileOutputStream(scoreFilePath));

        assertEquals(10, scoreSaver.getSavedScore());
    }

    @Test
    public void testGetScoreNoFileExists() {
        ScoreSaver scoreSaver = new ScoreSaver();
        String scoreFilePath = "/no/path/at/all";
        ReflectionTestUtils.setField(scoreSaver, "scoreFilePath", scoreFilePath);

        assertEquals(0, scoreSaver.getSavedScore());
    }

}
