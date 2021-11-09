package com.example.alertdialog_date_picker_final_project;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AndroidSQLite extends SQLiteOpenHelper {
    private static Cursor cursor;
    private final static String Dbname = "medicine_notes.db";
    private final static String Tablename = "notes";
    private final static int DBVersion = 1;
    private static String Createtable = "create table if not exists notes (ID integer primary key AUTOINCREMENT, Date text not null, Time text not null, ImagePath text not null,Event text not null)";

    public AndroidSQLite(Context context) {
        super(context, Dbname, null, DBVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(Createtable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        final String SQL = "DROP TABLE " + Tablename;
        sqLiteDatabase.execSQL(SQL);
    }
}
