package com.lucascabrales.gradesdatabase.models;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.lucascabrales.gradesdatabase.MainActivity;
import com.lucascabrales.gradesdatabase.data.StorageManager;

import java.util.ArrayList;

/**
 * Created by lucascabrales on 10/16/17.
 */

public class Grade implements Parcelable {
    public static final String TABLE_NAME = "grade";
    public static final String TABLE = "create table " + TABLE_NAME +
            "(year text," +
            "grade text," +
            "exam text," +
            "subject text)";

    public String year;
    public String grade;
    public String exam;
    public String subject;

    public Grade() {

    }

    //region Database
    public static void addObj(Grade obj) {
        try {
            ContentValues values = new ContentValues();

            values.put("year", obj.year);
            values.put("grade", obj.grade);
            values.put("exam", obj.exam);
            values.put("subject", obj.subject);

            StorageManager.getDb().insert(TABLE_NAME, "", values);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void updateObj(Grade obj) {
        try {
            ContentValues values = new ContentValues();

            values.put("year", obj.year);
            values.put("grade", obj.grade);
            values.put("exam", obj.exam);
            values.put("subject", obj.subject);

            StorageManager.getDb().update(TABLE_NAME, values, "subject='" + obj.subject + "'", null);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void deleteObj(Grade obj) {
        try {
            StorageManager.getDb().delete(TABLE_NAME, "subject='" + obj.subject + "'", null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Grade getObj(String subject) {
        Grade obj = new Grade();

        try {
            String query = "select * from " + TABLE_NAME + " where subject='" + subject + "'";
            Cursor c = StorageManager.getDb().rawQuery(query, null);
            if (c.getCount() > 0) {
                c.moveToFirst();
                obj.year = c.getString(c.getColumnIndex("year"));
                obj.grade = c.getString(c.getColumnIndex("grade"));
                obj.exam = c.getString(c.getColumnIndex("exam"));
                obj.subject = c.getString(c.getColumnIndex("subject"));
            }
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

    public static ArrayList<Grade> getList() {
        return getList(StorageManager.getOrderBy());
    }

    public static ArrayList<Grade> getList(String orderBy) {
        ArrayList<Grade> list = new ArrayList<>();

        switch (orderBy) {
            case MainActivity.SUBJECT:
                orderBy = "subject";
                break;
            case MainActivity.YEAR:
                orderBy = "year";
                break;
            case MainActivity.GRADE:
                orderBy = "grade";
                break;
            case MainActivity.EXAM:
                orderBy = "exam asc";
                break;
        }

        try {
            String query = "select * from " + TABLE_NAME + " order by " + orderBy;
            Cursor c = StorageManager.getDb().rawQuery(query, null);
            c.moveToFirst();
            while (!c.isAfterLast()) {
                Grade obj = new Grade();
                obj.year = c.getString(c.getColumnIndex("year"));
                obj.grade = c.getString(c.getColumnIndex("grade"));
                obj.exam = c.getString(c.getColumnIndex("exam"));
                obj.subject = c.getString(c.getColumnIndex("subject"));

                list.add(obj);
                c.moveToNext();
            }

            c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public static void deleteTable() {
        try {
            StorageManager.getDb().delete(TABLE_NAME, null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //endregion

    //region Parcelable
    protected Grade(Parcel in) {
        year = in.readString();
        grade = in.readString();
        exam = in.readString();
        subject = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(year);
        dest.writeString(grade);
        dest.writeString(exam);
        dest.writeString(subject);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Grade> CREATOR = new Parcelable.Creator<Grade>() {
        @Override
        public Grade createFromParcel(Parcel in) {
            return new Grade(in);
        }

        @Override
        public Grade[] newArray(int size) {
            return new Grade[size];
        }
    };
    //endregion
}
