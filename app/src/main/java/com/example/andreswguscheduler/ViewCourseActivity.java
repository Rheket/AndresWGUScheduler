package com.example.andreswguscheduler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.andreswguscheduler.Entities.Course;
import com.example.andreswguscheduler.ViewModel.AssessmentViewModel;
import com.example.andreswguscheduler.ViewModel.CourseViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static com.example.andreswguscheduler.Utilities.Constants.ADD_ASSESSMENT_REQUEST;
import static com.example.andreswguscheduler.Utilities.Constants.ADD_NOTES_REQUEST;
import static com.example.andreswguscheduler.Utilities.Constants.EDIT_COURSE_REQUEST;
import static com.example.andreswguscheduler.Utilities.Constants.EXTRA_COURSE;
import static com.example.andreswguscheduler.Utilities.Constants.EXTRA_COURSE_END_DATE;
import static com.example.andreswguscheduler.Utilities.Constants.EXTRA_COURSE_ID;
import static com.example.andreswguscheduler.Utilities.Constants.EXTRA_COURSE_START_DATE;
import static com.example.andreswguscheduler.Utilities.Constants.EXTRA_COURSE_STATUS;
import static com.example.andreswguscheduler.Utilities.Constants.EXTRA_COURSE_TITLE;
import static com.example.andreswguscheduler.Utilities.Constants.EXTRA_MENTOR_EMAIL;
import static com.example.andreswguscheduler.Utilities.Constants.EXTRA_MENTOR_NAME;
import static com.example.andreswguscheduler.Utilities.Constants.EXTRA_PHONE_NUMBER;
import static com.example.andreswguscheduler.Utilities.Constants.EXTRA_TERM;

public class ViewCourseActivity extends AppCompatActivity {

    private CourseViewModel courseViewModel;
    private AssessmentViewModel assessmentViewModel;

    private Course course;

