package com.example.alertdialog_date_picker_final_project;


public class RecycleView_Elements {
    private int _id;
    private String ImagePath;
    private String Date;
    private String Time;
    private String Event;


    public RecycleView_Elements(int _id, String imagepath, String date, String time, String event) {
        this._id = _id;
        this.ImagePath = imagepath;
        this.Date = date;
        this.Time = time;
        this.Event = event;
    }
    public int getID() {return _id;}

    public void setID(int _id) {this._id = _id;}

    public String getImagePath() {return ImagePath;}

    public void setImagePath(String imagepath) {this.ImagePath = imagepath;}

    public String getDate() {return Date;}

    public void setDate(String date) {this.Date = date;}

    public String getTime() {return Time;}

    public void setTime(String time) {this.Time = time;}

    public String getEvent() {return Event;}

    public void setEvent(String event) {this.Event = event;}
}