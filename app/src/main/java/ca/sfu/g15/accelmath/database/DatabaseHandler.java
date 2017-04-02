package ca.sfu.g15.accelmath.database;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.bluelinelabs.logansquare.LoganSquare;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import ca.sfu.g15.accelmath.R;

public class DatabaseHandler {

    public static final String DB_PREFIX = "db";

    private static final String DB_FILE_TYPE = "json";
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

    public boolean setDatabase(Context context, String fileName) {
        Database database = getDatabaseFromFile(context, fileName);
        if (database != null) {
            mCurrentFile = fileName;
            mDatabase = database;
            return true;
        }
        return false;
    }

    public String getCurrentFile() {
        return mCurrentFile;
    }

    public Database getDatabase() {
        return mDatabase;
    }

    public List<Database.Unit> getUnits() {
        return getDatabase().units;
    }

    public List<DatabaseMetaData> getDatabases(Context context) {
        List<DatabaseMetaData> databases = new ArrayList<>();

        //Get all local files
        File localStorage = context.getFilesDir();
        File[] allFiles = localStorage.listFiles();

        for (File file: allFiles) {
            String fileName = file.getName();
            Log.d("AccelMath", fileName);
            //If the file is a database file, add it to the list
            if (fileName.startsWith(DB_PREFIX) && fileName.endsWith(DB_FILE_TYPE)) {
                Database database = getDatabaseFromFile(context, fileName);
                if (database != null) {
                    databases.add(new DatabaseMetaData(database.edition, fileName));
                }
            }
        }

        return databases;
    }

    public boolean addDatabaseFromURL(Context context, String urlLocation) {
        try {
            //Get JSON data from URL by using a AsyncTask to avoid running on the main thread
            return new RetrieveInternetJSONTask(context).execute(urlLocation).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteDatabase(Context context, String fileName) {
        File file = new File(context.getFilesDir(), fileName);
        //Delete the database file if it exists
        if (file.exists()) {
            return file.delete();
        }
        return true;
    }

    public Database getDatabaseContents(Context context, String fileName) {
        return getDatabaseFromFile(context, fileName);
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


    private Database getDatabaseFromFile(Context context, String fileName) {
        File file = new File(context.getFilesDir(), fileName);
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
        File file = new File(context.getFilesDir(), newFileName);
        try {
            //Create database from stream
            Database database = LoganSquare.parse(input, Database.class);
            input.close();

            //Serialize database to JSON file
            OutputStream outputStream = context.openFileOutput(file.getName(), Context.MODE_PRIVATE);
            LoganSquare.serialize(database, outputStream);
            outputStream.close();
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private class RetrieveInternetJSONTask extends AsyncTask<String, Void, Boolean> {

        private Context mContext;

        public RetrieveInternetJSONTask(Context context) {
            mContext = context;
        }

        @Override
        protected Boolean doInBackground(String... params) {
            String urlLocation = params[0];
            try {
                URL url = new URL(urlLocation);
                InputStream inputStream = url.openStream();
                String newFileName  = DB_PREFIX + System.currentTimeMillis() + "." + DB_FILE_TYPE;
                return copyDatabase(mContext, inputStream, newFileName);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        }
    }
}
