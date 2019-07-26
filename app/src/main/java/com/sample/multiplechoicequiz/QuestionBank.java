package com.sample.multiplechoicequiz;

// This file contains questions from QuestionBank

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public class QuestionBank {


    List<Question> questionList = new ArrayList<>();
    DataBaseHelper db;

    //get the list
    public List<Question> getQuestionList() {
        return questionList;
    }

    //set the list
    public void setQuestionList(List<Question> questionList) {
        this.questionList = questionList;
    }


    //get the length of the list
    public int getLength(){
        return questionList.size();
    }

    //gets the question
    public String getQuestion(int index){
        return questionList.get(index ).getQuestion();
    }

    //gets the choices of the questions
    public String getChoice(int index, int num){
        return questionList.get(index).getChoice(num-1);
    }

    //gets the correct answer of the question
    public String getAnswer(int index){
        return questionList.get(index).getAnswer();
    }

    public void initialiseQuestions(Context context) {
        DataBaseHelper db = new DataBaseHelper(context);
        //get questions/choices/answers from database
        questionList = db.getAllQuestions();

        //if list is empty, populate database with default questions/choices/answers
        if (questionList.isEmpty()) {
            db.addInitialQuestion(new Question("1. When did Google acquire Android ?", new String[]{"2001", "2003", "2004", "2005"}, "2005"));
            db.addInitialQuestion(new Question("2. What is the name of build toolkit for Android Studio?", new String[]{"JVM", "Gradle", "Dalvik", "HAXM"}, "Gradle"));
            db.addInitialQuestion(new Question("3. What widget can replace any use of radio buttons?", new String[]{"Toggle Button", "Spinner", "Switch Button", "ImageButton"}, "Spinner"));
            db.addInitialQuestion(new Question("4. What is a widget in Android app?", new String[]{"reusable GUI element", "Layout for Activity", "device placed in cans of beer", "build toolkit"}, "reusable GUI element"));
            //two new questions
            db.addInitialQuestion(new Question("5. Which company developed android?", new String[]{"Nokia", "Android Inc.", "Google", "Apple"}, "Android Inc."));
            db.addInitialQuestion(new Question("6. Android is based on which kernel?", new String[]{"Linux kernel", "Windows kernel", "MAC kernel", "Hybrid Kernel"}, "Linux kernel"));

            //get list from database again
            questionList = db.getAllQuestions();
        }
    }


}