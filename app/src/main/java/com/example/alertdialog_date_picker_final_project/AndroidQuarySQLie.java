package com.example.alertdialog_date_picker_final_project;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

public class AndroidQuarySQLie {
    public static int[] ID = new int[1000];
    public static String[] Date = new String[1000];
    public static String[] Time = new String[1000];
    public static String[] Event = new String[1000];
    public static String[] ImagePath = new String[1000];

    public static int EventCount;

    public static void queryDatabase(SQLiteDatabase db){

//        String[] strings = new String[]{"ID","Date","Time","ImagePath","Event"};

        Cursor cursor = db.query ("notes",null,null,null,null,null,null);
        EventCount = cursor.getCount ();

        Log.d("EventCount",String.valueOf(EventCount));

        for (int i = 0; i < cursor.getCount (); i++) {
            if(cursor.moveToFirst ()) {
                cursor.move (i);
                ID[i] = cursor.getInt(cursor.getColumnIndex("ID"));
                Date[i] = cursor.getString (cursor.getColumnIndex ("Date"));
                Time[i] = cursor.getString (cursor.getColumnIndex ("Time"));
                Event[i] = cursor.getString (cursor.getColumnIndex ("Event"));
                ImagePath[i] = cursor.getString (cursor.getColumnIndex ("ImagePath"));
            }
        }
    }

    public static ArrayList<RecycleView_Elements> createItem(SQLiteDatabase db){
        ArrayList<RecycleView_Elements> items = new ArrayList<RecycleView_Elements> ();
        queryDatabase (db);
        for (int i=0;i < EventCount;i++){
            int id = ID[i];
            String date = Date[i];
            String time = Time[i];
            String event = Event[i];
            String imagepath = ImagePath[i];
            items.add(new RecycleView_Elements(id, imagepath ,date ,time ,event));
        }

        return items;
    }
}

