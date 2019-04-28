package com.timickb.olympiadnotifier;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.google.firebase.iid.FirebaseInstanceId;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SubjectsPopup extends DialogFragment {
    private CheckBox mathCheckBox;
    private CheckBox infCheckBox;
    private CheckBox rusCheckBox;
    private CheckBox physicsCheckBox;
    private CheckBox chemistryCheckBox;
    private CheckBox biologyCheckBox;
    private CheckBox socialCheckBox;
    private CheckBox literatureCheckBox;
    private CheckBox geographyCheckBox;
    private CheckBox foreignCheckBox;
    private CheckBox artCheckBox;
    private CheckBox economyCheckBox;
    private SharedPreferences settings;
    private Retrofit retrofit;
    private API client;
    private String token, key;

    public Dialog onCreateDialog(Bundle bundle) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.subjects_popup, null);
        builder.setView(view);
        builder.setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {}});
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {}});

        settings = getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);
        retrofit = new Retrofit.Builder().baseUrl(getString(R.string.server_url))
                .addConverterFactory(GsonConverterFactory.create()).build();
        client = retrofit.create(API.class);
        token = FirebaseInstanceId.getInstance().getToken();
        key = "";

        mathCheckBox = view.findViewById(R.id.mathCheckBox);
        infCheckBox = view.findViewById(R.id.infCheckBox);
        rusCheckBox = view.findViewById(R.id.rusCheckBox);
        physicsCheckBox = view.findViewById(R.id.physicsCheckBox);
        chemistryCheckBox = view.findViewById(R.id.chemistryCheckBox);
        biologyCheckBox = view.findViewById(R.id.biologyCheckBox);
        socialCheckBox = view.findViewById(R.id.socialCheckBox);
        literatureCheckBox = view.findViewById(R.id.literatureCheckBox);
        geographyCheckBox = view.findViewById(R.id.geographyCheckBox);
        foreignCheckBox = view.findViewById(R.id.foreignCheckBox);
        artCheckBox = view.findViewById(R.id.artCheckBox);
        economyCheckBox = view.findViewById(R.id.economyCheckBox);

        mathCheckBox.setChecked(settings.getBoolean("user_subject_math", false));
        mathCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences.Editor editor = settings.edit();
                editor.putBoolean("user_subject_math", isChecked);
                editor.commit();
                updateUser("math", isChecked);
            }
        });
        infCheckBox.setChecked(settings.getBoolean("user_subject_inf", false));
        infCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences.Editor editor = settings.edit();
                editor.putBoolean("user_subject_inf", isChecked);
                editor.commit();
                updateUser("informatics", isChecked);
            }
        });
        rusCheckBox.setChecked(settings.getBoolean("user_subject_rus", false));
        rusCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences.Editor editor = settings.edit();
                editor.putBoolean("user_subject_rus", isChecked);
                editor.commit();
                updateUser("russian", isChecked);
            }
        });
        physicsCheckBox.setChecked(settings.getBoolean("user_subject_physics", false));
        physicsCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences.Editor editor = settings.edit();
                editor.putBoolean("user_subject_physics", isChecked);
                editor.commit();
                updateUser("physics", isChecked);
            }
        });
        chemistryCheckBox.setChecked(settings.getBoolean("user_subject_chemistry", false));
        chemistryCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences.Editor editor = settings.edit();
                editor.putBoolean("user_subject_chemistry", isChecked);
                editor.commit();
                updateUser("chemistry", isChecked);
            }
        });
        biologyCheckBox.setChecked(settings.getBoolean("user_subject_biology", false));
        biologyCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences.Editor editor = settings.edit();
                editor.putBoolean("user_subject_biology", isChecked);
                editor.commit();
                updateUser("biology", isChecked);
            }
        });
        socialCheckBox.setChecked(settings.getBoolean("user_subject_social", false));
        socialCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences.Editor editor = settings.edit();
                editor.putBoolean("user_subject_social", isChecked);
                editor.commit();
                updateUser("social", isChecked);
            }
        });
        literatureCheckBox.setChecked(settings.getBoolean("user_subject_literature", false));
        literatureCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences.Editor editor = settings.edit();
                editor.putBoolean("user_subject_literature", isChecked);
                editor.commit();
                updateUser("literature", isChecked);
            }
        });
        geographyCheckBox.setChecked(settings.getBoolean("user_subject_geography", false));
        geographyCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences.Editor editor = settings.edit();
                editor.putBoolean("user_subject_geography", isChecked);
                editor.commit();
                updateUser("geography", isChecked);
            }
        });
        foreignCheckBox.setChecked(settings.getBoolean("user_subject_foreign", false));
        foreignCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences.Editor editor = settings.edit();
                editor.putBoolean("user_subject_foreign", isChecked);
                editor.commit();
                updateUser("foreign", isChecked);
            }
        });
        artCheckBox.setChecked(settings.getBoolean("user_subject_art", false));
        artCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences.Editor editor = settings.edit();
                editor.putBoolean("user_subject_art", isChecked);
                editor.commit();
                updateUser("art", isChecked);
            }
        });
        economyCheckBox.setChecked(settings.getBoolean("user_subject_economy", false));
        economyCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences.Editor editor = settings.edit();
                editor.putBoolean("user_subject_economy", isChecked);
                editor.commit();
                updateUser("economy", isChecked);
            }
        });

        return builder.create();
    }

    private void updateUser(String subject, boolean isChecked) {
        client.updateUser(key, token, subject, isChecked);
    }
}
