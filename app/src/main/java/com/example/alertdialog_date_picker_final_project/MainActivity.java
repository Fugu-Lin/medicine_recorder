package com.example.alertdialog_date_picker_final_project;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    FloatingActionButton fab;
    RecyclerView recyclerView;
    Calendar calendar;
    DatePickerDialog datePickerDialog;
    private AndroidSQLite dbhelp;
    private SQLiteDatabase db;
    protected ArrayList<RecycleView_Elements> mDataset = new ArrayList<RecycleView_Elements>();
    private android.database.Cursor cursor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbhelp = new AndroidSQLite(MainActivity.this);
        db = dbhelp.getWritableDatabase();

        initDataset();

        initRecycleView(savedInstanceState);
        //  動態載入自定義之 HippoCustomRecyclerViewAdapter 物件mAdapter，包含自訂UI text_row_item.xml。
        final AndroidRecycleViewAdapter adapter = new AndroidRecycleViewAdapter(MainActivity.this,mDataset);
        recyclerView.setAdapter(adapter);


        for(int i=0;i<mDataset.size();i++){
            Log.d("ID",""+mDataset.get(i).getID());
            Log.d("Date",""+mDataset.get(i).getDate());
            Log.d("Time",""+mDataset.get(i).getTime());
            Log.d("Event",""+mDataset.get(i).getEvent());
            Log.d("ImagePath",""+mDataset.get(i).getImagePath());

        }
        fab = findViewById(R.id.menu_item);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String[] Date = new String[1];
                final String[] Time = new String[1];
                final String[] Event = new String[1];
