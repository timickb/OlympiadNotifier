package com.timickb.olympiadnotifier;

import android.content.Context;
import android.content.SharedPreferences;
import android.icu.text.SymbolTable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainFragment extends Fragment implements FiltersPopup.FiltersPopupListener {

    private View view;
    private Retrofit retrofit;
    private API client;
    private FloatingActionButton filtersOpenBtn;
    private ListView olympiadListView;
    private OlympiadListAdapter adapter;
    private List<Olympiad> olympiadList;
    private SharedPreferences settings;
    private String userClass;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main, container, false);
        // initialize settings
        settings = getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);
        // read user settings
        userClass = settings.getString("class", "7");
        // initialize retrofit
        Retrofit.Builder builder = new Retrofit.Builder().baseUrl(getString(R.string.server_url))
                .addConverterFactory(GsonConverterFactory.create());
        retrofit = builder.build();
        client = retrofit.create(API.class);

        // filters open button handler
        filtersOpenBtn = view.findViewById(R.id.filtersOpenBtn);
        filtersOpenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FiltersPopup popup = new FiltersPopup();
                Bundle args = new Bundle();
                args.putInt("user_class", Integer.parseInt(userClass));
                popup.setArguments(args);

                popup.show(getFragmentManager(), "filters popup");
                popup.setTargetFragment(MainFragment.this, 1);
            }
        });

        Call<List<Olympiad>> call = client.getNextEvents(userClass, "-1", "-1");

        call.enqueue(new Callback<List<Olympiad>>() {
            @Override
            public void onResponse(Call<List<Olympiad>> call, Response<List<Olympiad>> response) {
                olympiadList = response.body();
                olympiadListView = view.findViewById(R.id.olympiadsList);
                adapter = new OlympiadListAdapter(getContext(), olympiadList);
                olympiadListView.setAdapter(adapter);
                olympiadListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Olympiad olympiad = olympiadList.get(position);
                        OlympiadFragment newFragment = new OlympiadFragment();
                        Bundle args = new Bundle();
                        args.putParcelable("olympiad", olympiad);
                        newFragment.setArguments(args);

                        FragmentTransaction fr = getFragmentManager().beginTransaction();
                        fr.replace(R.id.fragment, newFragment);
                        fr.commit();
                    }
                });
            }

            @Override
            public void onFailure(Call<List<Olympiad>> call, Throwable t) {
                Toast.makeText(getContext(), R.string.no_internet, Toast.LENGTH_SHORT).show();
                System.out.println(t.getMessage());
            }
        });


        return view;
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

    @Override
    public void applyData(String class_, String subject, String stage) {
        if(!hasConnection(getContext())) {
            Toast.makeText(getContext(), R.string.no_internet, Toast.LENGTH_SHORT).show();
            return;
        }

        Call<List<Olympiad>> call = client.getNextEvents(class_, subject, stage);

        call.enqueue(new Callback<List<Olympiad>>() {
            @Override
            public void onResponse(Call<List<Olympiad>> call, Response<List<Olympiad>> response) {
                olympiadList = response.body();
                olympiadList = response.body();
                olympiadListView = view.findViewById(R.id.olympiadsList);
                adapter = new OlympiadListAdapter(getContext(), olympiadList);
                olympiadListView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Olympiad>> call, Throwable t) {
                Toast.makeText(getContext(), R.string.no_internet, Toast.LENGTH_SHORT).show();
                System.out.println(t.getMessage());
            }
        });
    }

    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).setActionBarTitle(getActivity().getString(R.string.main_title));
    }
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        ((MainActivity) getActivity()).setActionBarTitle(getActivity().getString(R.string.main_title));
        setRetainInstance(true);
    }
}
