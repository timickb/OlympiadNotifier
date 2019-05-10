package com.timickb.olympiadnotifier;

import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.List;

public class Tools {

    public static boolean isTomorrow(String date, int currentDay, int currentMonth) {
        String[] parsed = date.split("\\.");
        System.out.println(currentDay + " " + currentMonth);
        if(currentMonth == Integer.parseInt(parsed[1]) && currentDay == Integer.parseInt(parsed[0])-1) return true;
        return false;
    }

    public static boolean isToday(String date, int currentDay, int currentMonth) {
        String[] parsed = date.split("\\.");
        System.out.println(currentDay + " " + currentMonth);
        if(currentMonth == Integer.parseInt(parsed[1]) && currentDay == Integer.parseInt(parsed[0])) return true;
        return false;
    }

    public static boolean isExpired(String date, int currentDay, int currentMonth, int currentYear) {
        String[] parsed = date.split("\\.");
        int endYear = Integer.parseInt(parsed[2]);
        int endMonth = Integer.parseInt(parsed[1]);
        int endDay = Integer.parseInt(parsed[0]);
        if(currentYear > endYear) return true;
        if(currentMonth > endMonth && currentYear == endYear) return true;
        if(currentMonth == endMonth && currentYear == endYear && currentDay > endDay) return true;
        return false;
    }

    public static boolean intListContains(ArrayList<Integer> list, int n) {
        if(list == null) return false;
        boolean result = false;
        for(int element : list) {
            if(element == n) {
                result = true;
                break;
            }
        }
        return result;
    }

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
}
