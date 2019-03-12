package com.timickb.olympiadnotifier;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class OlympiadFragment extends Fragment implements View.OnClickListener {

    private TextView olTitle;
    private TextView subjectsInfo;
    private TextView classesInfo;
    private TextView dateInfo;
    private TextView olympiadSite;
    private Olympiad olympiad;
    private Button favBtn;
    private View view;
    private SharedPreferences settings;
    private boolean isFav;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_olympiad, container, false);

        settings = getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = settings.edit();
        editor.putString("fav_list", "");

        olTitle = view.findViewById(R.id.olTitle);
        subjectsInfo = view.findViewById(R.id.subjectsInfo);
        classesInfo = view.findViewById(R.id.classesInfo);
        dateInfo = view.findViewById(R.id.dateInfo);
        olympiadSite = view.findViewById(R.id.olympiadLink);
        favBtn = view.findViewById(R.id.addToFavBtn);

        olympiad = getArguments().getParcelable("olympiad");

        String title = olympiad.getTitle();
        String classes = Tools.getStringFromClasses(olympiad.getClasses());
        String subjects = Tools.getStringFromSubjects(olympiad.getSubjects());
        String date = Tools.getStringFromMonths(olympiad.getDateStart(), olympiad.getDateEnd());
        String link = olympiad.getLink();

        olTitle.setText(title);
        subjectsInfo.setText(subjects);
        classesInfo.setText(classes);
        dateInfo.setText(date);
        if(link.equals("undefined")) olympiadSite.setText(R.string.unknown);
        else olympiadSite.setText(link);

        Gson gson = new Gson();
        String favIDsRaw = settings.getString("fav_list", "");
        Type type = new TypeToken<ArrayList<Integer>>() {}.getType();
        ArrayList<Integer> favIDs = gson.fromJson(favIDsRaw, type);
        isFav = Tools.intListContains(favIDs, olympiad.getId());

        // button
        favBtn.setOnClickListener(this);
        if(isFav) {
            favBtn.setText(R.string.remove_from_fav);
        }
        return view;
    }

    @Override
    public void onClick(View v) {
        if(!isFav) {
            Gson gson = new Gson();
            String json = gson.toJson(olympiad);

            SharedPreferences.Editor editor = settings.edit();
            editor.putString("fav"+Integer.toString(olympiad.getId()), json);

            String favIDsRaw = settings.getString("fav_list", "");
            Type type = new TypeToken<ArrayList<Integer>>() {}.getType();
            ArrayList<Integer> favIDs = gson.fromJson(favIDsRaw, type);
            if(favIDs == null) favIDs = new ArrayList<Integer>();
            favIDs.add(olympiad.getId());
            String newFavList = gson.toJson(favIDs);
            editor.putString("fav_list", newFavList);
            editor.commit();

            Toast.makeText(getContext(), R.string.added_to_fav, Toast.LENGTH_SHORT).show();
            favBtn.setText(R.string.remove_from_fav);
        } else {
            Gson gson = new Gson();
            SharedPreferences.Editor editor = settings.edit();
            editor.remove("fav"+Integer.toString(olympiad.getId()));
            String favIDsRaw = settings.getString("fav_list", null);
            Type type = new TypeToken<ArrayList<Integer>>() {}.getType();
            ArrayList<Integer> favIDs = gson.fromJson(favIDsRaw, type);
            favIDs.remove(new Integer(olympiad.getId()));
            String newFavList = gson.toJson(favIDs);
            editor.putString("fav_list", newFavList);
            editor.commit();

            Toast.makeText(getContext(), R.string.removed_from_fav, Toast.LENGTH_SHORT).show();
            favBtn.setText(R.string.add_to_fav);
        }
        isFav = !isFav;
    }
}
