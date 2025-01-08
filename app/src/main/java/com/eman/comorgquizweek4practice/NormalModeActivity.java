package com.eman.comorgquizweek4practice;

import android.os.Bundle;
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

public class NormalModeActivity extends AppCompatActivity {

    int questionNumber = 0;
    TextView textView_questionNumber;
    TextView textView_question;
    EditText editText_answer;

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
        questionNumber = 0;
        textView_questionNumber = findViewById(R.id.textview_question_number);
        textView_question = findViewById(R.id.textview_question);
        editText_answer = findViewById(R.id.edittext_answer);
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(this.getResources().openRawResource(R.raw.questions)))){
            textView_question.setText(reader.readLine());
        }
        catch (Exception e) {
            textView_question.setText(e.getMessage());
        }
    }

    private void importQuestion() {

    }

    private void nextQuestion() {

    }
}