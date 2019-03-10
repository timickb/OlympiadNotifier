package com.timickb.olympiadnotifier;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class CurrentEventsFragment extends Fragment implements View.OnClickListener {

    private View view;
    private Retrofit retrofit;
    private API client;
    private Button applyBtn;
    private Spinner classChooser, subjectChooser;
    private ListView olympiadListView;
    private OlympiadListAdapter adapter;
    private Map<String, String> filtersDict = new HashMap<String, String>();
    private List<Olympiad> olympiadList;
    private SharedPreferences settings;
    private String userClass;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_current_events, container, false);

        settings = getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);
        userClass = settings.getString("class", "7");

        Retrofit.Builder builder = new Retrofit.Builder().baseUrl(getString(R.string.server_url))
                .addConverterFactory(GsonConverterFactory.create());
        retrofit = builder.build();
        client = retrofit.create(API.class);

        // initialize filters dict
        filtersDict.put("class", userClass);
        filtersDict.put("subject", "-1");

        classChooser = (Spinner) view.findViewById(R.id.classChooserC);
        subjectChooser = (Spinner) view.findViewById(R.id.subjectChooserC);

        // class spinner handler
        ArrayAdapter<CharSequence> classAdapter = ArrayAdapter.createFromResource(getContext(), R.array.classes, android.R.layout.simple_spinner_item);
        classAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        classChooser.setAdapter(classAdapter);
        classChooser.setSelection(Integer.parseInt(userClass)-5);
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

        // subject spinner handler
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
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        // apply button handler
        applyBtn = view.findViewById(R.id.applyBtnC);
        applyBtn.setOnClickListener(this);

        // make default query
        String class_ = filtersDict.get("class");
        String subject = filtersDict.get("subject");

        Call<List<Olympiad>> call = client.getCurrentEvents(class_, subject);

        call.enqueue(new Callback<List<Olympiad>>() {
            @Override
            public void onResponse(Call<List<Olympiad>> call, Response<List<Olympiad>> response) {
                olympiadList = response.body();
                olympiadListView = view.findViewById(R.id.currentList);
                adapter = new OlympiadListAdapter(getContext(), olympiadList);
                olympiadListView.setAdapter(adapter);
                olympiadListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Olympiad olympiad = olympiadList.get(position);
                        OlympiadFragment newFragment = new OlympiadFragment();
                        Bundle args = new Bundle();
                        args.putString("title", olympiad.getTitle());
                        args.putString("date_start", olympiad.getDateStart());
                        args.putString("date_end", olympiad.getDateEnd());
                        args.putIntegerArrayList("classes", olympiad.getClasses());
                        args.putStringArrayList("subjects", olympiad.getSubjects());
                        args.putString("link", olympiad.getLink());
                        newFragment.setArguments(args);

                        FragmentTransaction fr = getFragmentManager().beginTransaction();
                        fr.replace(R.id.fragment, newFragment);
                        fr.commit();
                    }
                });
            }

            @Override
            public void onFailure(Call<List<Olympiad>> call, Throwable t) {
                Toast.makeText(getContext(), R.string.nothing_found, Toast.LENGTH_SHORT).show();
                System.out.println(t.getMessage());
            }
        });


        return view;
    }

    @Override
    public void onClick(View v) {
        if(!hasConnection(getContext())) {
            Toast.makeText(getContext(), R.string.no_internet, Toast.LENGTH_SHORT).show();
            return;
        }

        String class_ = filtersDict.get("class");
        String subject = filtersDict.get("subject");

        Call<List<Olympiad>> call = client.getCurrentEvents(class_, subject);

        call.enqueue(new Callback<List<Olympiad>>() {
            @Override
            public void onResponse(Call<List<Olympiad>> call, Response<List<Olympiad>> response) {
                olympiadList = response.body();
                olympiadList = response.body();
                olympiadListView = view.findViewById(R.id.currentList);
                adapter = new OlympiadListAdapter(getContext(), olympiadList);
                olympiadListView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Olympiad>> call, Throwable t) {
                Toast.makeText(getContext(), R.string.nothing_found, Toast.LENGTH_SHORT).show();
                System.out.println(t.getMessage());
            }
        });
    }

    public static boolean hasConnection(final Context context)
    {
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifiInfo != null && wifiInfo.isConnected())
        {
            return true;
        }
        wifiInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (wifiInfo != null && wifiInfo.isConnected())
        {
            return true;
        }
        wifiInfo = cm.getActiveNetworkInfo();
        if (wifiInfo != null && wifiInfo.isConnected())
        {
            return true;
        }
        return false;
    }
}
