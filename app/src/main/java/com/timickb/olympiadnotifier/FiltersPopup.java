package com.timickb.olympiadnotifier;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.HashMap;
import java.util.Map;

public class FiltersPopup extends DialogFragment {
    private Spinner classChooser, subjectChooser, stageChooser;
    private Map<String, String> filtersDict = new HashMap<String, String>();
    private FiltersPopupListener listener;
    private SharedPreferences settings;

    private int getPositionBySubject(String s) {
        switch(s) {
            case "mathematics": return 1;
            case "russian": return 2;
            case "informatics": return 3;
            case "physics": return 4;
            case "chemistry": return 5;
            case "biology": return 6;
            case "social": return 7;
            case "literature": return 8;
            case "geography": return 9;
            case "foreign": return 10;
            case "art": return 11;
            case "economy": return 12;
            default: return 0;
        }
    }

    @Override
    public void onAttach(Context context) {
        try {
            listener = (FiltersPopupListener) getTargetFragment();
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implements FiltersPopupListener");
        }
        super.onAttach(context);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.filters_popup, null);
        builder.setView(view);
        builder.setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {}
        });
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String class_ = filtersDict.get("class");
                String subject = filtersDict.get("subject");
                String stage = filtersDict.get("stage");
                listener.applyData(class_, subject, stage);
            }
        });

        settings = getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);

        filtersDict.put("class", Integer.toString(getArguments().getInt("user_class")));
        filtersDict.put("subject", "-1");
        filtersDict.put("stage", "-1");

        classChooser = view.findViewById(R.id.classChooser2);
        subjectChooser = view.findViewById(R.id.subjectChooser2);
        stageChooser = view.findViewById(R.id.stageChooser);

        ArrayAdapter<CharSequence> classAdapter = ArrayAdapter.createFromResource(getContext(), R.array.classes, android.R.layout.simple_spinner_item);
        classAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        classChooser.setAdapter(classAdapter);
        classChooser.setSelection(getArguments().getInt("user_class")-5);
        classChooser.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String text = parent.getSelectedItem().toString();
                String class_ = text.split(" ")[0];
                filtersDict.put("class", class_);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        ArrayAdapter<CharSequence> subjectAdapter = ArrayAdapter.createFromResource(getContext(), R.array.subjects, android.R.layout.simple_spinner_item);
        subjectAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        subjectChooser.setAdapter(subjectAdapter);
        subjectChooser.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item_raw = parent.getSelectedItem().toString();
                String item = "-1";

                if(item_raw == getString(R.string.math_subj)) item = "mathematics";
                else if(item_raw == getString(R.string.russian_subj)) item = "russian";
                else if(item_raw == getString(R.string.informatics_subj)) item = "informatics";
                else if(item_raw == getString(R.string.physics_subj)) item = "physics";
                else if(item_raw == getString(R.string.chemistry_subj)) item = "chemistry";
                else if(item_raw == getString(R.string.biology_subj)) item = "biology";
                else if(item_raw == getString(R.string.social_subj)) item = "social";
                else if(item_raw == getString(R.string.literature_subj)) item = "literature";
                else if(item_raw == getString(R.string.geography_subj)) item = "geography";
                else if(item_raw == getString(R.string.foreign_subj)) item = "foreign";
                else if(item_raw == getString(R.string.art_subj)) item = "art";
                else if(item_raw == getString(R.string.economy_subj)) item = "economy";

                filtersDict.put("subject", item);

                SharedPreferences.Editor editor = settings.edit();
                editor.putString("last_subject", item);
                editor.commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
        subjectChooser.setSelection(getPositionBySubject(settings.getString("last_subject", "-1")));

        ArrayAdapter<CharSequence> stageAdapter = ArrayAdapter.createFromResource(getContext(), R.array.stages, android.R.layout.simple_spinner_item);
        stageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        stageChooser.setAdapter(stageAdapter);
        stageChooser.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item_raw = parent.getSelectedItem().toString();
                String item = "-1";

                if(item_raw == getString(R.string.selection_stage)) item = "selection";
                else if(item_raw == getString(R.string.final_stage)) item = "final";

                filtersDict.put("stage", item);

                SharedPreferences.Editor editor = settings.edit();
                editor.putString("last_stage", item);
                editor.commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
        switch(settings.getString("last_stage", "-1")) {
            case "selection":
                stageChooser.setSelection(1);
                break;
            case "final":
                stageChooser.setSelection(2);
                break;
            default:
                stageChooser.setSelection(0);
        }

        return builder.create();
    }

    public interface FiltersPopupListener {
        void applyData(String class_, String subject, String stage);
    }
}
