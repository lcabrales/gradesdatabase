package com.lucascabrales.gradesdatabase.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.lucascabrales.gradesdatabase.R;
import com.lucascabrales.gradesdatabase.models.Grade;

import java.util.ArrayList;

/**
 * Created by lucascabrales on 10/16/17.
 */

public class GradesAdapter extends ArrayAdapter<Grade> {
    private Context mContext;
    private ArrayList<Grade> mDataset;

    public GradesAdapter(Context context, ArrayList<Grade> dataSet) {
        super(context, R.layout.row_grade);
        mContext = context;
        mDataset = dataSet;
    }

    public void setData(ArrayList<Grade> dataSet) {
        mDataset = dataSet;
        notifyDataSetChanged();
    }

    @Override
    public Grade getItem(int position) {
        return mDataset.get(position);
    }

    @Override
    public int getCount() {
        return mDataset.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        GradesAdapter.ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.row_grade, parent, false);
            holder = new GradesAdapter.ViewHolder();
            holder.subject = (TextView) convertView.findViewById(R.id.tv_subject);
            holder.grade = (TextView) convertView.findViewById(R.id.tv_grade);
            holder.year = (TextView) convertView.findViewById(R.id.tv_year);
            holder.exam = (TextView) convertView.findViewById(R.id.tv_exam);

            convertView.setTag(holder);
        } else {
            holder = (GradesAdapter.ViewHolder) convertView.getTag();
        }

        Grade obj = mDataset.get(position);

        holder.subject.setText(obj.subject);
        holder.grade.setText(obj.grade);
        holder.year.setText(obj.year);
        holder.exam.setText(obj.exam);

        return convertView;
    }

    private static class ViewHolder {
        private TextView subject;
        private TextView grade;
        private TextView year;
        private TextView exam;
    }
}
