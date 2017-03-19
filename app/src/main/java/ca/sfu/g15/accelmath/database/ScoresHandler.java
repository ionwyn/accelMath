package ca.sfu.g15.accelmath.database;

import android.content.Context;

import java.io.File;

public class ScoresHandler {

    private static String FILE_NAME = "scores.json";

    private static ScoresHandler sScoresHandler;

    private Scores mScores;

    private ScoresHandler (Context context) {

    }

    private Scores getScoresFromFiles (Context context) {
        File file = new File(context.getFilesDir(), FILE_NAME);
        //TODO read scores from json file
        return null;
    }
}
