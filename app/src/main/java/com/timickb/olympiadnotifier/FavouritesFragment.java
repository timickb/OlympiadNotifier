package com.timickb.olympiadnotifier;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class FavouritesFragment extends Fragment {

    private View view;
    private ListView favListView;
    private ArrayList<Olympiad> olympiadList = new ArrayList<Olympiad>();
    private OlympiadListAdapter adapter;
    private SharedPreferences settings;
    private int currentDay, currentMonth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_favourites, container, false);

        settings = getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);

        Calendar calendar = Calendar.getInstance();
        this.currentDay = calendar.get(Calendar.DAY_OF_MONTH);
        this.currentMonth = calendar.get(Calendar.MONTH);

        Gson gson = new Gson();
        String json = settings.getString("fav_list", null);
        Type type = new TypeToken<ArrayList<Integer>>() {}.getType();
        ArrayList<Integer> ids = gson.fromJson(json, type);
        if(ids.size() > 0) {
            for(int i = 0; i < ids.size(); i++) {
                json = settings.getString("fav"+ids.get(i), "");
                Olympiad newOlympiad = gson.fromJson(json, Olympiad.class);
                if(Tools.isExpired(newOlympiad.getDateEnd(), currentDay, currentMonth)) {
                    SharedPreferences.Editor editor = settings.edit();
                    editor.remove("fav"+ids.get(i));
                    String favIDsRaw = settings.getString("fav_list", null);
                    Type type0 = new TypeToken<ArrayList<Integer>>() {}.getType();
                    ArrayList<Integer> favIDs = gson.fromJson(favIDsRaw, type0);
                    favIDs.remove(new Integer(ids.get(i)));
                    String newFavList = gson.toJson(favIDs);
                    editor.putString("fav_list", newFavList);
                    editor.commit();
                    continue;
                }
                olympiadList.add(newOlympiad);
            }
            favListView = view.findViewById(R.id.favList);
            adapter = new OlympiadListAdapter(getContext(), olympiadList);
            favListView.setAdapter(adapter);

            favListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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

        return view;
    }
}
