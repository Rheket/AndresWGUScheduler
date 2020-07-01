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
import com.example.andreswguscheduler.Entities.Term;
import com.example.andreswguscheduler.Ui.CourseAdapter;
import com.example.andreswguscheduler.ViewModel.CourseViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import static com.example.andreswguscheduler.Utilities.Constants.ADD_COURSE_REQUEST;
import static com.example.andreswguscheduler.Utilities.Constants.EXTRA_COURSE;
import static com.example.andreswguscheduler.Utilities.Constants.EXTRA_COURSE_END_DATE;
import static com.example.andreswguscheduler.Utilities.Constants.EXTRA_COURSE_NOTES;
import static com.example.andreswguscheduler.Utilities.Constants.EXTRA_COURSE_START_DATE;
import static com.example.andreswguscheduler.Utilities.Constants.EXTRA_COURSE_TITLE;
import static com.example.andreswguscheduler.Utilities.Constants.EXTRA_COURSE_STATUS;
import static com.example.andreswguscheduler.Utilities.Constants.EXTRA_MENTOR_EMAIL;
import static com.example.andreswguscheduler.Utilities.Constants.EXTRA_MENTOR_NAME;
import static com.example.andreswguscheduler.Utilities.Constants.EXTRA_PHONE_NUMBER;
import static com.example.andreswguscheduler.Utilities.Constants.EXTRA_TERM;
import static com.example.andreswguscheduler.Utilities.Constants.EXTRA_TERM_ID;
import static com.example.andreswguscheduler.Utilities.Constants.VIEW_COURSE_REQUEST;

public class CourseListActivity extends AppCompatActivity {

    private CourseViewModel courseViewModel;
    private int termId;

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

        termId = courseViewModel.getTermId();

        courseViewModel.setTermId(termId);
        courseViewModel.getAllCourses(termId).observe(this, new Observer<List<Course>>() {
            @Override
            public void onChanged(List<Course> courses) {

                adapter.setmCourses(courses);

            }
        });

        adapter.setOnCourseListener(new CourseAdapter.OnCourseListener() {
            @Override
            public void onCourseClick(Course course) {

                Intent intent = new Intent(CourseListActivity.this, ViewCourseActivity.class);

                intent.putExtra(EXTRA_COURSE_TITLE, course.getCourseTitle());
                intent.putExtra(EXTRA_COURSE_START_DATE, course.getCourseStartDate());
                intent.putExtra(EXTRA_COURSE_END_DATE, course.getCourseAnticipatedEndDate());
                intent.putExtra(EXTRA_COURSE_STATUS, course.getCourseStatus());
                intent.putExtra(EXTRA_MENTOR_NAME, course.getCourseMentorName());
                intent.putExtra(EXTRA_MENTOR_EMAIL, course.getCourseMentorEmail());
                intent.putExtra(EXTRA_PHONE_NUMBER, course.getCourseMentorPhoneNumber());
                intent.putExtra(EXTRA_COURSE_NOTES, course.getCourseNotes());

                intent.putExtra(EXTRA_COURSE, course);

                //Intent termIdIntent = getIntent();

                //intent.putExtra(EXTRA_TERM, termIdIntent.getSerializableExtra(EXTRA_TERM));

                startActivityForResult(intent, VIEW_COURSE_REQUEST);

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_COURSE_REQUEST && resultCode == RESULT_OK) {


            int termId = courseViewModel.getTermId();
            String title = data.getStringExtra(EXTRA_COURSE_TITLE);
            String start = data.getStringExtra(EXTRA_COURSE_START_DATE);
            String end = data.getStringExtra(EXTRA_COURSE_END_DATE);
            String status = data.getStringExtra(EXTRA_COURSE_STATUS);
            String mentorName = data.getStringExtra(EXTRA_MENTOR_NAME);
            String mentorEmail = data.getStringExtra(EXTRA_MENTOR_EMAIL);
            String mentorPhone = data.getStringExtra(EXTRA_PHONE_NUMBER);

            Course course = new Course(termId, title, start, end, status, mentorName, mentorEmail, mentorPhone);

            courseViewModel.insertCourse(course);

            Toast.makeText(this, "Course Added", Toast.LENGTH_SHORT).show();


        } else {
            return;
        }

    }
}