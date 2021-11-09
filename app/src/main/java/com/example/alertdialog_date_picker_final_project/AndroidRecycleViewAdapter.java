package com.example.alertdialog_date_picker_final_project;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.Calendar;

public class AndroidRecycleViewAdapter extends RecyclerView.Adapter<AndroidRecycleViewAdapter.ViewHolder>{
    private static Context mContext;
    private static SQLiteDatabase db;
    private static AndroidSQLite dbhelpr;
    private static ArrayList<RecycleView_Elements> mDataSet = new ArrayList<RecycleView_Elements>();
    private static RecyclerView recyclerView;
    private static String FIELD01_NAME = "ID";
    private static String TABLENAME = "notes";
    private android.database.Cursor cursor;

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView date_time;
        private TextView event;
        private ImageView show_image;

        public ViewHolder(View itemView) {
            super(itemView);
            //  設計RecyclerView中點選RecyclerView.ViewHolder的項目，以Toast顯示訊息：「你點選的是第 xx 部電影」
            // TO DO
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

//                    getMayupdateData(getAdapterPosition());
                    final String[] Date = new String[1];
                    final String[] Time = new String[1];
                    final String[] Event = new String[1];
//                String VoicePath;

                    LayoutInflater inflater = LayoutInflater.from(mContext);
                    final View v = inflater.inflate(R.layout.add_something, null);
                    final AlertDialog alertD = new AlertDialog.Builder(mContext).
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
                    alertD.show();

                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    removeData(getAdapterPosition());
                    return false;
                }
            });

            date_time =  itemView.findViewById(R.id.pick_date_time);
            show_image = itemView.findViewById(R.id.show_image);
            event = itemView.findViewById(R.id.event);
        }


        private void getMayupdateData(int adapterPosition){
            int a = mDataSet.get(adapterPosition).getID();
            dbhelpr = new AndroidSQLite(mContext);
            db = dbhelpr.getWritableDatabase();
            String[] columns = {"ID", "ImagePath", "Time", "Date"};
            String [] selectArg = {"ID="+a};
            Cursor cursor = db.query("notes", columns, null, selectArg, null, null, null);
            Log.d("getMayupdateData", String.valueOf(cursor.getString(0)));
        }
        private void removeData(int adapterPosition) {
            int a = mDataSet.get(adapterPosition).getID();
            mDataSet.remove(adapterPosition);
            //删除动画
            notifyItemRemoved(adapterPosition);
            notifyDataSetChanged();

            dbhelpr = new AndroidSQLite(mContext);
            String where = FIELD01_NAME +"=?";
            db = dbhelpr.getWritableDatabase();
            String[] whereValue = {Integer.toString(a)};
            db.delete(TABLENAME,where,whereValue);
            Log.d("DBdelete_successfully",""+adapterPosition);
        }

        public TextView getDate_Time() { return date_time; }
        public ImageView getShow_image() { return show_image; }
        public TextView getEvent(){return event;}
    }

    public AndroidRecycleViewAdapter(Context context, ArrayList<RecycleView_Elements> mDataSet) {
        this.mContext = context;
        this.mDataSet = mDataSet;
    }


    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.redesign_recycleview_elements, viewGroup, false);
        return new ViewHolder(v);
    }


    public void onBindViewHolder(final ViewHolder viewHolder, int position) {
        // 指派ViewHolder物件，重複使用，動態載入電影名稱(TextView)及圖片Resource ID(ImageView)
        // TO DO
        viewHolder.itemView.setTag(""+position);
        final RecycleView_Elements mv = mDataSet.get(position);
        viewHolder.getDate_Time().setText(mv.getDate()+" "+mv.getTime());
        viewHolder.getEvent().setText(mv.getEvent());
        final Bitmap myBitmap = BitmapFactory.decodeFile(mv.getImagePath());
        viewHolder.getShow_image().setImageBitmap(myBitmap);

        viewHolder.getShow_image().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent TransferToFullScreen = new Intent(view.getContext(),ImageFullScreen.class);
                Bundle bundle = new Bundle();
                bundle.putString("ImagePath",""+mv.getImagePath());
                TransferToFullScreen.putExtras(bundle);
                view.getContext().startActivity(TransferToFullScreen);
            }
        });
    }

    public int getItemCount() {
        return mDataSet.size();
    }
}
