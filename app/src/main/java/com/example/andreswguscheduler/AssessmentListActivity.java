package com.example.andreswguscheduler;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.andreswguscheduler.Entities.Assessment;
import com.example.andreswguscheduler.Ui.AssessmentAdapter;
import com.example.andreswguscheduler.ViewModel.AssessmentViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import static com.example.andreswguscheduler.Utilities.Constants.ADD_ASSESSMENT_REQUEST;
import static com.example.andreswguscheduler.Utilities.Constants.EXTRA_ASSESSMENT;
import static com.example.andreswguscheduler.Utilities.Constants.EXTRA_ASSESSMENT_DUE_DATE;
import static com.example.andreswguscheduler.Utilities.Constants.EXTRA_ASSESSMENT_TITLE;
import static com.example.andreswguscheduler.Utilities.Constants.EXTRA_ASSESSMENT_TYPE;
import static com.example.andreswguscheduler.Utilities.Constants.VIEW_ASSESSMENT_REQUEST;

public class AssessmentListActivity extends AppCompatActivity {

    private AssessmentViewModel assessmentViewModel;
    private int courseId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_list);

        FloatingActionButton buttonAddAssessment = findViewById(R.id.button_add_assessment);

        buttonAddAssessment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AssessmentListActivity.this, AddAssessmentActivity.class);
                startActivityForResult(intent, ADD_ASSESSMENT_REQUEST);
            }
        });

        final RecyclerView recyclerView = findViewById(R.id.assessment_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final AssessmentAdapter adapter = new AssessmentAdapter();
        recyclerView.setAdapter(adapter);

        assessmentViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(AssessmentViewModel.class);

        courseId = assessmentViewModel.getCourseId();

        assessmentViewModel.setCourseId(courseId);
        assessmentViewModel.getAllAssessments(courseId).observe(this, new Observer<List<Assessment>>() {
            @Override
            public void onChanged(List<Assessment> assessments) {
                adapter.setAssessments(assessments);
            }
        });

        adapter.setOnAssessmentListener(new AssessmentAdapter.OnAssessmentListener() {
            @Override
            public void onAssessmentClick(Assessment assessment) {

                Intent intent = new Intent(AssessmentListActivity.this, ViewAssessmentActivity.class);

                intent.putExtra(EXTRA_ASSESSMENT_TITLE, assessment.getAssessmentTitle());
                intent.putExtra(EXTRA_ASSESSMENT_TYPE, assessment.getAssessmentType());
                intent.putExtra(EXTRA_ASSESSMENT_DUE_DATE, assessment.getAssessmentDueDate());

                intent.putExtra(EXTRA_ASSESSMENT, assessment);

                startActivityForResult(intent, VIEW_ASSESSMENT_REQUEST);

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_ASSESSMENT_REQUEST && resultCode == RESULT_OK) {

            int courseId = assessmentViewModel.getCourseId();

            String title = data.getStringExtra(EXTRA_ASSESSMENT_TITLE);
            String type = data.getStringExtra(EXTRA_ASSESSMENT_TYPE);
            String dueDate = data.getStringExtra(EXTRA_ASSESSMENT_DUE_DATE);

            Assessment assessment = new Assessment(courseId, title, type, dueDate);

            assessmentViewModel.insertAssessment(assessment);

        } else {
            return;
        }


    }
}