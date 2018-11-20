package com.example.professor.pratico2;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ActionMode;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, ActionMode.Callback, ItemDialog.OnItemListener {

    private ListView lista;
    private ItemAdapter adapter;
    private int selectedItem;
    private String selectedItemName;
    private boolean insertMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ItemDialog dialog = new ItemDialog();
                dialog.show(getSupportFragmentManager(), "itemDialog");
                insertMode = true;
            }
        });

        lista = findViewById(R.id.lista);
        adapter = new ItemAdapter(this);
        lista.setAdapter(adapter);
        lista.setOnItemClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        selectedItem = position;
        selectedItemName = adapter.getItem(position);
        view.setBackgroundColor(Color.LTGRAY);
        startActionMode(this);
    }

    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        getMenuInflater().inflate(R.menu.context_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        return false;
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_delete) {
            adapter.removeItem(selectedItem);
            mode.finish();
            return true;

        } else if (id == R.id.action_edit) {
            ItemDialog dialog = new ItemDialog();
            dialog.setItem(selectedItemName);
            dialog.show(getSupportFragmentManager(), "itemDialog");
            insertMode = false;
            mode.finish();
            return true;

        } else {
            return false;
        }
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {
        View view = lista.getChildAt(selectedItem);
        view.setBackgroundColor(Color.TRANSPARENT);
    }

    @Override
    public void onItem(String item) {
        if (insertMode) {
            adapter.insertItem(item);
        } else {
            adapter.updateItem(selectedItem, item);
        }
    }
}
