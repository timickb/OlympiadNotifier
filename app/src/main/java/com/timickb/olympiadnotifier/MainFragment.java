package com.timickb.olympiadnotifier;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;
import java.util.List;
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
    private ProgressBar progressBar;
    private OlympiadListAdapter adapter;
    private List<Olympiad> olympiadList;
    private SharedPreferences settings;
    private String userClass;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main, container, false);
        // импортируем настройки пользователя
        settings = getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);
        // считываем его класс
        userClass = settings.getString("class", "7");
        // инициализируем Retrofit
        Retrofit.Builder builder = new Retrofit.Builder().baseUrl(getString(R.string.server_url))
                .addConverterFactory(GsonConverterFactory.create());
        retrofit = builder.build();
        client = retrofit.create(API.class);

        progressBar = view.findViewById(R.id.progressBar);

        // обработка нажатия на кнопку вызова диалогового окна с фильтрами
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

        // делаем дефолтный запрос к серверу на основе последних фильтров
        String defaultSubject = settings.getString("last_subject", "-1");
        String defaultStage = settings.getString("last_stage", "-1");
        Call<List<Olympiad>> call = client.getNextEvents(userClass, defaultSubject, defaultStage);
        call.enqueue(new Callback<List<Olympiad>>() {
            @Override
            public void onResponse(Call<List<Olympiad>> call, Response<List<Olympiad>> response) {
                olympiadList = response.body();
                olympiadListView = view.findViewById(R.id.olympiadsList);
                adapter = new OlympiadListAdapter(getContext(), olympiadList);
                olympiadListView.setAdapter(adapter);
                progressBar.setVisibility(View.GONE);
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
                progressBar.setVisibility(View.GONE);
            }
        });


        return view;
    }

    public static boolean hasConnection(final Context context) {
        // проверка соединения с сетью
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
        // обработка нажатия кнопки "ок" в окне выбора фильтров
        if(!hasConnection(getContext())) {
            Toast.makeText(getContext(), R.string.no_internet, Toast.LENGTH_SHORT).show();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        Call<List<Olympiad>> call = client.getNextEvents(class_, subject, stage);

        call.enqueue(new Callback<List<Olympiad>>() {
            @Override
            public void onResponse(Call<List<Olympiad>> call, Response<List<Olympiad>> response) {
                olympiadList = response.body();
                olympiadList = response.body();
                olympiadListView = view.findViewById(R.id.olympiadsList);
                adapter = new OlympiadListAdapter(getContext(), olympiadList);
                olympiadListView.setAdapter(adapter);
                progressBar.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<List<Olympiad>> call, Throwable t) {
                Toast.makeText(getContext(), R.string.no_internet, Toast.LENGTH_SHORT).show();
                System.out.println(t.getMessage());
                progressBar.setVisibility(View.GONE);
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