    private TextView courseTitle;
    private TextView courseStart;
    private TextView courseEnd;
    private TextView courseStatus;
    private TextView courseMentorName;
    private TextView courseMentorEmail;
    private TextView courseMentorPhone;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_course);

        assessmentViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(AssessmentViewModel.class);
        courseViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(CourseViewModel.class);

        courseTitle = findViewById(R.id.text_view_course_title);
        courseStart = findViewById(R.id.text_view_course_start);
        courseEnd = findViewById(R.id.text_view_course_end);
        courseStatus = findViewById(R.id.text_view_course_status);
        courseMentorName = findViewById(R.id.text_view_course_mentor_name);
        courseMentorEmail = findViewById(R.id.text_view_course_mentor_email);
        courseMentorPhone = findViewById(R.id.text_view_course_mentor_number);

        Intent intent = getIntent();

        course = (Course)intent.getSerializableExtra(EXTRA_COURSE);

        courseTitle.setText(intent.getStringExtra(EXTRA_COURSE_TITLE));
        courseStart.setText(intent.getStringExtra(EXTRA_COURSE_START_DATE));
        courseEnd.setText(intent.getStringExtra(EXTRA_COURSE_END_DATE));
        courseStatus.setText(intent.getStringExtra(EXTRA_COURSE_STATUS));
        courseMentorName.setText(intent.getStringExtra(EXTRA_MENTOR_NAME));
        courseMentorEmail.setText(intent.getStringExtra(EXTRA_MENTOR_EMAIL));
        courseMentorPhone.setText(intent.getStringExtra(EXTRA_PHONE_NUMBER));

        Button buttonViewAssessments = findViewById(R.id.button_view_assessments_list);

        buttonViewAssessments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int cId = course.getCourseId();
                assessmentViewModel.setCourseId(cId);

                Intent i = new Intent(ViewCourseActivity.this, AssessmentListActivity.class);

                startActivityForResult(i, ADD_ASSESSMENT_REQUEST);

            }
        });

        Button buttonViewNotes = findViewById(R.id.button_view_notes);

        buttonViewNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent courseIdIntent = getIntent();
                Intent i = new Intent(ViewCourseActivity.this, NotesActivity.class);

                i.putExtra(EXTRA_COURSE, courseIdIntent.getSerializableExtra(EXTRA_COURSE));
                startActivityForResult(i, ADD_NOTES_REQUEST);

            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.course_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        Intent intent = getIntent();
        Intent delIntent = new Intent(ViewCourseActivity.this, CourseListActivity.class);

        course = (Course)intent.getSerializableExtra(EXTRA_COURSE);

        switch (item.getItemId()) {

            case R.id.action_delete:


                courseViewModel.deleteCourse(course);
                Toast.makeText(this, "Course Deleted", Toast.LENGTH_SHORT).show();
                startActivity(delIntent);
                return true;

            case R.id.action_edit:

                 Intent editIntent = new Intent(ViewCourseActivity.this, EditCourseActivity.class);

                 editIntent.putExtra(EXTRA_COURSE, intent.getSerializableExtra(EXTRA_COURSE));

                 editIntent.putExtra(EXTRA_COURSE_ID, course.getCourseId());
                 editIntent.putExtra(EXTRA_COURSE_TITLE, course.getCourseTitle());
                 editIntent.putExtra(EXTRA_COURSE_START_DATE, course.getCourseStartDate());
                 editIntent.putExtra(EXTRA_COURSE_END_DATE, course.getCourseAnticipatedEndDate());
                 editIntent.putExtra(EXTRA_COURSE_STATUS, course.getCourseStatus());
                 editIntent.putExtra(EXTRA_MENTOR_NAME, course.getCourseMentorName());
                 editIntent.putExtra(EXTRA_MENTOR_EMAIL, course.getCourseMentorEmail());
                 editIntent.putExtra(EXTRA_PHONE_NUMBER, course.getCourseMentorPhoneNumber());

                 Intent termIdIntent = getIntent();

                 editIntent.putExtra(EXTRA_TERM, termIdIntent.getSerializableExtra(EXTRA_TERM));

                 startActivityForResult(editIntent, EDIT_COURSE_REQUEST);

                 Toast.makeText(this, "Editing Course", Toast.LENGTH_SHORT).show();

                 return true;

            case R.id.action_start_alert:

                String start = course.getCourseStartDate();

                try {

                    Date date = sdf.parse(start);

                    final Calendar calStart = Calendar.getInstance();
                    calStart.setTime(date);

                    Intent alertIntent = new Intent(ViewCourseActivity.this, MyReceiver.class);
                    alertIntent.putExtra("key", course.getCourseTitle() + " Starts Today");
                    PendingIntent sender = PendingIntent.getBroadcast(ViewCourseActivity.this, 1, alertIntent, 0);

                    AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
                    Long startMillis = calStart.getTimeInMillis();

                    alarmManager.set(AlarmManager.RTC_WAKEUP, startMillis, sender);

                } catch (ParseException e) {
                    e.printStackTrace();
                }

                return true;

            case R.id.action_end_alert:

                String end = course.getCourseAnticipatedEndDate();

                try {

                    Date date = sdf.parse(end);

                    final Calendar calEnd = Calendar.getInstance();
                    calEnd.setTime(date);

                    Intent alertIntent = new Intent(ViewCourseActivity.this, MyReceiver.class);
                    alertIntent.putExtra("key", course.getCourseTitle() + " Ends Today");
                    PendingIntent sender = PendingIntent.getBroadcast(ViewCourseActivity.this, 2, alertIntent, 0);

                    AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
                    Long endMillis = calEnd.getTimeInMillis();

                    alarmManager.set(AlarmManager.RTC_WAKEUP, endMillis, sender);

                } catch (ParseException e) {
                    e.printStackTrace();
                }

                return true;

            default:

                return super.onOptionsItemSelected(item);

        }

    }
}