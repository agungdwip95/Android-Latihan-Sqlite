package id.gani.latihansqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.logging.Logger;

/**
 * Created by MochGani on 10/24/17.
 */

public class DatabaseSQLiteHelper extends SQLiteOpenHelper {
    private static final String TAG = DatabaseSQLiteHelper.class.getSimpleName();

    private static final int DATABASE_VERSION = 23;
    private static final String WORD_LIST_TABLE = "word_entries";
    private static final String USER_LIST_TABLE = "user_list";
    private static final String DATABASE_NAME = "wordlist";

    public static final String KEY_ID = "_id";

    // Column names word_entries
    public static final String KEY_WORD = "word";
    public static final String KEY_KET = "keterangan";

    // Column names user_list
    public static final String KEY_USERNAME = "username";
    public static final String KEY_PASSWORD = "password";

    // Build the SQL query that creates the table.
    private static final String WORD_LIST_TABLE_CREATE =
            "CREATE TABLE " + WORD_LIST_TABLE + " (" +
                    KEY_ID + " INTEGER PRIMARY KEY, " +
                    KEY_WORD + " TEXT, " +
                    KEY_KET + " TEXT );";

    // Build the SQL query that creates the table.
    private static final String USER_LIST_TABLE_CREATE =
            "CREATE TABLE " + USER_LIST_TABLE + " (" +
                    KEY_ID + " INTEGER PRIMARY KEY, " +
                    KEY_USERNAME + " TEXT, " +
                    KEY_PASSWORD + " TEXT );";

    private SQLiteDatabase mWritableDB;
    private SQLiteDatabase mReadableDB;

    public DatabaseSQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.d(TAG, "Database Terbuat");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(WORD_LIST_TABLE_CREATE);
        Log.d(TAG, "Tabel Word Terbuat");

        db.execSQL(USER_LIST_TABLE_CREATE);
        Log.d(TAG, "Tabel User Terbuat");

        fillDatabaseWithData(db);
        fillUser(db);
    }

    public void fillDatabaseWithData(SQLiteDatabase db) {

        String[] words = {"Android", "Adapter", "ListView", "AsyncTask",
                "Android Studio","SQLiteDatabase", "SQLOpenHelper", "Data model",
                "ViewHolder","Android Performance", "OnClickListener"};

        // Create a container for the data.
        ContentValues values = new ContentValues();

        for (int i=0; i < words.length;i++) {
            // Put column/value pairs into the container. put() overwrites existing values.
            values.put(KEY_WORD, words[i]);
            values.put(KEY_KET, "Keterangan " + words[i]);
            db.insert(WORD_LIST_TABLE, null, values);

            Log.d(TAG, "Data Masuk = " + words[i]);
        }
    }

    public void fillUser(SQLiteDatabase db) {

        // Create a container for the data.
        ContentValues values = new ContentValues();

        values.put(KEY_USERNAME, "admin");
        values.put(KEY_PASSWORD, "admin123");
        db.insert(USER_LIST_TABLE, null, values);

        Log.d(TAG, "Data Admin Masuk");
    }

    public String[] getDataAll(int position){
        String query = "SELECT * FROM " + WORD_LIST_TABLE + " ORDER BY _id ASC";

        Cursor cursor = null;
        String[] daftar = new String[0];

        try {
            if (mReadableDB == null) {mReadableDB = getReadableDatabase();}
            cursor = mReadableDB.rawQuery(query, null);
            daftar = new String[cursor.getCount()];
            cursor.moveToFirst();
            for (int i=0; i < cursor.getCount(); i++){
                cursor.moveToPosition(i);
                daftar[i] = cursor.getString(position).toString();
            }
        } catch (Exception e) {
            Log.d(TAG, "QUERY EXCEPTION! " + e.getMessage());
        } finally {
            // Must close cursor and db now that we are done with it.
            cursor.close();
            return daftar;
        }
    }

    public Cursor getDataAllCursor(){
        String query = "SELECT * FROM " + WORD_LIST_TABLE + " ORDER BY _id ASC";
        if (mReadableDB == null) {mReadableDB = getReadableDatabase();}
        Cursor c = mReadableDB.rawQuery(query, null);
        return c;
    }

    public String getDataWhere(int id, String namaField){
        String query = "SELECT  * FROM " + WORD_LIST_TABLE +
                       " WHERE " + KEY_ID + "=" + id;

        Cursor cursor = null;
        String word = null;

        try {
            if (mReadableDB == null) {mReadableDB = getReadableDatabase();}
            cursor = mReadableDB.rawQuery(query, null);
            cursor.moveToFirst();
            word = cursor.getString(cursor.getColumnIndex(namaField));
        } catch (Exception e) {
            Log.d(TAG, "QUERY EXCEPTION! " + e.getMessage());
        } finally {
            // Must close cursor and db now that we are done with it.
            cursor.close();
            return word;
        }
    }

    public Boolean cekLogin(String username,String password){
        String query = "SELECT  * FROM " + USER_LIST_TABLE +
                " WHERE " + KEY_USERNAME + "='" + username + "' AND " + KEY_PASSWORD + "='" + password + "'";

        Cursor cursor = null;
        Boolean cek = false;

        try {
            if (mReadableDB == null) {mReadableDB = getReadableDatabase();}
            cursor = mReadableDB.rawQuery(query, null);
            if(cursor.getCount()>0){
                cek = true;
                cursor.close();
            }
        } catch (Exception e) {
            Log.d(TAG, "QUERY EXCEPTION! " + e.getMessage());
        } finally {
            // Must close cursor and db now that we are done with it.
            return cek;
        }
    }

    public long insert(String word, String keterangan) {
        long newId = 0;
        ContentValues values = new ContentValues();
        values.put(KEY_WORD, word);
        values.put(KEY_KET, keterangan);
        try {
            if (mWritableDB == null) {mWritableDB = getWritableDatabase();}
            newId = mWritableDB.insert(WORD_LIST_TABLE, null, values);
        } catch (Exception e) {
            Log.d(TAG, "INSERT EXCEPTION! " + e.getMessage());
        }
        return newId;
    }

    public int update(int id, String word, String keterangan) {
        int mNumberOfRowsUpdated = -1;
        try {
            if (mWritableDB == null) {mWritableDB = getWritableDatabase();}
            ContentValues values = new ContentValues();
            values.put(KEY_WORD, word);
            values.put(KEY_KET, keterangan);

            mNumberOfRowsUpdated = mWritableDB.update(WORD_LIST_TABLE, //table to change
                    values, // new values to insert
                    KEY_ID + " = ?", // selection criteria for row (in this case, the _id column)
                    new String[]{String.valueOf(id)}); //selection args; the actual value of the id

        } catch (Exception e) {
            Log.d (TAG, "UPDATE EXCEPTION! " + e.getMessage());
        }
        return mNumberOfRowsUpdated;
    }

    public int delete(int id) {
        int deleted = 0;
        try {
            if (mWritableDB == null) {mWritableDB = getWritableDatabase();}
            deleted = mWritableDB.delete(WORD_LIST_TABLE, //table name
                    KEY_ID + " = ? ", new String[]{String.valueOf(id)});
        } catch (Exception e) {
            Log.d (TAG, "DELETE EXCEPTION! " + e.getMessage());        }
        return deleted;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(DatabaseSQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + WORD_LIST_TABLE);
        onCreate(db);
    }
}
