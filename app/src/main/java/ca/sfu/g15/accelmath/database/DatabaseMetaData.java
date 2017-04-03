package ca.sfu.g15.accelmath.database;

public class DatabaseMetaData {

    private String mDatabaseName;
    private String mFileName;
    private String mScoresFileName;

    public DatabaseMetaData(String databaseName, String fileName, String scoresFileName) {
        mDatabaseName = databaseName;
        mFileName = fileName;
        mScoresFileName = scoresFileName;
    }

    public String getDatabaseName() {
        return mDatabaseName;
    }

    public String getFileName() {
        return mFileName;
    }

    public String getScoresFileName() {
        return mScoresFileName;
    }
}
