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
import androidx.lifecycle.ViewModelProvider;

import com.example.andreswguscheduler.Entities.Assessment;
import com.example.andreswguscheduler.ViewModel.AssessmentViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static com.example.andreswguscheduler.Utilities.Constants.EDIT_ASSESSMENT_REQUEST;
import static com.example.andreswguscheduler.Utilities.Constants.EXTRA_ASSESSMENT_DUE_DATE;
import static com.example.andreswguscheduler.Utilities.Constants.EXTRA_ASSESSMENT_ID;
import static com.example.andreswguscheduler.Utilities.Constants.EXTRA_ASSESSMENT_TITLE;
import static com.example.andreswguscheduler.Utilities.Constants.EXTRA_ASSESSMENT_TYPE;

public class EditAssessmentActivity extends AppCompatActivity {

    private EditText editTitle;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private DatePicker editDueDate;

    private AssessmentViewModel assessmentViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_assessment);

        Intent intent = getIntent();

        editTitle = findViewById(R.id.edit_assessment_title);
        radioGroup = findViewById(R.id.radio_group_assessment_type);
        editDueDate = findViewById(R.id.date_picker_due_date);

        editTitle.setText(intent.getStringExtra(EXTRA_ASSESSMENT_TITLE));
        getTypeSelection(intent.getStringExtra(EXTRA_ASSESSMENT_TYPE));

        String aDueDate = intent.getStringExtra(EXTRA_ASSESSMENT_DUE_DATE);

        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        try {
            Date date = sdf.parse(aDueDate);
            final Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);

            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            editDueDate.updateDate(year, month, day);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        assessmentViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(AssessmentViewModel.class);

        setTitle("Editing Assessment");

    }

    private void saveEdit() {

        String title = editTitle.getText().toString();

        int radioId = radioGroup.getCheckedRadioButtonId();

        radioButton = findViewById(radioId);
        String type = radioButton.getText().toString();

        Calendar cal = Calendar.getInstance();
        cal.set(editDueDate.getYear(), editDueDate.getMonth(), editDueDate.getDayOfMonth());
        SimpleDateFormat converter = new SimpleDateFormat("MM/dd/yyyy");
        String formattedDueDate = converter.format(cal.getTime());

        if (title.trim().isEmpty()) {
            Toast.makeText(this, "Please set a title", Toast.LENGTH_SHORT).show();
        }

        Intent data = new Intent(EditAssessmentActivity.this, AssessmentListActivity.class);

        int courseId = assessmentViewModel.getCourseId();
        int id = getIntent().getIntExtra(EXTRA_ASSESSMENT_ID, -1);

        Assessment assessment = new Assessment(courseId, title, type, formattedDueDate);
        assessment.setAssessmentId(id);

        assessmentViewModel.updateAssessment(assessment);
        startActivityForResult(data, EDIT_ASSESSMENT_REQUEST);

    }

    private void getTypeSelection(String s) {

        if (s.trim().equals("Objective Assessment")) {
            radioGroup.check(R.id.rb_objective_assessment);
        } else if (s.trim().equals("Performance Assessment")) {
            radioGroup.check(R.id.rb_performance_assessment);
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