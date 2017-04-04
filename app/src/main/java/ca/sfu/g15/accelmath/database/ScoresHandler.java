package ca.sfu.g15.accelmath.database;

import android.content.Context;

import com.bluelinelabs.logansquare.LoganSquare;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class ScoresHandler {

    private static final String SCORES_PREFIX = "scores";

    private static ScoresHandler sScoresHandler;

    private Scores mScores;
    private String mCurrentFileName;

    public static ScoresHandler get(Context context) {
        if (sScoresHandler == null) {
            sScoresHandler = new ScoresHandler(context);
        }
        return sScoresHandler;
    }

    public void setRating(Context context, int unitIndex, int chapterIndex, float rating) {
        refreshCurrentScores(context);

        Scores.Score score = getScore(unitIndex, chapterIndex);
        if (score != null && score.rating < rating) {
            score.rating = rating;
        } else {
            Scores.Score newScore = new Scores.Score();
            newScore.chapterIndex = chapterIndex;
            newScore.unitIndex = unitIndex;
            newScore.rating = rating;
            mScores.scores.add(newScore);
        }
        updateScoresInFile(context);
    }

    public float getRating(Context context, int unitIndex, int chapterIndex) {
        refreshCurrentScores(context);

        Scores.Score score = getScore(unitIndex, chapterIndex);
        return score != null ? score.rating : -1f;
    }

    public Scores getScoresContents(Context context, String fileName) {
        return getScoresFromFile(context, fileName);
    }

    private void refreshCurrentScores(Context context) {
        String latestFile = getCurrentFileName(context);
        if (!latestFile.equals(mCurrentFileName)) {
            mScores = getScoresFromFile(context);
            mCurrentFileName = latestFile;
        }
    }

    private Scores.Score getScore(int unitIndex, int chapterIndex) {
        for (Scores.Score score : mScores.scores) {
            if (score.unitIndex == unitIndex && score.chapterIndex == chapterIndex) {
                return score;
            }
        }
        return null;
    }

    private ScoresHandler(Context context) {
        mScores = getScoresFromFile(context);
    }

    private Scores getScoresFromFile(Context context) {
        return getScoresFromFile(context, getCurrentFileName(context));
    }

    private Scores getScoresFromFile(Context context, String fileName) {
        File file = new File(context.getFilesDir(), fileName);
        if (file.exists()) {
            try {
                FileInputStream inputStream = context.openFileInput(file.getName());
                return LoganSquare.parse(inputStream, Scores.class);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Scores scores = new Scores();
        scores.scores = new ArrayList<>();
        return scores;
    }

    private void updateScoresInFile(Context context) {
        File file = new File(context.getFilesDir(), getCurrentFileName(context));
        FileOutputStream outputStream;
        try {
            outputStream = context.openFileOutput(file.getName(), Context.MODE_PRIVATE);
            LoganSquare.serialize(mScores, outputStream);
            outputStream.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private String getCurrentFileName(Context context) {
        String fileName = DatabaseHandler.get(context).getCurrentFile();
        //Replace database prefix with scores prefix
        fileName = fileName.substring(DatabaseHandler.DB_PREFIX.length());
        return SCORES_PREFIX + fileName;
    }
}
