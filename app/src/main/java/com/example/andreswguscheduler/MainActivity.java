package com.example.andreswguscheduler;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.andreswguscheduler.Entities.Course;
import com.example.andreswguscheduler.Ui.CourseAdapter;
import com.example.andreswguscheduler.ViewModel.CourseViewModel;

import java.util.List;

import static com.example.andreswguscheduler.Utilities.Constants.ADD_TERM_REQUEST;

public class MainActivity extends AppCompatActivity {

    private CourseViewModel courseViewModel;

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

        final CourseAdapter adapter = new CourseAdapter();

        courseViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(CourseViewModel.class);
        courseViewModel.getAllCourses().observe(this, new Observer<List<Course>>() {
            @Override
            public void onChanged(@Nullable List<Course> courses) {

                //update recyclerview
                adapter.setmCourses(courses);

            }

        });

    }



}
