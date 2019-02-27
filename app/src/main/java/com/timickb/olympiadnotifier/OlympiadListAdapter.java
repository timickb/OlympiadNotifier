package com.timickb.olympiadnotifier;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class OlympiadListAdapter extends BaseAdapter {
    private Context mContext;
    private List<Olympiad> mOlympiadList;


    public OlympiadListAdapter(Context mContext, List<Olympiad> mOlympiadList) {
        this.mContext = mContext;
        this.mOlympiadList = mOlympiadList;
    }

    @Override
    public int getCount() {
        return mOlympiadList.size();
    }

    @Override
    public Object getItem(int position) {
        return mOlympiadList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = View.inflate(mContext, R.layout.olympiad_item, null);
        Olympiad currentItem = mOlympiadList.get(position);
        TextView olympiadTitle = v.findViewById(R.id.olympiadTitle);
        TextView olympiadInfo = v.findViewById(R.id.olympiadInfo);
        TextView olympiadInfo2 = v.findViewById(R.id.olympiadInfo2);
        olympiadTitle.setText(mOlympiadList.get(position).getTitle());
        olympiadInfo.setText(mOlympiadList.get(position).getSubjects().get(0));
        olympiadInfo2.setText(mOlympiadList.get(position).getClasses().get(0));
        return v;
    }
}
