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
import android.widget.TextView;
import android.widget.Toast;

import com.example.andreswguscheduler.Entities.Assessment;
import com.example.andreswguscheduler.ViewModel.AssessmentViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static com.example.andreswguscheduler.Utilities.Constants.EDIT_ASSESSMENT_REQUEST;
import static com.example.andreswguscheduler.Utilities.Constants.EXTRA_ASSESSMENT;
import static com.example.andreswguscheduler.Utilities.Constants.EXTRA_ASSESSMENT_DUE_DATE;
import static com.example.andreswguscheduler.Utilities.Constants.EXTRA_ASSESSMENT_ID;
import static com.example.andreswguscheduler.Utilities.Constants.EXTRA_ASSESSMENT_TITLE;
import static com.example.andreswguscheduler.Utilities.Constants.EXTRA_ASSESSMENT_TYPE;

public class ViewAssessmentActivity extends AppCompatActivity {

    private AssessmentViewModel assessmentViewModel;

    private TextView assessmentTitle;
    private TextView assessmentType;
    private TextView assessmentDueDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_assessment);

        assessmentViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(AssessmentViewModel.class);

        assessmentTitle = findViewById(R.id.text_view_assessment_title);
        assessmentType = findViewById(R.id.text_view_assessment_type);
        assessmentDueDate = findViewById(R.id.text_view_assessment_due_date);

        Intent intent = getIntent();

        assessmentTitle.setText(intent.getStringExtra(EXTRA_ASSESSMENT_TITLE));
        assessmentType.setText(intent.getStringExtra(EXTRA_ASSESSMENT_TYPE));
        assessmentDueDate.setText(intent.getStringExtra(EXTRA_ASSESSMENT_DUE_DATE));


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.assessment_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        Intent intent = getIntent();
        Intent delIntent = new Intent(ViewAssessmentActivity.this, AssessmentListActivity.class);

        Assessment assessment = (Assessment)intent.getSerializableExtra(EXTRA_ASSESSMENT);

        switch (item.getItemId()) {
            case R.id.action_delete:

                assessmentViewModel.deleteAssessment(assessment);
                Toast.makeText(this, "Assessment Deleted", Toast.LENGTH_SHORT).show();
                startActivity(delIntent);
                return true;

            case R.id.action_edit:

                Intent editIntent = new Intent(ViewAssessmentActivity.this, EditAssessmentActivity.class);

                editIntent.putExtra(EXTRA_ASSESSMENT, intent.getSerializableExtra(EXTRA_ASSESSMENT));

                editIntent.putExtra(EXTRA_ASSESSMENT_ID, assessment.getAssessmentId());
                editIntent.putExtra(EXTRA_ASSESSMENT_TITLE, assessment.getAssessmentTitle());
                editIntent.putExtra(EXTRA_ASSESSMENT_TYPE, assessment.getAssessmentType());
                editIntent.putExtra(EXTRA_ASSESSMENT_DUE_DATE, assessment.getAssessmentDueDate());

                startActivityForResult(editIntent, EDIT_ASSESSMENT_REQUEST);

                Toast.makeText(this, "Editing Assessment", Toast.LENGTH_SHORT).show();

                return true;

            case R.id.action_set_alert:

                String aDueDate = assessment.getAssessmentDueDate();
                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

                try{
                    Date date = sdf.parse(aDueDate);

                    final Calendar cal = Calendar.getInstance();
                    cal.setTime(date);

                    Intent alertIntent = new Intent(ViewAssessmentActivity.this, MyReceiver.class);
                    alertIntent.putExtra("key", assessment.getAssessmentTitle() + "Due Date is Today");
                    PendingIntent sender = PendingIntent.getBroadcast(ViewAssessmentActivity.this, 0, alertIntent, 0);

                    AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
                    Long dateMillis = cal.getTimeInMillis();

                    alarmManager.set(AlarmManager.RTC_WAKEUP, dateMillis, sender);

                } catch (ParseException e) {
                    e.printStackTrace();
                }


                return true;

            default:
                return super.onOptionsItemSelected(item);

        }

    }
    
}