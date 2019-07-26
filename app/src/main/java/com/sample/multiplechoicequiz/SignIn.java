package com.sample.multiplechoicequiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SignIn extends AppCompatActivity {

    private int theme;
    EditText mUserName, mPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        Toolbar mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        mUserName = findViewById(R.id.userName);
        mPassword = findViewById(R.id.password);

        Intent intent = getIntent();
        theme = intent.getIntExtra("theme", 0);
        if(savedInstanceState != null){
            theme = savedInstanceState.getInt("theme", 0);
        }
        changeTheme(theme);


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

            return super.onOptionsItemSelected(item);
        }
        if(item.getItemId() == R.id.hScore){
            Intent intent = new Intent(SignIn.this, ScoreTable.class);
            intent.putExtra("theme", theme); // sends theme
            startActivity(intent);
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

    public void logIn(View view) {
        DataBaseHelper db = new DataBaseHelper(getApplicationContext());
        List<Users> userList = new ArrayList<>();
        userList = db.getAllUser();
        boolean userFound = false;//checks if user is found (default not found)
        for (int i = 0; i < userList.size(); i++)
            if(mPassword.getText().toString().matches(userList.get(i).getPass()) && mUserName.getText().toString().matches(userList.get(i).getuName())){
                    Intent intent = new Intent(SignIn.this, QuizActivity.class);
                    intent.putExtra("theme", theme); //sending theme
                    intent.putExtra("uid", i);//the user id (where the user is located in the list)
                    startActivity(intent);
                userFound = true;//user found
            }

        if(!userFound)
            Toast.makeText(this, "Incorrect username and password", Toast.LENGTH_SHORT).show();
    }

    public void signUp(View view) {
        Intent intent = new Intent(SignIn.this, SignUp.class);
        intent.putExtra("theme", theme); //sending theme
        startActivity(intent);
    }
}
