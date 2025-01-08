package com.eman.comorgquizweek4practice;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

public class RandomizedModeActivity extends AppCompatActivity {

    ArrayList<Item> items;
    int questionNumber = -1;
    int score;
    int total;
    TextView textView_questionNumber;
    TextView textView_question;
    EditText editText_answer;
    Button button_submitAnswer;
    TextView textView_displayQuestionResult;
    TextView textView_yourAnswer;
    TextView textView_correctAnswer;
    Button button_nextQuestion;
    Dialog dialog_results;
    TextView textView_dialogMode;
    TextView textView_dialogScore;
    Button button_dialogTryAgain;
    Button button_dialogHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_normal_mode);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        items = new ArrayList<>();
        questionNumber = -1;
        score = 0;
        total = 0;
        textView_questionNumber = findViewById(R.id.textview_question_number);
        textView_question = findViewById(R.id.textview_question);
        editText_answer = findViewById(R.id.edittext_answer);
        button_submitAnswer = findViewById(R.id.button_submit_answer);
        button_submitAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer();
            }
        });
        textView_displayQuestionResult = findViewById(R.id.textview_displayquestionresult);
        textView_yourAnswer = findViewById(R.id.textview_youranswer);
        textView_correctAnswer = findViewById(R.id.textview_correctanswer);
        button_nextQuestion = findViewById(R.id.button_next_question);
        button_nextQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextQuestion();
            }
        });
        dialog_results = new Dialog(RandomizedModeActivity.this);
        dialog_results.setContentView(R.layout.quiz_result);
        dialog_results.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog_results.getWindow().setBackgroundDrawable(getDrawable(R.drawable.dialog_background));
        dialog_results.setCancelable(false);
        textView_dialogMode = dialog_results.findViewById(R.id.textview_mode);
        textView_dialogScore = dialog_results.findViewById(R.id.textview_score);
        button_dialogTryAgain = dialog_results.findViewById(R.id.button_tryAgain);
        button_dialogTryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_results.dismiss();
                Intent intent = new Intent(RandomizedModeActivity.this, RandomizedModeActivity.class);
                finish();
                startActivity(intent);
            }
        });
        button_dialogHome = dialog_results.findViewById(R.id.button_home);
        button_dialogHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_results.dismiss();
                finish();
            }
        });
        importQuestions();
        shuffleItems();
        nextQuestion();
    }

    private void importQuestions() {
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(this.getResources().openRawResource(R.raw.questions)))){
            while(reader.readLine() != null) {
                String question = reader.readLine();
                String answer = reader.readLine();
                items.add(new Item(question, answer));
                total++;
            }
        }
        catch (Exception ignore) {
            //
        }
    }

    private void nextQuestion() {
        button_submitAnswer.setClickable(true);
        button_nextQuestion.setVisibility(View.INVISIBLE);
        textView_displayQuestionResult.setVisibility(View.INVISIBLE);
        textView_yourAnswer.setVisibility(View.INVISIBLE);
        textView_correctAnswer.setVisibility(View.INVISIBLE);
        questionNumber++;
        if(questionNumber >= items.size()) {
            showResults();
            return;
        }
        String newQuestionNumber = "Question no." + (questionNumber + 1);
        String newQuestion = items.get(questionNumber).question;
        textView_questionNumber.setText(newQuestionNumber);
        textView_question.setText(newQuestion);
        editText_answer.setText("");
    }

    private void checkAnswer() {
        String correct = "Correct!";
        String wrong = "Wrong :(";
        String displayYourAnswer = "Your Answer: " + editText_answer.getText().toString();
        String displayCorrectAnswer = "Correct Answer: " + items.get(questionNumber).answer;
        button_submitAnswer.setClickable(false);
        button_nextQuestion.setVisibility(View.VISIBLE);
        textView_displayQuestionResult.setVisibility(View.VISIBLE);
        String entered_answer = editText_answer.getText().toString().toLowerCase().trim();
        String correct_answer = items.get(questionNumber).answer.toLowerCase().trim();
        if(entered_answer.equals(correct_answer)) {
            score++;
            textView_displayQuestionResult.setText(correct);
        }
        else {
            textView_displayQuestionResult.setText(wrong);
            textView_yourAnswer.setVisibility(View.VISIBLE);
            textView_correctAnswer.setVisibility(View.VISIBLE);
            textView_yourAnswer.setText(displayYourAnswer);
            textView_correctAnswer.setText(displayCorrectAnswer);
        }
    }

    private void showResults() {
        String displayMode = "Mode: Randomized";
        String displayScore = score + "/" + total;
        textView_dialogMode.setText(displayMode);
        textView_dialogScore.setText(displayScore);
        dialog_results.show();
    }

    private void shuffleItems() {
        for(int i = 0; i < items.size(); i++) {
            Random random = new Random();
            int num1 = random.nextInt(items.size());
            int num2 = random.nextInt(items.size());
            Item tempItem = items.get(num1);
            items.set(num1, items.get(num2));
            items.set(num2, tempItem);
        }
    }
}