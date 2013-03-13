package com.example.DBExample.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.DBExample.model.Presentation;

import java.util.ArrayList;
import java.util.List;

import static com.example.DBExample.util.Constants.*;

public class PresentationsDAO {
    // Database fields
    private SQLiteDatabase database;
    private PresentationsSQLiteOpenHelper dbHelper;
    private String[] allColumns = {PRESENTATION_ID, PRESENTATION_TITLE, PRESENTATION_AUTHOR };

    public PresentationsDAO(Context context) {
        dbHelper = new PresentationsSQLiteOpenHelper(context);
    }

    public void open() {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Presentation createPresentation(String title, String author) {
        open();
        ContentValues values = new ContentValues();
        values.put(PRESENTATION_TITLE, title);
        values.put(PRESENTATION_AUTHOR, author);
        long insertId = database.insert(PRESENTATIONS_TABLE, null, values);
        Cursor cursor = database.query(PRESENTATIONS_TABLE,
                allColumns, PRESENTATION_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        Presentation presentation = cursorToPresentation(cursor);
        cursor.close();
        close();
        return presentation;
    }

    public void deletePresentation(Presentation presentation) {
        open();
        long id = presentation.getId();
        database.delete(PRESENTATIONS_TABLE, PRESENTATION_ID + " = " + id, null);
        close();
    }

    public List<Presentation> getAllPresentations() {
        open();
        List<Presentation> presentations = new ArrayList<Presentation>();

        Cursor cursor = database.query(PRESENTATIONS_TABLE,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Presentation presentation = cursorToPresentation(cursor);
            presentations.add(presentation);
            cursor.moveToNext();
        }
        // Make sure to close the cursor
        cursor.close();
        close();
        return presentations;
    }

    private Presentation cursorToPresentation(Cursor cursor) {
        Presentation presentation = new Presentation();
        presentation.setId(cursor.getLong(0));
        presentation.setTitle(cursor.getString(1));
        presentation.setAuthor(cursor.getString(2));
        return presentation;
    }
}
