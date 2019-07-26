package com.sample.multiplechoicequiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SignUp extends AppCompatActivity {

    EditText mUserName, mPassword;
    private int theme;
    int counter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Toolbar mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        Intent intent = getIntent();
        theme = intent.getIntExtra("theme", 0);

        mUserName = findViewById(R.id.userName);
        mPassword = findViewById(R.id.password);

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
            Intent intent = new Intent(SignUp.this, SignIn.class);
            intent.putExtra("theme", theme); // sends theme
            startActivity(intent);
            return super.onOptionsItemSelected(item);
        }

        if(item.getItemId() == R.id.hScore){
            Intent intent = new Intent(SignUp.this, ScoreTable.class);
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

    public void signUp(View view) {

        if (mPassword.getText().toString().matches("") || mUserName.getText().toString().matches(""))
            Toast.makeText(this,"Please enter User name and password",Toast.LENGTH_SHORT).show();
        else {
            Users user = new Users(mUserName.getText().toString(), mPassword.getText().toString());
            DataBaseHelper db = new DataBaseHelper(getApplicationContext());
            db.addUser(user);
            Toast.makeText(this, "Successfully created " , Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(SignUp.this, SignIn.class);
            intent.putExtra("theme", theme); // sends theme
            startActivity(intent);
        }

    }
}
