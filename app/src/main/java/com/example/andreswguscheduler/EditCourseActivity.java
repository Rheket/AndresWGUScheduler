package com.example.andreswguscheduler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.andreswguscheduler.Entities.Course;
import com.example.andreswguscheduler.Entities.Term;
import com.example.andreswguscheduler.ViewModel.CourseViewModel;
import com.example.andreswguscheduler.ViewModel.TermViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static com.example.andreswguscheduler.Utilities.Constants.EDIT_COURSE_REQUEST;
import static com.example.andreswguscheduler.Utilities.Constants.EXTRA_COURSE;
import static com.example.andreswguscheduler.Utilities.Constants.EXTRA_COURSE_END_DATE;
import static com.example.andreswguscheduler.Utilities.Constants.EXTRA_COURSE_ID;
import static com.example.andreswguscheduler.Utilities.Constants.EXTRA_COURSE_START_DATE;
import static com.example.andreswguscheduler.Utilities.Constants.EXTRA_COURSE_STATUS;
import static com.example.andreswguscheduler.Utilities.Constants.EXTRA_COURSE_TERM_ID;
import static com.example.andreswguscheduler.Utilities.Constants.EXTRA_COURSE_TITLE;
import static com.example.andreswguscheduler.Utilities.Constants.EXTRA_MENTOR_EMAIL;
import static com.example.andreswguscheduler.Utilities.Constants.EXTRA_MENTOR_NAME;
import static com.example.andreswguscheduler.Utilities.Constants.EXTRA_PHONE_NUMBER;
import static com.example.andreswguscheduler.Utilities.Constants.EXTRA_TERM;

public class EditCourseActivity extends AppCompatActivity {

    private EditText editTextCourseTitle;
    private DatePicker datePickerCourseStart;
    private DatePicker datePickerCourseEnd;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private EditText editTextMentorName;
    private EditText editTextMentorEmail;
    private EditText editTextMentorPhone;

    private CourseViewModel courseViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_course);

        Intent intent = getIntent();

        editTextCourseTitle = findViewById(R.id.edit_course_title);
        datePickerCourseStart = findViewById(R.id.edit_date_picker_course_start);
        datePickerCourseEnd = findViewById(R.id.edit_date_picker_course_end);
        radioGroup = findViewById(R.id.edit_radio_group_course_status);
        editTextMentorName = findViewById(R.id.edit_mentor_name);
        editTextMentorEmail = findViewById(R.id.edit_mentor_email);
        editTextMentorPhone = findViewById(R.id.edit_mentor_phone);

        editTextCourseTitle.setText(intent.getStringExtra(EXTRA_COURSE_TITLE));

        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

        String editStartDate = intent.getStringExtra(EXTRA_COURSE_START_DATE);
        String editEndDate = intent.getStringExtra(EXTRA_COURSE_END_DATE);

        try {
            Date dateStart = sdf.parse(editStartDate);
            final Calendar calStart = Calendar.getInstance();
            calStart.setTime(dateStart);

            int sYear = calStart.get(Calendar.YEAR);
            int sMonth = calStart.get(Calendar.MONTH);
            int sDay = calStart.get(Calendar.DAY_OF_MONTH);
            datePickerCourseStart.updateDate(sYear, sMonth, sDay);


            Date dateEnd = sdf.parse(editEndDate);
            final Calendar calEnd = Calendar.getInstance();
            calEnd.setTime(dateEnd);

            int eYear = calEnd.get(Calendar.YEAR);
            int eMonth = calEnd.get(Calendar.MONTH);
            int eDay = calEnd.get(Calendar.DAY_OF_MONTH);
            datePickerCourseEnd.updateDate(eYear, eMonth, eDay);

        } catch (ParseException e) {
            e.printStackTrace();
        }


        getStatusSelection(intent.getStringExtra(EXTRA_COURSE_STATUS));
        editTextMentorName.setText(intent.getStringExtra(EXTRA_MENTOR_NAME));
        editTextMentorEmail.setText(intent.getStringExtra(EXTRA_MENTOR_EMAIL));
        editTextMentorPhone.setText(intent.getStringExtra(EXTRA_PHONE_NUMBER));


        courseViewModel = new  ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(CourseViewModel.class);

        setTitle("Editing Course");

    }

    private void saveEdit() {

        String courseEditTitle = editTextCourseTitle.getText().toString();

        SimpleDateFormat converter = new SimpleDateFormat("MM/dd/yyyy");

        int sDay = datePickerCourseStart.getDayOfMonth();
        int sMonth = datePickerCourseStart.getMonth();
        int sYear = datePickerCourseStart.getYear();
        Calendar calStart = Calendar.getInstance();
        calStart.set(sYear, sMonth, sDay);
        String formattedStart = converter.format(calStart.getTime());

        int eDay = datePickerCourseEnd.getDayOfMonth();
        int eMonth = datePickerCourseEnd.getMonth();
        int eYear = datePickerCourseEnd.getYear();
        Calendar calEnd = Calendar.getInstance();
        calEnd.set(eYear, eMonth, eDay);
        String formattedEnd = converter.format(calEnd.getTime());

        String courseEditMentorName = editTextMentorName.getText().toString();
        String courseEditMentorEmail = editTextMentorEmail.getText().toString();
        String courseEditMentorPhone = editTextMentorPhone.getText().toString();

        int radioId = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(radioId);
        String courseEditStatus  = radioButton.getText().toString();

        if (courseEditTitle.trim().isEmpty() || courseEditMentorName.trim().isEmpty() || courseEditMentorEmail.trim().isEmpty() || courseEditMentorPhone.trim().isEmpty() || courseEditStatus.trim().isEmpty()) {

            Toast.makeText(this, "All fields must be filled", Toast.LENGTH_SHORT).show();

        }


        Intent data = new Intent(EditCourseActivity.this, CourseListActivity.class);

        int termId = courseViewModel.getTermId();

        int id = getIntent().getIntExtra(EXTRA_COURSE_ID, -1);

        Course course = new Course(termId, courseEditTitle, formattedStart, formattedEnd, courseEditStatus, courseEditMentorName, courseEditMentorEmail, courseEditMentorPhone);
        course.setCourseId(id);

        courseViewModel.updateCourse(course);


        startActivityForResult(data, EDIT_COURSE_REQUEST);

    }

    private void getStatusSelection(String s) {

        if(s.trim().equals("Plan To Take")) {

            radioGroup.check(R.id.edit_rb_plan_to_take);

        } else if(s.trim().equals("In Progress")) {

            radioGroup.check(R.id.edit_rb_in_progress);

        } else if(s.trim().equals("Completed")) {

            radioGroup.check(R.id.edit_rb_completed);

        } else if(s.trim().equals("Dropped")) {

            radioGroup.check(R.id.edit_rb_dropped);

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_menu_button:
                saveEdit();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}