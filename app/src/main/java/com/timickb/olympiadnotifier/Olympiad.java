package com.timickb.olympiadnotifier;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Olympiad implements Parcelable {
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("subjects")
    @Expose
    private ArrayList<String> subjects;
    @SerializedName("classes")
    @Expose
    private int[] classes;
    @SerializedName("date_start")
    @Expose
    private String dateStart;
    @SerializedName("date_end")
    @Expose
    private String dateEnd;
    @SerializedName("link")
    @Expose
    private String link;
    @SerializedName("stage")
    @Expose
    private String stage;
    @SerializedName("id")
    @Expose
    private int id;

    public Olympiad(int id, String title, int[] classes, ArrayList<String> subjects, String dateStart, String dateEnd, String link) {
        this.id = id;
        this.title = title;
        this.classes = classes;
        this.subjects = subjects;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.link = link;
    }

    protected Olympiad(Parcel in) {
        title = in.readString();
        subjects = in.createStringArrayList();
        classes = in.createIntArray();
        dateStart = in.readString();
        dateEnd = in.readString();
        link = in.readString();
        id = in.readInt();
    }

    public static final Creator<Olympiad> CREATOR = new Creator<Olympiad>() {
        @Override
        public Olympiad createFromParcel(Parcel in) {
            return new Olympiad(in);
        }

        @Override
        public Olympiad[] newArray(int size) {
            return new Olympiad[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeStringList(subjects);
        dest.writeIntArray(classes);
        dest.writeString(dateStart);
        dest.writeString(dateEnd);
        dest.writeString(link);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<String> getSubjects() {
        return subjects;
    }

    public void setSubjects(ArrayList<String> subjects) {
        this.subjects = subjects;
    }

    public int[] getClasses() {
        return classes;
    }

    public void setClasses(int[] classes) {
        this.classes = classes;
    }

    public String getDateStart() {
        return dateStart;
    }

    public void setDateStart(String dateStart) {
        this.dateStart = dateStart;
    }

    public String getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(String dateEnd) {
        this.dateEnd = dateEnd;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }
}
