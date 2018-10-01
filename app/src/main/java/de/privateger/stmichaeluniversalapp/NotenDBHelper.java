package de.privateger.stmichaeluniversalapp;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

import com.google.errorprone.annotations.Var;

public class NotenDBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Noten.db";
    public static final String NOTEN_TABLE_NAME = "noten";
    public static final String NOTEN_COLUMN_FAECHER = "fach";
    public static final String NOTEN_COLUMN_NOTE = "note";
    public static final String NOTEN_COLUMN_NAME = "datum";

    private HashMap hp;

    public NotenDBHelper(Context context) {
        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table noten " +
                        "(fach varchar, note integer, datum text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS noten");
        onCreate(db);
    }

    public boolean insertContact (String fach, int note) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("fach", fach);
        contentValues.put("note", note);
        contentValues.put("datum", new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date()));
        db.insert("noten", null, contentValues);
        return true;
    }

    public Cursor getData(String fach) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from noten where fach="+fach+"", null );
        return res;
    }

    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, NOTEN_TABLE_NAME);
        return numRows;
    }

    public boolean updateNote (String fach, int note, String datum) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("fach", fach);
        contentValues.put("note", note);
        contentValues.put("datum", new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date()));
        db.update("contacts", contentValues, "datum = ? ", new String[] { datum } );
        return true;
    }

    public Integer deleteNote (String datum) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("noten",
                "daten = ? ",
                new String[] { datum });
    }

    public ArrayList<String> getAllNoten() {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from noten", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(res.getString(res.getColumnIndex(NOTEN_COLUMN_NAME)));
            res.moveToNext();
        }
        return array_list;
    }
}