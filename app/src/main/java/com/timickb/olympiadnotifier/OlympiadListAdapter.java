package com.timickb.olympiadnotifier;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.Calendar;
import java.util.List;

public class OlympiadListAdapter extends BaseAdapter {
    private Context mContext;
    private List<Olympiad> mOlympiadList;
    private int currentDay, currentMonth;


    public OlympiadListAdapter(Context mContext, List<Olympiad> mOlympiadList) {
        Calendar calendar = Calendar.getInstance();
        this.currentDay = calendar.get(Calendar.DAY_OF_MONTH);
        this.currentMonth = calendar.get(Calendar.MONTH)+1;
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

        TextView olympiadTitle = v.findViewById(R.id.olympiadTitle);
        TextView olympiadInfoSubjects = v.findViewById(R.id.olympiadInfoSubjects);
        TextView olympiadInfoClasses = v.findViewById(R.id.olympiadInfoClasses);
        TextView olympiadInfoDate = v.findViewById(R.id.olympiadInfoDate);

        Olympiad currentItem = mOlympiadList.get(position);

        String title = currentItem.getTitle();
        String subjects = Tools.getStringFromSubjects(currentItem.getSubjects());
        String classes = Tools.getStringFromClasses(currentItem.getClasses());
        String dateStart = currentItem.getDateStart();
        String dateEnd = currentItem.getDateEnd();
        String date = dateStart + " - " + dateEnd;
        if(Tools.isExpired(dateEnd, currentDay, currentMonth)) {
            date = mContext.getString(R.string.expired);
        } else if(Tools.isToday(dateEnd, currentDay, currentMonth)) {
            date = mContext.getString(R.string.ends_today);
            olympiadInfoDate.setTextColor(mContext.getResources().getColor(android.R.color.holo_red_dark));
        } else if(Tools.isTomorrow(dateEnd, currentDay, currentMonth)) {
            date = mContext.getString(R.string.ends_tomorrow);
            olympiadInfoDate.setTextColor(mContext.getResources().getColor(android.R.color.holo_red_dark));
        }

        olympiadTitle.setText(title);
        olympiadInfoClasses.setText(classes);
        olympiadInfoSubjects.setText(subjects);
        olympiadInfoDate.setText(date);

        return v;
    }
}
