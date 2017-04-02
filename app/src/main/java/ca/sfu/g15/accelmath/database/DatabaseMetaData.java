package ca.sfu.g15.accelmath.database;

public class DatabaseMetaData {

    private String mDatabaseName;
    private String mFileName;

    public DatabaseMetaData(String databaseName, String fileName) {
        mDatabaseName = databaseName;
        mFileName = fileName;
    }

    public String getDatabaseName() {
        return mDatabaseName;
    }

    public String getFileName() {
        return mFileName;
    }
}
