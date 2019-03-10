package com.timickb.olympiadnotifier;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import javax.security.auth.Subject;

public class CurrentOlympiadListAdapter extends BaseAdapter {
    private Context mContext;
    private List<CurrentOlympiad> mOlympiadList;


    public CurrentOlympiadListAdapter(Context mContext, List<CurrentOlympiad> mOlympiadList) {
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

        CurrentOlympiad currentItem = mOlympiadList.get(position);

        String title = currentItem.getTitle();
        String subjects = Tools.getStringFromSubjects(currentItem.getSubjects());
        String classes = Tools.getStringFromIntClasses(currentItem.getClasses());
        String dateStart = currentItem.getDateStart();
        String dateEnd = currentItem.getDateEnd();


        TextView olympiadTitle = v.findViewById(R.id.olympiadTitle);
        TextView olympiadInfoSubjects = v.findViewById(R.id.olympiadInfoSubjects);
        TextView olympiadInfoClasses = v.findViewById(R.id.olympiadInfoClasses);
        TextView olympiadInfoDate = v.findViewById(R.id.olympiadInfoDate);

        olympiadTitle.setText(title);
        olympiadInfoClasses.setText(classes);
        olympiadInfoSubjects.setText(subjects);
        olympiadInfoDate.setText(dateStart + " - " + dateEnd);

        return v;
    }
}
