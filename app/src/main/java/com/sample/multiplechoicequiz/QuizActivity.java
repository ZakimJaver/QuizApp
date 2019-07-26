package com.sample.multiplechoicequiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;


public class QuizActivity extends AppCompatActivity {

    private QuestionBank mQuestionLibrary = new QuestionBank();

    private TextView mScoreView;
    private TextView mQuestionView;
    private Button mButtonChoice1;
    private Button mButtonChoice2;
    private Button mButtonChoice3;
    private Button mButtonChoice4;

    private String mAnswer;  // correct answer for question in mQuestionView
    private int mScore = 0;  // current total score
    private int mQuestionNumber = 0; // current question number
    private int cNumber = 0;
    private int theme = 0;
    private int uid; //user id used to update score

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        // setup screen for the first question with four alternative to answer
        mScoreView = (TextView)findViewById(R.id.score);
        mQuestionView = (TextView)findViewById(R.id.question);
        mButtonChoice1 = (Button)findViewById(R.id.choice1);
        mButtonChoice2 = (Button)findViewById(R.id.choice2);
        mButtonChoice3 = (Button)findViewById(R.id.choice3);
        mButtonChoice4 = (Button)findViewById(R.id.choice4);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        Intent intent = getIntent();
        theme = intent.getIntExtra("theme", 0);
        uid = intent.getIntExtra("uid", 0);


        if(savedInstanceState != null){
            mQuestionLibrary.setQuestionList((ArrayList) savedInstanceState.getStringArrayList("questionBank"));
            mAnswer = savedInstanceState.getString("currentAnswer");
            mQuestionNumber = savedInstanceState.getInt("currentQuestion",0);
            cNumber = savedInstanceState.getInt("currentQuestion",0);
            mScore = savedInstanceState.getInt("currentScore", 0);
            theme = savedInstanceState.getInt("theme", 0);
            cNumber = savedInstanceState.getInt("currentQuestion",0);
            uid = savedInstanceState.getInt("uid",0);
        }else
            mQuestionLibrary.initialiseQuestions(getApplicationContext());

        changeTheme(theme);
        updateQuestion();
        // show current total score for the user
        updateScore(mScore);
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
            Intent intent = new Intent(QuizActivity.this, SignIn.class);
            intent.putExtra("theme", theme); // sends theme
            startActivity(intent);
            return super.onOptionsItemSelected(item);
        }

        if(item.getItemId() == R.id.hScore){
            Intent intent = new Intent(QuizActivity.this, ScoreTable.class);
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

    private void updateQuestion(){
        // check if we are not outside array bounds for questions
        if(mQuestionNumber<mQuestionLibrary.getLength()){
            // set the text for new question,
            // and new 4 alternative to answer on four buttons
            mQuestionView.setText(mQuestionLibrary.getQuestion(mQuestionNumber));
            mButtonChoice1.setText(mQuestionLibrary.getChoice(mQuestionNumber, 1));
            mButtonChoice2.setText(mQuestionLibrary.getChoice(mQuestionNumber, 2));
            mButtonChoice3.setText(mQuestionLibrary.getChoice(mQuestionNumber, 3));
            mButtonChoice4.setText(mQuestionLibrary.getChoice(mQuestionNumber,4));
            mAnswer = mQuestionLibrary.getAnswer(mQuestionNumber);
            mQuestionNumber= mQuestionNumber+1;
        }
        else {
            //Toast.makeText(QuizActivity.this, "It was the last question!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(QuizActivity.this, HighScore.class);
            intent.putExtra("score", mScore); // pass the current score to the second screen
            intent.putExtra("theme1", theme); // sends theme
            intent.putExtra("uid",uid);//sends the id of the user
            startActivity(intent);
        }
    }

    // show current total score for the user
    private void updateScore(int point) {
        mScoreView.setText(mScore+"/"+mQuestionLibrary.getLength());
    }

    public void onClick(View view) {
        //all logic for all answers buttons in one method
        Button answer = (Button) view;
        // if the answer is correct, increase the score
        if (answer.getText().equals(mAnswer)){
            mScore = mScore + 1;
            Toast.makeText(QuizActivity.this, "Correct!", Toast.LENGTH_SHORT).show();
        }else
            Toast.makeText(QuizActivity.this, "Wrong!", Toast.LENGTH_SHORT).show();

        cNumber++;
        // show current total score for the user
        updateScore(mScore);
        // once user answer the question, we move on to the next one, if any
        updateQuestion();
    }

    @Override
    public void onSaveInstanceState(Bundle save) {
        super.onSaveInstanceState(save);
        //saves the question list
        save.putStringArrayList("questionBank", (ArrayList) mQuestionLibrary.getQuestionList());
        //saves the current question
        save.putInt("currentQuestion", cNumber);

        //saves the current score
        save.putInt("currentScore", mScore);
        //saves current answer
        save.putString("currentAnswer", mAnswer);
        //saves the theme
        save.putInt("theme", theme);
        save.putInt("uid", uid);
    }

}

