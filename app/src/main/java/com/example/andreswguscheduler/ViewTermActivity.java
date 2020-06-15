package com.example.andreswguscheduler;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.andreswguscheduler.Entities.Term;
import com.example.andreswguscheduler.ViewModel.TermViewModel;

import static com.example.andreswguscheduler.Utilities.Constants.ADD_COURSE_REQUEST;
import static com.example.andreswguscheduler.Utilities.Constants.EDIT_TERM_REQUEST;
import static com.example.andreswguscheduler.Utilities.Constants.EXTRA_TERM;
import static com.example.andreswguscheduler.Utilities.Constants.EXTRA_TERM_END_DATE;
import static com.example.andreswguscheduler.Utilities.Constants.EXTRA_TERM_ID;
import static com.example.andreswguscheduler.Utilities.Constants.EXTRA_TERM_START_DATE;
import static com.example.andreswguscheduler.Utilities.Constants.EXTRA_TERM_TITLE;

public class ViewTermActivity extends AppCompatActivity {

    private TextView termTitle;
    private TextView termStart;
    private TextView termEnd;

    private TermViewModel termViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_term);

        termViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(TermViewModel.class);

        termTitle = findViewById(R.id.add_text_term_title);
        termStart = findViewById(R.id.edit_text_start);
        termEnd = findViewById(R.id.edit_text_end);

        Intent intent = getIntent();

        termTitle.setText(intent.getStringExtra(EXTRA_TERM_TITLE));
        termStart.setText(intent.getStringExtra(EXTRA_TERM_START_DATE));
        termEnd.setText(intent.getStringExtra(EXTRA_TERM_END_DATE));

        Button buttonViewAllCourses = findViewById(R.id.view_course_list);

        buttonViewAllCourses.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent i = new Intent(ViewTermActivity.this, CourseListActivity.class);
                startActivityForResult(i, ADD_COURSE_REQUEST);

            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.edit_del_menu, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        Intent intent = getIntent();
        Intent delIntent = new Intent(ViewTermActivity.this, TermListActivity.class);

        Term term = (Term)intent.getSerializableExtra(EXTRA_TERM);

        switch (item.getItemId()) {

            case R.id.action_delete:

                termViewModel.deleteTerm(term);
                Toast.makeText(this, "Term Deleted", Toast.LENGTH_SHORT).show();
                startActivity(delIntent);

                return true;

            case R.id.action_edit:

                Intent editIntent = new Intent(ViewTermActivity.this, EditTermActivity.class);

                editIntent.putExtra(EXTRA_TERM, intent.getSerializableExtra(EXTRA_TERM));
                editIntent.putExtra(EXTRA_TERM_ID, term.getId());
                editIntent.putExtra(EXTRA_TERM_TITLE, term.getTermTitle());
                editIntent.putExtra(EXTRA_TERM_START_DATE, term.getStartDate());
                editIntent.putExtra(EXTRA_TERM_END_DATE, term.getEndDate());

                startActivityForResult(editIntent, EDIT_TERM_REQUEST);

                Toast.makeText(this, "Editing Term", Toast.LENGTH_SHORT).show();
                return true;

            default:

                return super.onOptionsItemSelected(item);


        }


    }
}