package com.sample.multiplechoicequiz;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class HighScore extends AppCompatActivity {

    TextView mCurrentScore, mHighestScore;
    ImageView mImage;

    // Database table name
    private static final String HIGHSCORE = "highScore";

    private int theme;
    private int uid; //user id used to update score
    private int hScore;

    private boolean highScoreImg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score);

        mCurrentScore = findViewById(R.id.textScore);
        mHighestScore = findViewById(R.id.textHighScore);
        mImage = findViewById(R.id.image);

        //gets all the users in the database
        DataBaseHelper db = new DataBaseHelper(getApplicationContext());
        List<Users> userList = new ArrayList<>();
        userList = db.getAllUser();

        if(savedInstanceState != null){
            theme = savedInstanceState.getInt("theme", 0);
            highScoreImg = savedInstanceState.getBoolean("highScore");
            uid = savedInstanceState.getInt("uid",0);
            //hScore = savedInstanceState.getInt("hScore",0);
            if(highScoreImg)
                mImage.setVisibility(View.VISIBLE);

        }


        //mImage.setVisibility(View.INVISIBLE);
        //receive the score from the last activity
        Intent intent = getIntent();
        int cScore = intent.getIntExtra("score",0);
        mCurrentScore.setText("Your Score: " + cScore);
        uid = intent.getIntExtra("uid",0);//sets the user id
        theme = intent.getIntExtra("theme1", 0);
        changeTheme(theme);

        //not needed anymore
        //SharedPreferences myPref = getPreferences(MODE_PRIVATE);
        //int hScore = myPref.getInt("hScore",0);
        hScore = userList.get(uid).gethScore();

        if(hScore < cScore && cScore != 0){


            //change the high score
            //update the database
            db.updateScore(uid, cScore);
            userList.clear();
            userList = db.getAllUser();//get updated users

            hScore = userList.get(uid).gethScore();//set the new hScore

            mHighestScore.setText("Highest Score: " + cScore);
            mImage.setVisibility(View.VISIBLE);
            highScoreImg = true;
            Toast.makeText(this,"Congratulations " + userList.get(uid).getuName() +"! Your high score has been updated", Toast.LENGTH_SHORT).show();
        }else
            mHighestScore.setText("Highest Score: " + hScore);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);


        changeTheme(theme);
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

    //toolbar settings
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mMenuInflater = getMenuInflater();
        mMenuInflater.inflate(R.menu.my_menu, menu);
        return true;
    }

    //toolbar actions

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.logOut){
            Intent intent = new Intent(HighScore.this, SignIn.class);
            intent.putExtra("theme", theme); // sends theme
            startActivity(intent);
            return super.onOptionsItemSelected(item);
        }

        if(item.getItemId() == R.id.hScore){
            Intent intent = new Intent(HighScore.this, ScoreTable.class);
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

    public void tryAgain(View view) {
        Intent intent = new Intent(HighScore.this, QuizActivity.class);
        intent.putExtra("theme", theme); //sending theme
        intent.putExtra("uid",uid);
        startActivity(intent);
    }

    public void clear(View view) {
        DataBaseHelper db = new DataBaseHelper(getApplicationContext());
        db.updateScore(uid, 0);
        Toast toast = Toast.makeText(HighScore.this, "The High Score has been reset!", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 0);
        toast.show();
        mHighestScore.setText("Highest Score: 0");
    }

    @Override
    public void onSaveInstanceState(Bundle save) {
        super.onSaveInstanceState(save);
        //saves the theme
        save.putInt("theme", theme);
        //saves the visibility of the image
        save.putBoolean("highScore", highScoreImg);
        save.putInt("uid",uid);
        save.putInt("hScore", hScore);
    }

}
