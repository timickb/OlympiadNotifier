package com.timickb.olympiadnotifier;

import java.util.List;

public class Tools {

    public static String getStringFromClasses(List<String> classesRaw) {
        String classes = new String();
        if(classesRaw.size() == 1) classes = classesRaw.get(0) + " класс";
        else classes = classesRaw.get(0) + "-" + classesRaw.get(classesRaw.size()-1) + " класс";

        return classes;
    }

    public static String getStringFromSubjects(List<String> subjectsRaw) {
        String subjects = new String();
        for(String s : subjectsRaw) {
            subjects += s;
            if (s != subjectsRaw.get(subjectsRaw.size()-1)) subjects += ", ";
        }
        return subjects;
    }
}
