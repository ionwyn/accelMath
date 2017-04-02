package ca.sfu.g15.accelmath.database;

import android.content.Context;

import com.bluelinelabs.logansquare.LoganSquare;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import ca.sfu.g15.accelmath.R;

public class DatabaseHandler {

    private static final String DB_PREFIX = "db";
    private static final String DEFAULT_DATABASE_FILE_NAME = DB_PREFIX + "AccelMathDefault.json";

    private static DatabaseHandler sDatabaseHandler;

    private Database mDatabase;
    private String mCurrentFile = DEFAULT_DATABASE_FILE_NAME;

    public static DatabaseHandler get(Context context) {
        if (sDatabaseHandler == null) {
            sDatabaseHandler = new DatabaseHandler(context);
        }
        return sDatabaseHandler;
    }

    public void setDatabase(Context context, String fileName) {
        Database database = getDatabaseFromFile(context, fileName);
        if (database != null) {
            mCurrentFile = fileName;
            mDatabase = database;
        }
    }

    public String getCurrentFile() {
        return mCurrentFile;
    }

    private DatabaseHandler(Context context) {
        this(context, DEFAULT_DATABASE_FILE_NAME);
    }

    private DatabaseHandler(Context context, String fileName) {
        //Copy the default database everytime
        InputStream defaultFileStream = context.getResources().openRawResource(R.raw.question_bank);
        copyDatabase(context, defaultFileStream, DEFAULT_DATABASE_FILE_NAME);

        //Read the database from a file
        mDatabase = getDatabaseFromFile(context, fileName);
    }

    public List<Database.Unit> getUnits() {
        return mDatabase.units;
    }

    private Database getDatabaseFromFile(Context context, String filename) {
        File file = new File(context.getFilesDir(), DB_PREFIX + filename);
        if (file.exists()) {
            try {
                FileInputStream inputStream = context.openFileInput(file.getName());
                Database database = LoganSquare.parse(inputStream, Database.class);
                inputStream.close();
                return database;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private boolean copyDatabase(Context context, InputStream input, String newFileName) {
        File file = new File(context.getFilesDir(), DB_PREFIX + newFileName);
        try {
            Database database = LoganSquare.parse(input, Database.class);
            OutputStream outputStream = context.openFileOutput(file.getName(), Context.MODE_PRIVATE);
            LoganSquare.serialize(database, outputStream);
            return false;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }
}
