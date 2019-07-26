package com.sample.multiplechoicequiz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {

    // Database name
    public static String DATABASE_NAME = "userQuestionBank.db";
    // Current version of database
    private static final int DATABASE_VERSION = 1;

    // All fields used in database table
    private static final String KEY_ID = "id";

    //questions
    // Database table name
    public static final String TABLE_NAME_QUESTION = "QuestionBank";
    private static final String QUESTION = "question";
    private static final String CHOICE1 = "choice1";
    private static final String CHOICE2 = "choice2";
    private static final String CHOICE3 = "choice3";
    private static final String CHOICE4 = "choice4";
    private static final String ANSWER = "answer";

    //users
    // Database table name
    public static final String TABLE_NAME_USER = "UsersBank";
    private static final String USER = "userName";
    private static final String PASSWORD = "passwprd";
    private static final String HIGHSCORE = "highScore";

    // Question Table Create Query in this string
    private static final String CREATE_TABLE_QUESTION = "CREATE TABLE " +
            TABLE_NAME_QUESTION + "(" +
            KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            QUESTION + " TEXT," +
            CHOICE1 + " TEXT, " +
            CHOICE2 + " TEXT, " +
            CHOICE3 + " TEXT, " +
            CHOICE4 + " TEXT, " +
            ANSWER + " TEXT);";

    // User Table Create Query in this string
    private static final String CREATE_TABLE_USER = "CREATE TABLE " +
            TABLE_NAME_USER + "(" +
            KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            USER + " TEXT," +
            PASSWORD + " TEXT, " +
            HIGHSCORE + " INTEGER);";


    public DataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //creates the questions
        db.execSQL(CREATE_TABLE_USER);
        db.execSQL(CREATE_TABLE_QUESTION);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_QUESTION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_USER);
        onCreate(db);
    }


    //QUESTION METHODS
    public long addInitialQuestion(Question question){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(QUESTION, question.getQuestion());
        values.put(CHOICE1, question.getChoice(0));
        values.put(CHOICE2, question.getChoice(1));
        values.put(CHOICE3, question.getChoice(2));
        values.put(CHOICE4, question.getChoice(3));
        values.put(ANSWER, question.getAnswer());

        long insert = db.insert(TABLE_NAME_QUESTION, null, values);
        return insert;
    }

    public List<Question> getAllQuestions(){
        List<Question> questionList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_NAME_QUESTION;
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all records and adding to the list
        if (c.moveToFirst()) {
            do {
                Question question = new Question();

                //setting all the question data
                question.setQuestion(c.getString(c.getColumnIndex(QUESTION)));
                question.setChoice(0, c.getString(c.getColumnIndex(CHOICE1)));
                question.setChoice(1,c.getString(c.getColumnIndex(CHOICE2)));
                question.setChoice(2,c.getString(c.getColumnIndex(CHOICE3)));
                question.setChoice(3,c.getString(c.getColumnIndex(CHOICE4)));
                question.setAnswer(c.getString(c.getColumnIndex(ANSWER)));

                // adding to Questions list
                questionList.add(question);
            } while (c.moveToNext());
            Collections.shuffle(questionList);
        }
        return questionList;
    }


    //USER METHODS
    public long addUser(Users user){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(USER, user.getuName());
        values.put(PASSWORD, user.getPass());
        values.put(HIGHSCORE, user.gethScore());

        long insert = db.insert(TABLE_NAME_USER, null, values);
        return insert;
    }

    public List<Users> getAllUser(){
        List<Users> userList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_NAME_USER;
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all records and adding to the list
        if (c.moveToFirst()) {
            do {
                //setting all the user data
                Users user = new Users(
                        c.getString(c.getColumnIndex(USER)),
                        c.getString(c.getColumnIndex(PASSWORD)),
                        c.getInt(c.getColumnIndex(HIGHSCORE)));
                // adding to User list
                userList.add(user);
            } while (c.moveToNext());
        }
        c.close();
        return userList;
    }

    public void updateScore(int id, int score){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(HIGHSCORE, score);
        db.update(TABLE_NAME_USER, cv, KEY_ID + " = ?", new String[] { String.valueOf(id+1)});
    }


}
