package ca.sfu.group15.accelmath.database;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

import java.util.List;

@JsonObject
public class Scores {

    @JsonField
    public List<Score> scores;

    @JsonObject
    public static class Score {
        @JsonField
        public int unitIndex;

        @JsonField
        public int chapterIndex;

        @JsonField
        public float rating;
    }
}
