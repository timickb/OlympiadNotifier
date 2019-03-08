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
    private ArrayList<String> classes;

    @SerializedName("date_start")
    @Expose
    private String dateStart;

    @SerializedName("date_end")
    @Expose
    private String dateEnd;

    @SerializedName("link")
    @Expose
    private String link;

    @SerializedName("orgs")
    @Expose
    private ArrayList<String> organizers;

    public String getTitle() {
        return title;
    }

    public ArrayList<String> getSubjects() {
        return subjects;
    }

    public ArrayList<String> getClasses() {
        return classes;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSubjects(ArrayList<String> subjects) {
        this.subjects = subjects;
    }

    public void setClasses(ArrayList<String> classes) {
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

    public ArrayList<String> getOrganizers() {
        return organizers;
    }

    public void setOrganizers(ArrayList<String> organizers) {
        this.organizers = organizers;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
