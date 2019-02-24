package com.timickb.olympiadnotifier;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class MainFragment extends Fragment {

    Button applyBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        applyBtn = (Button) getView().findViewById(R.id.applyBtn);
        return inflater.inflate(R.layout.fragment_main, container, false);
    }
}