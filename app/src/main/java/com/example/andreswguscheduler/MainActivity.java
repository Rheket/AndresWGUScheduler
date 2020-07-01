package com.example.andreswguscheduler;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import static com.example.andreswguscheduler.Utilities.Constants.ADD_TERM_REQUEST;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonViewAllTerms = findViewById(R.id.view_all_terms);

        buttonViewAllTerms.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, TermListActivity.class);
                startActivityForResult(intent, ADD_TERM_REQUEST);

            }
        });



    }



}
