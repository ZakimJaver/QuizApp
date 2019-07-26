package com.sample.multiplechoicequiz;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ScoreTable extends AppCompatActivity {

    private int theme;

    private ListView mListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_table);


        Toolbar mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        mListView = findViewById(R.id.listView);
        populateList();
        if(savedInstanceState != null){
            theme = savedInstanceState.getInt("theme", 0);
        }
        changeTheme(theme);
    }

    private void populateList() {
        DataBaseHelper db = new DataBaseHelper(getApplicationContext());
        List<Users> userList = new ArrayList<>();
        userList = db.getAllUser();

        //sorts the list
        Collections.sort(userList, new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                Users p1 = (Users) o1;
                Users p2 = (Users) o2;
                return Integer.valueOf(p2.gethScore()).compareTo(p1.gethScore());
            }
        });

        UsersListAdapter adapter = new UsersListAdapter(this, R.layout.adapter_view_layout, userList);
        mListView.setAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mMenuInflater = getMenuInflater();
        mMenuInflater.inflate(R.menu.my_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.logOut){
            Intent intent = new Intent(ScoreTable.this, SignIn.class);
            intent.putExtra("theme", theme); // sends theme
            startActivity(intent);
            return super.onOptionsItemSelected(item);
        }

        if(item.getItemId() == R.id.hScore){
            return super.onOptionsItemSelected(item);
        }

        if(item.getItemId() == R.id.action_default)
            theme = 0;

        if(item.getItemId() == R.id.action_red_theme)
            theme = 1;

        if(item.getItemId() == R.id.action_green_theme)
            theme = 2;

        changeTheme(theme);

        Toast.makeText(this,"Theme Changed", Toast.LENGTH_SHORT).show();
        return super.onOptionsItemSelected(item);
    }


    private void changeTheme(int c){
        View view = this.getWindow().getDecorView();
        switch (c){
            case 0:
                view.setBackgroundResource(R.color.Default);
                break;
            case 1:
                view.setBackgroundResource(R.color.red);
                break;
            case 2:
                view.setBackgroundResource(R.color.green);
                break;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle save) {
        super.onSaveInstanceState(save);
        //saves the theme
        save.putInt("theme", theme);
    }
}


