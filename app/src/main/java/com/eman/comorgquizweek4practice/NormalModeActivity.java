package com.eman.comorgquizweek4practice;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.FileWriter;

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
        try(FileWriter writer = new FileWriter("test.txt")){
            writer.write("test");
        }
        catch (Exception e) {
            //
        }
    }

    private void importQuestion() {

    }

    private void nextQuestion() {

    }
}