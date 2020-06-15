package com.example.andreswguscheduler;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import static com.example.andreswguscheduler.Utilities.Constants.EXTRA_COURSE_END_DATE;
import static com.example.andreswguscheduler.Utilities.Constants.EXTRA_COURSE_START_DATE;
import static com.example.andreswguscheduler.Utilities.Constants.EXTRA_PHONE_NUMBER;
import static com.example.andreswguscheduler.Utilities.Constants.EXTRA_COURSE_TITLE;

public class AddCourseActivity extends AppCompatActivity {

    private EditText editCourseTitle;
    private EditText editMentorName;
    private EditText editMentorEmail;
    private EditText editMentorPhoneNumber;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);

        editCourseTitle = findViewById(R.id.edit_text_course_title);
        editMentorName = findViewById(R.id.edit_text_mentor);
        editMentorEmail = findViewById(R.id.edit_text_mentor_email);
        editMentorPhoneNumber = findViewById(R.id.edit_text_mentor_phone_number);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        setTitle("Add Course");

    }

    private void saveCourse() {

        String title = editCourseTitle.getText().toString();
        String mentorName = editMentorName.getText().toString();
        String mentorEmail = editMentorEmail.getText().toString();
        String mentorPhoneNumber = editMentorPhoneNumber.getText().toString();
        
        if (title.trim().isEmpty() || mentorName.trim().isEmpty() || mentorEmail.trim().isEmpty() || mentorPhoneNumber.trim().isEmpty()) {

            Toast.makeText(this, "All fields must be filled", Toast.LENGTH_SHORT).show();
            return;
            
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_COURSE_TITLE, title);
        data.putExtra(EXTRA_COURSE_START_DATE, mentorName);
        data.putExtra(EXTRA_COURSE_END_DATE, mentorEmail);
        data.putExtra(EXTRA_PHONE_NUMBER, mentorPhoneNumber);

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
