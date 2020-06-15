package com.example.andreswguscheduler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.andreswguscheduler.Entities.Course;
import com.example.andreswguscheduler.Ui.CourseAdapter;
import com.example.andreswguscheduler.ViewModel.CourseViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import static com.example.andreswguscheduler.Utilities.Constants.ADD_COURSE_REQUEST;
import static com.example.andreswguscheduler.Utilities.Constants.VIEW_COURSE_REQUEST;

public class CourseListActivity extends AppCompatActivity {

    private CourseViewModel courseViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_list);

        FloatingActionButton buttonAddCourse = findViewById(R.id.button_add_course);

        buttonAddCourse.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CourseListActivity.this, AddCourseActivity.class);
                startActivityForResult(intent, ADD_COURSE_REQUEST);
            }
        });

        final RecyclerView recyclerView = findViewById(R.id.course_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final CourseAdapter adapter = new CourseAdapter();
        recyclerView.setAdapter(adapter);

        courseViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(CourseViewModel.class);
        courseViewModel.getAllCourses().observe(this, new Observer<List<Course>>() {
            @Override
            public void onChanged(List<Course> courses) {

                adapter.setmCourses(courses);

            }
        });

        adapter.setOnCourseListener(new CourseAdapter.OnCourseListener() {
            @Override
            public void onCourseClick(Course course) {

                Intent intent = new Intent(CourseListActivity.this, ViewCourseActivity.class);

                //@fixme intent.putextra goes here

                startActivityForResult(intent, VIEW_COURSE_REQUEST);



            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_COURSE_REQUEST && resultCode == RESULT_OK) {



        } else {
            Toast.makeText(this, "Course not saved", Toast.LENGTH_SHORT).show();
        }

    }
}