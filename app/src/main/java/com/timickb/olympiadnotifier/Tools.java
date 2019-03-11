package com.timickb.olympiadnotifier;

import java.util.List;

public class Tools {

    public static String getStringFromClasses(int [] classesRaw) {
        String result = new String();
        if(classesRaw.length == 1) result = Integer.toString(classesRaw[0]) + " класс";
        else result = Integer.toString(classesRaw[0]) + "-" + Integer.toString(classesRaw[classesRaw.length-1]) + " класс";

        return result;
    }

    public static String getStringFromSubjects(List<String> subjectsRaw) {
        String subjects = new String();
        for(String s : subjectsRaw) {
            subjects += s;
            if (s != subjectsRaw.get(subjectsRaw.size()-1)) subjects += ", ";
        }
        return subjects;
    }

    public static String getStringFromMonths(String start, String end) {
        return start + " - " + end;
    }

    public static String getStringFromOrganizers(List<String> orgs) {
        String result = new String();

        for(String org: orgs) {
            result += org;
            if(orgs.get(orgs.size()-1) != org) result += ", ";
        }
        return result;
    }
}
