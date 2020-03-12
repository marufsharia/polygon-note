package me.marufsharia.simplenote.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import me.marufsharia.simplenote.model.Note;

public class NoteDBHelper {
    Context context;
    
    //table name
    public static final String TABLE_NAME="tbl_note";
    //column name
    public static final String COLUMN_ID="id";
    public static final String COLUMN_TITLE="title";
    public static final String COLUMN_DESCRIPTION="description";
    public static final String COLUMN_CATEGORY_ID="category_id";
    public static final String COLUMN_PRIORITY="priority";
    public static final String COLUMN_CREATED_AT="created_at";
    public static final String COLUMN_UPDATED_AT="updated_at";
    
    //create table sql query
    public static  final  String noteTableCreate=
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_TITLE + " TEXT,"
                    + COLUMN_DESCRIPTION + " TEXT,"
                    + COLUMN_CATEGORY_ID + " INTEGER DEFAULT 0,"
                    + COLUMN_PRIORITY + " INTEGER DEFAULT 0,"
                    + COLUMN_CREATED_AT + " DATETIME DEFAULT CURRENT_TIMESTAMP,"
                    + COLUMN_UPDATED_AT + " DATETIME DEFAULT CURRENT_TIMESTAMP"
                    + ")";
    
    
    public NoteDBHelper(Context context) {
        this.context=context;
    }
    
    //insert new note
    public long createNote(Note note) {
        // get writable database as we want to write data
        SQLiteDatabase db = new SQLiteDatabaseHelper(context).getWritableDatabase();
        
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, note.getTitle());
        values.put(COLUMN_DESCRIPTION, note.getDescription());
        
        // insert row
        long id = db.insert(TABLE_NAME, null, values);
        
        // close db connection
        db.close();
        
        // return newly inserted row id
        return id;
    }
    
    public List<Note> getAllNotes() {
        List<Note> notes = new ArrayList<>();
        
        // Select All Query
        String selectQuery = "SELECT  * FROM " + NoteDBHelper.TABLE_NAME + " ORDER BY " +
                NoteDBHelper.COLUMN_CREATED_AT + " DESC";
        
        SQLiteDatabase db = new SQLiteDatabaseHelper(context).getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Note note = new Note();
                note.setId(cursor.getInt(cursor.getColumnIndex(NoteDBHelper.COLUMN_ID)));
                note.setTitle(cursor.getString(cursor.getColumnIndex(NoteDBHelper.COLUMN_TITLE)));
                note.setCategory(cursor.getInt(cursor.getColumnIndex(NoteDBHelper.COLUMN_CATEGORY_ID)));
                note.setCreated_at(cursor.getString(cursor.getColumnIndex(NoteDBHelper.COLUMN_CREATED_AT)));
                
                notes.add(note);
            } while (cursor.moveToNext());
        }
        
        // close db connection
        db.close();
        
        // return notes list
        return notes;
        
    }

}
