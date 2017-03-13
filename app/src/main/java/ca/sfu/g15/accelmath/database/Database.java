package ca.sfu.g15.accelmath.database;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

import java.util.List;

@JsonObject
public class Database {

    @JsonField
    public String edition;

    @JsonField
    public int version;

    @JsonField
    public List<Unit> units;

    @JsonObject
    public static class Unit {

        @JsonField
        public String unitName;

        @JsonField
        public List<Chapter> chapters;

        @JsonObject
        public static class Chapter {

            @JsonField
            public String topic;

            @JsonField
            public String lesson;

            @JsonField
            public List<Question> questions;

            @JsonObject
            public static class Question {

                @JsonField
                public String question;

                @JsonField
                public String answer;

            }
        }
    }
}
