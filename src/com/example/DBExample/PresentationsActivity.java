package com.example.DBExample;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import com.example.DBExample.db.PresentationsDAO;
import com.example.DBExample.model.Presentation;

import java.util.List;

public class PresentationsActivity extends ListActivity {

    public static final int DELETE_ID = 101;
    private List<Presentation> presentations;
    private ArrayAdapter<Presentation> adapter;
    private PresentationsDAO presentationsDAO;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        final Button addButton = (Button) findViewById(R.id.add);
        presentationsDAO = new PresentationsDAO(this);

        presentations = presentationsDAO.getAllPresentations();

        adapter = new ArrayAdapter<Presentation>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, presentations);
        setListAdapter(adapter);
        registerForContextMenu(getListView());

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(PresentationsActivity.this);

                alert.setTitle("Title");
                alert.setMessage("Message");

                final View dialogView = getLayoutInflater().inflate(R.layout.dialog, null);
                alert.setView(dialogView);
                final EditText presentationTitle = (EditText) dialogView.findViewById(R.id.presentation_title);
                final EditText presentationAuthor = (EditText) dialogView.findViewById(R.id.presentation_author);

                alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        final Editable title = presentationTitle.getText();
                        final Editable author = presentationAuthor.getText();

                        final Presentation presentation =
                                presentationsDAO.createPresentation(title.toString(), author.toString());

                        addPresentation(presentation);
                    }
                });

                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // Canceled.
                    }
                });

                alert.show();
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, DELETE_ID, 0, R.string.menu_delete);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case DELETE_ID:
                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                presentationsDAO.deletePresentation(adapter.getItem(info.position));
                removePresentation(info.position);
                return true;
        }
        return super.onContextItemSelected(item);
    }

    public void addPresentation(Presentation presentation) {
        presentations.add(presentation);
        adapter.notifyDataSetChanged();
    }

    public void removePresentation(int position) {
        presentations.remove(position);
        adapter.notifyDataSetChanged();
    }
}
