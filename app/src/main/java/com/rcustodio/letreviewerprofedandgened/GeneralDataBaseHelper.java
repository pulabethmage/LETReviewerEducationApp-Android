package com.rcustodio.letreviewerprofedandgened;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class GeneralDataBaseHelper extends SQLiteOpenHelper {


    private static final String TABLE_NAME = "generalEducation";

    public GeneralDataBaseHelper(@Nullable Context context) {
        super(context, TABLE_NAME, null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        String TASK_TABLE = "CREATE TABLE generalEducation (id INTEGER PRIMARY KEY AUTOINCREMENT, question TEXT, answer1 TEXT,answer2 TEXT,answer3 TEXT,answer4 TEXT,answercol TEXT)";
        db.execSQL(TASK_TABLE);



    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }




}
