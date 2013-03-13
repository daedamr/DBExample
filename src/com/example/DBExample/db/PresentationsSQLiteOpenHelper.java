package com.example.DBExample.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.example.DBExample.util.Constants.DATABASE_NAME;
import static com.example.DBExample.util.Constants.DATABASE_VERSION;

public class PresentationsSQLiteOpenHelper extends SQLiteOpenHelper {

    public PresentationsSQLiteOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        PresentationsTable.onCreate(sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        PresentationsTable.onUpgrade(sqLiteDatabase, oldVersion, newVersion);
    }
}
