package com.example.andreswguscheduler;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.andreswguscheduler.Entities.Term;
import com.example.andreswguscheduler.Ui.TermAdapter;
import com.example.andreswguscheduler.ViewModel.CourseViewModel;
import com.example.andreswguscheduler.ViewModel.TermViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import static com.example.andreswguscheduler.Utilities.Constants.ADD_TERM_REQUEST;
import static com.example.andreswguscheduler.Utilities.Constants.EXTRA_TERM;
import static com.example.andreswguscheduler.Utilities.Constants.EXTRA_TERM_END_DATE;
import static com.example.andreswguscheduler.Utilities.Constants.EXTRA_TERM_ID;
import static com.example.andreswguscheduler.Utilities.Constants.EXTRA_TERM_START_DATE;
import static com.example.andreswguscheduler.Utilities.Constants.EXTRA_TERM_TITLE;
import static com.example.andreswguscheduler.Utilities.Constants.VIEW_TERM_REQUEST;

public class TermListActivity extends AppCompatActivity  {

    private TermViewModel termViewModel;
    private CourseViewModel courseViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_list);

        FloatingActionButton buttonAddTerm = findViewById(R.id.button_add_term);

        buttonAddTerm.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TermListActivity.this, AddTermActivity.class);
                startActivityForResult(intent, ADD_TERM_REQUEST);
            }
        });


        final RecyclerView recyclerView = findViewById(R.id.term_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final TermAdapter adapter = new TermAdapter();
        recyclerView.setAdapter(adapter);

        courseViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(CourseViewModel.class);

        termViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(TermViewModel.class);
        termViewModel.getAllTerms().observe(this, new Observer<List<Term>>() {
            @Override
            public void onChanged(@Nullable List<Term> terms) {

                adapter.setTerms(terms);

            }
        });

        adapter.setOnTermListener(new TermAdapter.OnTermListener() {
            @Override
            public void onTermClick(Term term) {

                Intent intent = new Intent(TermListActivity.this, ViewTermActivity.class);

                intent.putExtra(EXTRA_TERM_ID, term.getId());
                intent.putExtra(EXTRA_TERM_TITLE, term.getTermTitle());
                intent.putExtra(EXTRA_TERM_START_DATE, term.getStartDate());
                intent.putExtra(EXTRA_TERM_END_DATE, term.getEndDate());

                intent.putExtra(EXTRA_TERM, term);

                startActivityForResult(intent, VIEW_TERM_REQUEST);

            }
        });



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_TERM_REQUEST && resultCode == RESULT_OK) {

            String title = data.getStringExtra(EXTRA_TERM_TITLE);
            String startDate = data.getStringExtra(EXTRA_TERM_START_DATE);
            String endDate = data.getStringExtra(EXTRA_TERM_END_DATE);

            Term term = new Term(title, startDate, endDate);
            termViewModel.insertTerm(term);

            Toast.makeText(this, "Term saved", Toast.LENGTH_SHORT).show();

        } else {
            return;
        }



    }


}