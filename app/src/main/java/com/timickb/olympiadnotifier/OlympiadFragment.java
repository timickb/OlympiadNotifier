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

        olTitle = view.findViewById(R.id.olTitle);
        subjectsInfo = view.findViewById(R.id.subjectsInfo);
        classesInfo = view.findViewById(R.id.classesInfo);
        dateInfo = view.findViewById(R.id.dateInfo);
        olympiadSite = view.findViewById(R.id.olympiadLink);
        favBtn = view.findViewById(R.id.addToFavBtn);

        olympiad = getArguments().getParcelable("olympiad");
        isFav = settings.getBoolean("isfav"+Integer.toString(olympiad.getId()), false);

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
            editor.commit();
            Toast.makeText(getContext(), R.string.added_to_fav, Toast.LENGTH_SHORT).show();
            favBtn.setText(R.string.remove_from_fav);
        } else {
            SharedPreferences.Editor editor = settings.edit();
            editor.remove("isfav"+Integer.toString(olympiad.getId()));
            editor.remove("fav"+Integer.toString(olympiad.getId()));
            Toast.makeText(getContext(), R.string.removed_from_fav, Toast.LENGTH_SHORT).show();
            favBtn.setText(R.string.add_to_fav);
        }
        isFav = !isFav;
    }
}
