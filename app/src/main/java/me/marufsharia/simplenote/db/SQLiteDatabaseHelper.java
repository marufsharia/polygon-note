package me.marufsharia.simplenote.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteDatabaseHelper extends SQLiteOpenHelper {
    
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "note_db";
    
    public SQLiteDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //create new note table;
        sqLiteDatabase.execSQL(NoteDBHelper.noteTableCreate);
        
        
    }
    
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        
        //drop table if exist
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + NoteDBHelper.TABLE_NAME);
        //create new table
        onCreate(sqLiteDatabase);
    }
}
