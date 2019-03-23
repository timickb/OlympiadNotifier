package com.timickb.olympiadnotifier;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class InfoFragment extends Fragment {

    public void onResume(){
        super.onResume();

        ((MainActivity) getActivity()).setActionBarTitle(getActivity().getString(R.string.info_title));
    }
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        ((MainActivity) getActivity()).setActionBarTitle(getActivity().getString(R.string.info_title));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_info, container, false);
        TextView feedbackLink = view.findViewById(R.id.feedback);
        feedbackLink.setMovementMethod(LinkMovementMethod.getInstance());
        TextView githubLink = view.findViewById(R.id.sourcesLink);
        githubLink.setMovementMethod(LinkMovementMethod.getInstance());
        TextView apiLink = view.findViewById(R.id.apiLink);
        apiLink.setMovementMethod(LinkMovementMethod.getInstance());
        return view;
    }
}
