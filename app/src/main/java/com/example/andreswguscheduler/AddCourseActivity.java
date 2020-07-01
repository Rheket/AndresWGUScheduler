package com.example.andreswguscheduler;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import static com.example.andreswguscheduler.Utilities.Constants.EXTRA_COURSE_END_DATE;
import static com.example.andreswguscheduler.Utilities.Constants.EXTRA_COURSE_START_DATE;
import static com.example.andreswguscheduler.Utilities.Constants.EXTRA_COURSE_STATUS;
import static com.example.andreswguscheduler.Utilities.Constants.EXTRA_COURSE_TITLE;
import static com.example.andreswguscheduler.Utilities.Constants.EXTRA_MENTOR_EMAIL;
import static com.example.andreswguscheduler.Utilities.Constants.EXTRA_MENTOR_NAME;
import static com.example.andreswguscheduler.Utilities.Constants.EXTRA_PHONE_NUMBER;

public class AddCourseActivity extends AppCompatActivity {

    private EditText editCourseTitle;
    private EditText editMentorName;
    private EditText editMentorEmail;
    private EditText editMentorPhoneNumber;

    private DatePicker editCourseStart;
    private DatePicker editCourseEnd;

    private RadioGroup radioGroup;
    private RadioButton radioButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);

        editCourseTitle = findViewById(R.id.edit_text_course_title);
        editMentorName = findViewById(R.id.edit_text_course_mentor_name);
        editMentorEmail = findViewById(R.id.edit_text_course_mentor_email);
        editMentorPhoneNumber = findViewById(R.id.edit_text_course_mentor_phone);

        editCourseStart = findViewById(R.id.date_picker_course_start);
        editCourseEnd = findViewById(R.id.date_picker_course_end);

        radioGroup = findViewById(R.id.radio_group_course_status);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        setTitle("Add Course");

    }

    private void saveCourse() {

        String title = editCourseTitle.getText().toString();
        String mentorName = editMentorName.getText().toString();
        String mentorEmail = editMentorEmail.getText().toString();
        String mentorPhoneNumber = editMentorPhoneNumber.getText().toString();

        SimpleDateFormat converter = new SimpleDateFormat("MM/dd/yyyy");

        int sDay = editCourseStart.getDayOfMonth();
        int sMonth = editCourseStart.getMonth();
        int sYear = editCourseStart.getYear();
        Calendar calStart = Calendar.getInstance();
        calStart.set(sYear, sMonth, sDay);
        String formattedStart = converter.format(calStart.getTime());

        int eDay = editCourseEnd.getDayOfMonth();
        int eMonth = editCourseEnd.getMonth();
        int eYear = editCourseEnd.getYear();
        Calendar calEnd = Calendar.getInstance();
        calEnd.set(eYear, eMonth, eDay);
        String formattedEnd = converter.format(calEnd.getTime());

        int radioId = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(radioId);

        String courseStatus = radioButton.getText().toString();
        
        if (title.trim().isEmpty() || mentorName.trim().isEmpty() || mentorEmail.trim().isEmpty() || mentorPhoneNumber.trim().isEmpty()) {

            Toast.makeText(this, "All fields must be filled", Toast.LENGTH_SHORT).show();
            return;
            
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_COURSE_TITLE, title);
        data.putExtra(EXTRA_COURSE_START_DATE, formattedStart);
        data.putExtra(EXTRA_COURSE_END_DATE, formattedEnd);
        data.putExtra(EXTRA_MENTOR_NAME, mentorName);
        data.putExtra(EXTRA_MENTOR_EMAIL, mentorEmail);
        data.putExtra(EXTRA_PHONE_NUMBER, mentorPhoneNumber);
        data.putExtra(EXTRA_COURSE_STATUS, courseStatus);

        setResult(RESULT_OK, data);
        finish();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_menu, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {

            case R.id.save_menu_button:
                saveCourse();
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }


}
