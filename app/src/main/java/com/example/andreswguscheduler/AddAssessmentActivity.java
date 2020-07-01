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

import com.example.andreswguscheduler.Entities.Assessment;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import static com.example.andreswguscheduler.Utilities.Constants.EXTRA_ASSESSMENT_DUE_DATE;
import static com.example.andreswguscheduler.Utilities.Constants.EXTRA_ASSESSMENT_TITLE;
import static com.example.andreswguscheduler.Utilities.Constants.EXTRA_ASSESSMENT_TYPE;

public class AddAssessmentActivity extends AppCompatActivity {

    private EditText title;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private DatePicker dueDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_assessment);

        title = findViewById(R.id.add_assessment_title);
        radioGroup = findViewById(R.id.radio_group_add_assessment_type);
        dueDate = findViewById(R.id.date_picker_add_due_date);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        setTitle("Add Assessment");


    }

    private void saveAssessment() {

        String sTitle = title.getText().toString();
        int radioId = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(radioId);

        String sType = radioButton.getText().toString();

        int day = dueDate.getDayOfMonth();
        int month = dueDate.getMonth();
        int year = dueDate.getYear();
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        SimpleDateFormat converter = new SimpleDateFormat("MM/dd/yyyy");
        String formattedDueDate = converter.format(calendar.getTime());

        if (sTitle.trim().isEmpty()) {
            Toast.makeText(this, "Please set a Title", Toast.LENGTH_SHORT).show();
            return;
        }


        Intent data  = new Intent();
        data.putExtra(EXTRA_ASSESSMENT_TITLE, sTitle);
        data.putExtra(EXTRA_ASSESSMENT_TYPE, sType);
        data.putExtra(EXTRA_ASSESSMENT_DUE_DATE, formattedDueDate);

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

        switch (item.getItemId()) {
            case R.id.save_menu_button:
                saveAssessment();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }


    }
}