package ca.sfu.g15.accelmath.database;

import android.content.Context;

import com.bluelinelabs.logansquare.LoganSquare;

import java.io.InputStream;
import java.util.List;

import ca.sfu.g15.accelmath.R;

public class DatabaseHandler {

    private static DatabaseHandler sDatabaseHandler;

    private Database mDatabase;

    public static DatabaseHandler get(Context context) {
        if (sDatabaseHandler == null) {
            sDatabaseHandler = new DatabaseHandler(context);
        }
        return sDatabaseHandler;
    }

    private DatabaseHandler(Context context) {
        try {
            InputStream inputStream = context.getResources().openRawResource(R.raw.question_bank);
            mDatabase = LoganSquare.parse(inputStream, Database.class);
        } catch (Exception ex) {

        }
    }

    public List<Database.Unit> getUnits() {
        return mDatabase.units;
    }
}
