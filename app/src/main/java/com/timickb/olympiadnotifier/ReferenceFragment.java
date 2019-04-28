package com.timickb.olympiadnotifier;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class ReferenceFragment extends Fragment {


    public void onResume(){
        super.onResume();

        ((MainActivity) getActivity()).setActionBarTitle(getActivity().getString(R.string.reference_title));
    }
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        ((MainActivity) getActivity()).setActionBarTitle(getActivity().getString(R.string.reference_title));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_reference, container, false);
    }
}