//                String VoicePath;

                LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
                final View v = inflater.inflate(R.layout.add_something, null);
                final AlertDialog alertD = new AlertDialog.Builder(MainActivity.this).
                        setNegativeButton("離開", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        }).setPositiveButton("確定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).create();

                alertD.setTitle("輸入事件");
                alertD.setView(v);

                ImageButton date_picker = (v.findViewById(R.id.button));
                ImageButton time_picker = (v.findViewById(R.id.button2));
                ImageButton take_photo = (v.findViewById(R.id.button3));

                final EditText show_event = (v.findViewById(R.id.editText1));
                final EditText show_date_picker = (v.findViewById(R.id.editText));
                final EditText show_time_picker = (v.findViewById(R.id.editText2));

                date_picker.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.d("AAAAAAAAAAA","OK");
                        final Calendar c = Calendar.getInstance();

                        DatePickerDialog dpd = new DatePickerDialog(view.getContext(),
                                new DatePickerDialog.OnDateSetListener() {
                                    @Override
                                    public void onDateSet(DatePicker view, int year,
                                                          int monthOfYear, int dayOfMonth) {
                                        Date[0] = year + "/" + (monthOfYear + 1) + "/" + dayOfMonth;
                                        show_date_picker.setText(Date[0]);

                                    }
                                }, c.get(Calendar.YEAR),c.get(Calendar.MONTH),c.get(Calendar.DATE));
                        dpd.show();
                    }
                });

                time_picker.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        TimePickerDialog timePickerDialog = new TimePickerDialog(view.getContext(), new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                                Time[0] = i + ":" + i1;
                                show_time_picker.setText(i + ":" + i1);
                            }
                        },0,0,true);

                        timePickerDialog.show();
                    }
                });

                take_photo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Event[0] = String.valueOf(show_event.getText());
                        Intent intent = new Intent(MainActivity.this,AndroidCameraApi.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("Date",Date[0]);
                        bundle.putString("Time",Time[0]);
                        bundle.putString("Event",Event[0]);
                        intent.putExtras(bundle);

                        startActivityForResult(intent,50);

                        alertD.cancel();
                    }
                });
                alertD.show();
            }
        });

    }

    private void initRecycleView(Bundle savedInstanceState) {
        recyclerView = findViewById(R.id.recycleview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager); // 必須設置 LayoutManager
    }

    private void initDataset() {
        mDataset = AndroidQuarySQLie.createItem(db);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == 50){
            final String[] result_image_path = new String[1];

            final String[] Date = new String[1];
            final String[] Time = new String[1];
            final String[] Event = new String[1];

            Date[0] = data.getExtras().getString("Date");
            Time[0] = data.getExtras().getString("Time");
            Event[0] = data.getExtras().getString("Event");

            result_image_path[0] = data.getExtras().getString("ImagePath");
            Log.d("File_Path",result_image_path[0]);
            LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
            final View v = inflater.inflate(R.layout.add_something, null);
            final AlertDialog alertD = new AlertDialog.Builder(MainActivity.this).
                    setNegativeButton("離開", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    }).setPositiveButton("確定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            ContentValues contentValues = new ContentValues();

                            Log.d("ContentValues",Date[0]);
                            Log.d("ContentValues",Time[0]);
                            Log.d("ContentValues",result_image_path[0]);
                            Log.d("ContentValues",Event[0]);
        //                    contentValues.put("ID",1);//此句也可不用
                            contentValues.put("Date",Date[0]);
                            contentValues.put("Time",Time[0]);
                            contentValues.put("ImagePath",result_image_path[0]);
                            contentValues.put("Event",Event[0]);
                            db.insert("notes",null,contentValues);

                            mDataset = AndroidQuarySQLie.createItem(db);
                            final AndroidRecycleViewAdapter adapter = new AndroidRecycleViewAdapter(MainActivity.this,mDataset);
                            recyclerView.setAdapter(adapter);
                        }
                    }).create();

            alertD.setTitle("輸入事件");
            alertD.setView(v);

            ImageButton date_picker = (v.findViewById(R.id.button));
            ImageButton time_picker = (v.findViewById(R.id.button2));
            ImageButton take_photo = (v.findViewById(R.id.button3));

            final EditText show_event = (v.findViewById(R.id.editText1));
            final EditText show_date_picker = (v.findViewById(R.id.editText));
            final EditText show_time_picker = (v.findViewById(R.id.editText2));

            show_event.setText(Event[0]);
            show_date_picker.setText(Date[0]);
            show_time_picker.setText(Time[0]);

            date_picker.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("AAAAAAAAAAA","OK");
                    final Calendar c = Calendar.getInstance();

                    DatePickerDialog dpd = new DatePickerDialog(view.getContext(),
                            new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker view, int year,
                                                      int monthOfYear, int dayOfMonth) {
                                    Date[0] = year + "/" + dayOfMonth + "/" + (monthOfYear + 1);
                                    show_date_picker.setText(Date[0]);
                                }
                            }, c.get(Calendar.YEAR),c.get(Calendar.MONTH),c.get(Calendar.DATE));
                    dpd.show();
                }
            });

            time_picker.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TimePickerDialog timePickerDialog = new TimePickerDialog(view.getContext(), new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int i, int i1) {
                            Time[0] = i + ":" + i1;
                            show_time_picker.setText(i + "-" + i1);
                        }
                    },0,0,true);
                    timePickerDialog.show();
                }
            });

            File imgFile = new File(result_image_path[0]);
            if(imgFile.exists()){
                ImageView show_image = (v.findViewById(R.id.imageView));
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                show_image.setImageBitmap(myBitmap);
            }
            else{
                Log.d("File_NOT_Exists","File_NOT_Exists!!!!!!!!!!!!!!!!!!!!!!!!!!");
            }
            take_photo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MainActivity.this,AndroidCameraApi.class);
                    Bundle bundle = new Bundle();

                    bundle.putString("Event",Event[0]);
                    bundle.putString("Date",Date[0]);
                    bundle.putString("Time",Time[0]);
                    intent.putExtras(bundle);
                    startActivityForResult(intent,50);
                    alertD.cancel();
                }
            });
            alertD.show();
        }
    }
}
