package com.timickb.olympiadnotifier;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class FavouritesFragment extends Fragment {

    private View view;
    private ListView favListView;
    private ArrayList<Olympiad> olympiadList = new ArrayList<Olympiad>();
    private OlympiadListAdapter adapter;
    private SharedPreferences settings;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_favourites, container, false);

        settings = getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);

        Gson gson = new Gson();
        String json = settings.getString("fav_list", null);
        Type type = new TypeToken<ArrayList<Integer>>() {}.getType();
        ArrayList<Integer> ids = gson.fromJson(json, type);
        if(ids.size() > 0) {
            for(int i = 0; i < ids.size(); i++) {
                json = settings.getString("fav"+ids.get(i), "");
                olympiadList.add(gson.fromJson(json, Olympiad.class));
            }
            favListView = view.findViewById(R.id.favList);
            adapter = new OlympiadListAdapter(getContext(), olympiadList);
            favListView.setAdapter(adapter);
        }

        return view;
    }
}
