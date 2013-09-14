package uk.co.lrnk.self_esteem_snake;

import org.apache.commons.io.IOUtils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ScoreSaver {

    private String scoreFilePath = "self-esteem-snake-score";

    public ScoreSaver() {

        String userHome = System.getProperty("user.home");
        String scoreFileName = "self-esteem-snake-score";

        scoreFilePath = userHome + "\\" + scoreFileName;
    }


    public void saveScore(int score) {

        FileWriter writer = null;

        try {
            writer = new FileWriter(scoreFilePath);
            IOUtils.write("" + score, writer);
        } catch (IOException e) {
            System.err.println("Couldn't save score (" + score + ") to file location " + scoreFilePath);
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(writer);
        }

    }

    public int getSavedScore() {

        FileReader reader = null;
        int savedScore;

        try {
            reader = new FileReader(scoreFilePath);
            savedScore = Integer.parseInt((String) IOUtils.readLines(reader).get(0));
        } catch (FileNotFoundException e) {
            savedScore = 0;
        } catch (IOException e) {
            e.printStackTrace();
            savedScore = 0;
        } finally {
            IOUtils.closeQuietly(reader);
        }

        return savedScore;

    }

}
