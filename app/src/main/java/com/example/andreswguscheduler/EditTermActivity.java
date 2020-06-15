package com.example.andreswguscheduler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.andreswguscheduler.Entities.Term;
import com.example.andreswguscheduler.ViewModel.TermViewModel;

import java.util.List;

import static com.example.andreswguscheduler.Utilities.Constants.EDIT_TERM_REQUEST;
import static com.example.andreswguscheduler.Utilities.Constants.EXTRA_TERM;
import static com.example.andreswguscheduler.Utilities.Constants.EXTRA_TERM_END_DATE;
import static com.example.andreswguscheduler.Utilities.Constants.EXTRA_TERM_ID;
import static com.example.andreswguscheduler.Utilities.Constants.EXTRA_TERM_START_DATE;
import static com.example.andreswguscheduler.Utilities.Constants.EXTRA_TERM_TITLE;

public class EditTermActivity extends AppCompatActivity {

    private EditText termTitle;
    private DatePicker datePickerStart;
    private TextView termStart;
    private DatePicker datePickerEnd;
    private TextView termEnd;

    private TermViewModel termViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_term);

        Intent intent = getIntent();

        termTitle = findViewById(R.id.edit_text_term_title);
        termStart = findViewById(R.id.text_view_term_start);
        termEnd = findViewById(R.id.text_view_term_end);
        datePickerStart = findViewById(R.id.date_picker_edit_term_start);
        datePickerEnd = findViewById(R.id.date_picker_edit_term_end);

        termTitle.setText(intent.getStringExtra(EXTRA_TERM_TITLE));
        termStart.setText(intent.getStringExtra(EXTRA_TERM_START_DATE));
        termEnd.setText(intent.getStringExtra(EXTRA_TERM_END_DATE));

        termViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(TermViewModel.class);


        setTitle("Editing Term");

    }

    private void saveEdit() {

        String editTitle = termTitle.getText().toString();
        String editStart = datePickerStart.getMonth() + "/" + datePickerStart.getDayOfMonth() + "/" + datePickerStart.getYear();
        String editEnd = datePickerEnd.getMonth() + "/" + datePickerEnd.getDayOfMonth() + "/" + datePickerEnd.getYear();

        if (editTitle.trim().isEmpty()) {

            Toast.makeText(this, "Please enter a title", Toast.LENGTH_SHORT).show();
            return;

        }

        Intent data = new Intent(EditTermActivity.this, TermListActivity.class);

        data.putExtra(EXTRA_TERM_TITLE, editTitle);
        data.putExtra(EXTRA_TERM_START_DATE, editStart);
        data.putExtra(EXTRA_TERM_END_DATE, editEnd);

        int id = getIntent().getIntExtra(EXTRA_TERM_ID, -1);

        if(id != -1) {
            data.putExtra(EXTRA_TERM_ID, id);
        }

        setResult(RESULT_OK, data);

        Term term = new Term(editTitle, editStart, editEnd);
        term.setId(id);

        termViewModel.updateTerm(term);

        startActivityForResult(data, EDIT_TERM_REQUEST);

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
                Toast.makeText(this, "Term updated", Toast.LENGTH_SHORT).show();

                return true;

            default:
                return super.onOptionsItemSelected(item);

        }

    }


}