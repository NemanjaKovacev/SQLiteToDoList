package com.example.nemanja.sqlitetodolist;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nemanja.sqlitetodolist.adapters.Adapter;
import com.example.nemanja.sqlitetodolist.interfaces.ItemClickListener;
import com.example.nemanja.sqlitetodolist.helpers.SQLiteManager;
import com.example.nemanja.sqlitetodolist.models.Item;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, ItemClickListener {

    private EditText editTextItem;
    private Button buttonSave;
    private Button buttonEdit;
    private Button buttonCancel;
    private RecyclerView recyclerView;
    private List<Item> itemsList;
    private FloatingActionButton fab;
    private Adapter adapter;
    private SQLiteManager databaseManager;
    private static final int EDIT = 0;
    private static final int DELETE = 1;
    private static final int CANCEL = 2;
    private int edit_id = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editTextItem = (EditText) findViewById(R.id.editTextItem);
        buttonSave = (Button) findViewById(R.id.buttonSave);
        buttonEdit = (Button) findViewById(R.id.buttonEdit);
        buttonCancel = (Button) findViewById(R.id.buttonCancel);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        itemsList = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        databaseManager = new SQLiteManager(this);
        databaseManager.open();
        itemsList = databaseManager.getAllItems();
        if (itemsList.size() > 0) {
            adapter = new Adapter(this, itemsList, this);
            recyclerView.setAdapter(adapter);
        }
        databaseManager.close();

        buttonSave.setOnClickListener(this);
        buttonEdit.setOnClickListener(this);
        buttonCancel.setOnClickListener(this);

        fab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                editTextItem.setVisibility(View.VISIBLE);
                buttonSave.setVisibility(View.VISIBLE);
                buttonEdit.setVisibility(View.VISIBLE);
                buttonCancel.setVisibility(View.VISIBLE);
                fab.setVisibility(View.INVISIBLE);
            }
        });
    }

    @Override
    public void onClick(View view) {

        if (view == buttonSave) {
            InputMethodManager imm = (InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
            String title = editTextItem.getText().toString().trim();
            if (!title.isEmpty()) {
                fab.setVisibility(View.VISIBLE);
                editTextItem.setVisibility(View.GONE);
                buttonSave.setVisibility(View.GONE);
                buttonEdit.setVisibility(View.GONE);
                buttonCancel.setVisibility(View.GONE);
                Item item = new Item();
                item.title = title;
                databaseManager.open();
                databaseManager.insertItem(item);
                databaseManager.close();

                databaseManager.open();
                itemsList.clear();
                itemsList = databaseManager.getAllItems();
                databaseManager.close();

                if (itemsList.size() > 0) {
                    adapter = new Adapter(this, itemsList, this);
                    recyclerView.setAdapter(adapter);
                }
                editTextItem.setText("");

            } else {
                Toast.makeText(MainActivity.this, R.string.enter_item, Toast.LENGTH_LONG).show();
            }
        } else if (view == buttonEdit) {
            InputMethodManager imm = (InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
            String title = editTextItem.getText().toString().trim();
            if (!title.isEmpty()) {
                fab.setVisibility(View.VISIBLE);
                editTextItem.setVisibility(View.GONE);
                buttonSave.setVisibility(View.GONE);
                buttonEdit.setVisibility(View.GONE);
                buttonCancel.setVisibility(View.GONE);
                Item item = new Item();
                item.title = title;
                databaseManager.open();
                databaseManager.update(item, edit_id);
                itemsList.clear();
                itemsList = databaseManager.getAllItems();
                databaseManager.close();

                if (itemsList.size() > 0) {
                    adapter = new Adapter(this, itemsList, this);
                    recyclerView.setAdapter(adapter);
                }
                adapter.notifyDataSetChanged();
                editTextItem.setText("");
            } else {
                Toast.makeText(MainActivity.this, R.string.enter_item, Toast.LENGTH_LONG).show();
            }
        } else if (view == buttonCancel) {
            InputMethodManager imm = (InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(buttonCancel.getWindowToken(), 0);
            fab.setVisibility(View.VISIBLE);
            editTextItem.setVisibility(View.GONE);
            buttonSave.setVisibility(View.GONE);
            buttonEdit.setVisibility(View.GONE);
            buttonCancel.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(int id, int TAG, String title) {
        if (TAG == EDIT) {
            editTextItem.setVisibility(View.VISIBLE);
            buttonSave.setVisibility(View.VISIBLE);
            buttonEdit.setVisibility(View.VISIBLE);
            buttonCancel.setVisibility(View.VISIBLE);
            fab.setVisibility(View.INVISIBLE);
            edit_id = id;
            editTextItem.setText(title);
        }

        if (TAG == DELETE) {
            databaseManager.open();
            databaseManager.remove(id);
            itemsList.clear();
            itemsList = databaseManager.getAllItems();
            databaseManager.close();

            if (itemsList.size() > 0) {
                adapter = new Adapter(this, itemsList, this);
                recyclerView.setAdapter(adapter);
            }
            adapter.notifyDataSetChanged();
        }

        if (TAG == CANCEL) {
            InputMethodManager imm = (InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(buttonCancel.getWindowToken(), 0);
        }
    }
}
