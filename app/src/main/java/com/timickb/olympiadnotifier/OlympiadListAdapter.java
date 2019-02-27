package com.timickb.olympiadnotifier;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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

        String title = currentItem.getTitle();
        String classes = Tools.getStringFromClasses(currentItem.getClasses());
        String subjects = Tools.getStringFromSubjects(currentItem.getSubjects());
        String date = Tools.getStringFromMonths(currentItem.getDateStart(), currentItem.getDateEnd());

        TextView olympiadTitle = v.findViewById(R.id.olympiadTitle);
        TextView olympiadInfoSubjects = v.findViewById(R.id.olympiadInfoSubjects);
        TextView olympiadInfoClasses = v.findViewById(R.id.olympiadInfoClasses);
        TextView olympiadInfoDate = v.findViewById(R.id.olympiadInfoDate);

        olympiadTitle.setText(title);
        olympiadInfoSubjects.setText(subjects);
        olympiadInfoClasses.setText(classes);
        olympiadInfoDate.setText(date);
        return v;
    }
}
