package com.timickb.olympiadnotifier;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatDelegate;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import static android.content.Context.MODE_PRIVATE;


public class SettingsFragment extends Fragment {
    private Spinner classChooser, startScreenChooser;
    private EditText timeInput;
    private Switch notifiesSwitch, recomendsSwitch;
    private Button changeSubjectsBtn;
    private SharedPreferences settings;
    private View view;
    private String userClass;

    public void onResume(){
        super.onResume();

        ((MainActivity) getActivity()).setActionBarTitle(getActivity().getString(R.string.settings_title));
    }
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        ((MainActivity) getActivity()).setActionBarTitle(getActivity().getString(R.string.settings_title));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_settings, container, false);
        settings = getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);
        userClass = settings.getString("class", "7");

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
                SharedPreferences.Editor editor = settings.edit();
                editor.putString("class", class_);
                editor.commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        startScreenChooser = view.findViewById(R.id.startScreenChooser);
        ArrayAdapter<CharSequence> startScreenAdapter = ArrayAdapter.createFromResource(getContext(), R.array.start_fragments, android.R.layout.simple_spinner_item);
        startScreenAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        startScreenChooser.setAdapter(startScreenAdapter);
        String userStartFragment = settings.getString("start_fragment", "main");
        switch(userStartFragment) {
            case "main": startScreenChooser.setSelection(0);
            break;
            case "current": startScreenChooser.setSelection(1);
            break;
            case "favourites": startScreenChooser.setSelection(2);
        }
        startScreenChooser.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String text = parent.getSelectedItem().toString();
                SharedPreferences.Editor editor = settings.edit();
                if(text == getString(R.string.main_title)) {
                    editor.putString("start_fragment", "main");
                    editor.commit();
                } else if(text == getString(R.string.current_title)) {
                    editor.putString("start_fragment", "current");
                    editor.commit();
                } else if(text == getString(R.string.favourites)) {
                    editor.putString("start_fragment", "favourites");
                    editor.commit();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });



        timeInput = view.findViewById(R.id.timeInput);
        String time = settings.getString("notify_time", "18:00");
        timeInput.setText(time);
        timeInput.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                String newTime = parseTime(timeInput.getText().toString());
                SharedPreferences.Editor editor = settings.edit();
                editor.putString("notify_time", newTime);
                editor.commit();
            }
        });

        notifiesSwitch = view.findViewById(R.id.notifiesSwitch);
        recomendsSwitch = view.findViewById(R.id.recomendsSwitch);
        notifiesSwitch.setChecked(settings.getBoolean("notifies_switch", false));
        recomendsSwitch.setChecked(settings.getBoolean("recomends_switch", false));
        notifiesSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences.Editor editor = settings.edit();
                editor.putBoolean("notifies_switch", isChecked);
                editor.commit();
            }
        });
        recomendsSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences.Editor editor = settings.edit();
                editor.putBoolean("recomends_switch", isChecked);
                editor.commit();
            }
        });

        changeSubjectsBtn = view.findViewById(R.id.changeSubjectsBtn);
        changeSubjectsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SubjectsPopup popup = new SubjectsPopup();

                popup.show(getFragmentManager(), "subjects popup");
                popup.setTargetFragment(SettingsFragment.this, 1);
            }
        });

        return view;
    }

    private String parseTime(String time) {
        try {
            int hours = Integer.parseInt(time.split(":")[0]);
            int minutes = Integer.parseInt(time.split(":")[1]);
            if (hours >= 0 && hours <= 23 && minutes >= 0 && minutes <= 59) {
                String shours = "";
                String sminutes = "";
                if(hours < 10) shours += "0";
                if(minutes < 10) sminutes += "0";
                shours += Integer.toString(hours);
                sminutes += Integer.toString(minutes);
                return shours + ":" + sminutes;
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return "18:00";
    }
}
