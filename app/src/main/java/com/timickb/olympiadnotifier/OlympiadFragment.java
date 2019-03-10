package com.timickb.olympiadnotifier;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;


public class OlympiadFragment extends Fragment {

    private TextView olTitle;
    private TextView subjectsInfo;
    private TextView classesInfo;
    private TextView dateInfo;
    private TextView orgsInfo;
    private TextView olympiadSite;
    private boolean isOrganizers;
    private View view;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_olympiad, container, false);
        olTitle = view.findViewById(R.id.olTitle);
        subjectsInfo = view.findViewById(R.id.subjectsInfo);
        classesInfo = view.findViewById(R.id.classesInfo);
        dateInfo = view.findViewById(R.id.dateInfo);
        orgsInfo = view.findViewById(R.id.orgsList);
        olympiadSite = view.findViewById(R.id.olympiadLink);

        String title = getArguments().getString("title");
        String classes = new String();
        try {
            classes = Tools.getStringFromClasses(getArguments().getStringArrayList("classes"));
        } catch(Exception e) {
            classes = Tools.getStringFromIntClasses(getArguments().getIntegerArrayList("classes"));
        }

        String subjects = Tools.getStringFromSubjects(getArguments().getStringArrayList("subjects"));
        String date = Tools.getStringFromMonths(getArguments().getString("date_start"), getArguments().getString("date_end"));
        String organizers = new String();
        String link = getArguments().getString("link");
        try {
            if(getArguments().getStringArrayList("organizers").size() == 0) {
                organizers = getString(R.string.unknown);
            } else {
                organizers = Tools.getStringFromOrganizers(getArguments().getStringArrayList("organizers"));
            }
            isOrganizers = true;
        } catch(Exception e) {
            isOrganizers = false;
        }


        olTitle.setText(title);
        subjectsInfo.setText(subjects);
        classesInfo.setText(classes);
        dateInfo.setText(date);
        if(isOrganizers) orgsInfo.setText(organizers);
        else orgsInfo.setText(R.string.unknown);
        if(link.equals("undefined")) olympiadSite.setText(R.string.unknown);
        else olympiadSite.setText(link);

        return view;
    }
}
