package com.eman.comorgquizweek4practice;

import android.os.Bundle;
import android.view.View;
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

public class NormalModeActivity extends AppCompatActivity {

    ArrayList<Item> items;
    int questionNumber = -1;
    TextView textView_questionNumber;
    TextView textView_question;
    EditText editText_answer;
    Button button_submitAnswer;

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
        textView_questionNumber = findViewById(R.id.textview_question_number);
        textView_question = findViewById(R.id.textview_question);
        editText_answer = findViewById(R.id.edittext_answer);
        button_submitAnswer = findViewById(R.id.button_submit_answer);
        button_submitAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button_submitAnswer.setClickable(false);
                nextQuestion();
            }
        });
        importQuestions();
        nextQuestion();
    }

    private void importQuestions() {
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(this.getResources().openRawResource(R.raw.questions)))){
            while(reader.readLine() != null) {
                String question = reader.readLine();
                String answer = reader.readLine();
                items.add(new Item(question, answer));
            }
        }
        catch (Exception ignore) {
            //
        }
    }

    private void nextQuestion() {
        questionNumber++;
        if(questionNumber >= items.size()) {
            return;
        }
        String newQuestionNumber = "Question no." + (questionNumber + 1);
        String newQuestion = items.get(questionNumber).question;
        String newAnswer = items.get(questionNumber).answer;
        textView_questionNumber.setText(newQuestionNumber);
        textView_question.setText(newQuestion);
    }
}

class Item {
    String question;
    String answer;

    Item(String _question, String _answer) {
        question = _question;
        answer = _answer;
    }

}