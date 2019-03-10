package com.timickb.olympiadnotifier;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Olympiad {
    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("subjects")
    @Expose
    private ArrayList<String> subjects;

    @SerializedName("classes")
    @Expose
    private ArrayList<Integer> classes;

    @SerializedName("date_start")
    @Expose
    private String dateStart;

    @SerializedName("date_end")
    @Expose
    private String dateEnd;

    @SerializedName("link")
    @Expose
    private String link;

    @SerializedName("id")
    @Expose
    private int id;

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

    public ArrayList<Integer> getClasses() {
        return classes;
    }

    public void setClasses(ArrayList<Integer> classes) {
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
}
