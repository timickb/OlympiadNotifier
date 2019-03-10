package com.timickb.olympiadnotifier;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatDelegate;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;

import static android.content.Context.MODE_PRIVATE;


public class SettingsFragment extends Fragment {
    private Spinner classChooser;
    private Switch themeSwitch;
    private SharedPreferences prefs;
    private View view;
    private String userClass;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_settings, container, false);
        prefs = getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);
        userClass = prefs.getString("class", "7");

        classChooser = view.findViewById(R.id.userClassChooser);
        ArrayAdapter<CharSequence> classAdapter = ArrayAdapter.createFromResource(getContext(), R.array.classes, android.R.layout.simple_spinner_item);
        classAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        classChooser.setAdapter(classAdapter);
        classChooser.setSelection(Integer.parseInt(userClass)-5);
        classChooser.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String text = parent.getSelectedItem().toString();
                String class_ = text.split(" ")[0];
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("class", class_);
                editor.commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        themeSwitch = view.findViewById(R.id.themeSwitch);
        if(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            themeSwitch.setChecked(true);
        }
        themeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }
            }
        });

        return view;
    }
}
