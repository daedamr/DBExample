package com.example.DBExample.db;

import android.database.sqlite.SQLiteDatabase;

import static com.example.DBExample.util.Constants.*;

public class PresentationsTable {

    public static final String PRESENTATION_TABLE_CREATE = "create table "
            + PRESENTATIONS_TABLE   + "("
            + PRESENTATION_ID + " integer primary key autoincrement, "
            + PRESENTATION_TITLE + " text not null, "
            + PRESENTATION_AUTHOR + " text not null" + ");";

    public static void onCreate(SQLiteDatabase database) {
        database.execSQL(PRESENTATION_TABLE_CREATE);
    }

    public static void onUpgrade(SQLiteDatabase database, int oldVersion,
                                 int newVersion) {
        database.execSQL("DROP TABLE IF EXISTS " + PRESENTATIONS_TABLE);
        onCreate(database);
    }
}
