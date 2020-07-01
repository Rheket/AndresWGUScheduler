package com.example.andreswguscheduler;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.andreswguscheduler.Entities.Term;
import com.example.andreswguscheduler.ViewModel.TermViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static com.example.andreswguscheduler.Utilities.Constants.EDIT_TERM_REQUEST;
import static com.example.andreswguscheduler.Utilities.Constants.EXTRA_TERM_END_DATE;
import static com.example.andreswguscheduler.Utilities.Constants.EXTRA_TERM_ID;
import static com.example.andreswguscheduler.Utilities.Constants.EXTRA_TERM_START_DATE;
import static com.example.andreswguscheduler.Utilities.Constants.EXTRA_TERM_TITLE;

public class EditTermActivity extends AppCompatActivity {

    private EditText termTitle;
    private DatePicker datePickerStart;
    private DatePicker datePickerEnd;

    private TermViewModel termViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_term);

        Intent intent = getIntent();

        termTitle = findViewById(R.id.edit_text_term_title);
        datePickerStart = findViewById(R.id.date_picker_edit_term_start);
        datePickerEnd = findViewById(R.id.date_picker_edit_term_end);

        termTitle.setText(intent.getStringExtra(EXTRA_TERM_TITLE));

        SimpleDateFormat sdf  = new SimpleDateFormat("MM/dd/yyyy");

        String editStartDate = intent.getStringExtra(EXTRA_TERM_START_DATE);
        String editEndDate = intent.getStringExtra(EXTRA_TERM_END_DATE);

        try {
            Date dateStart = sdf.parse(editStartDate);
            final Calendar calStart = Calendar.getInstance();
            calStart.setTime(dateStart);

            int sYear = calStart.get(Calendar.YEAR);
            int sMonth = calStart.get(Calendar.MONTH);
            int sDay = calStart.get(Calendar.DAY_OF_MONTH);
            datePickerStart.updateDate(sYear, sMonth, sDay);


            Date dateEnd = sdf.parse(editEndDate);
            final Calendar calEnd = Calendar.getInstance();
            calEnd.setTime(dateEnd);

            int eYear = calEnd.get(Calendar.YEAR);
            int eMonth = calEnd.get(Calendar.MONTH);
            int eDay = calEnd.get(Calendar.DAY_OF_MONTH);
            datePickerEnd.updateDate(eYear, eMonth, eDay);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        termViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(TermViewModel.class);

        setTitle("Editing Term");

    }

    private void saveEdit() {

        String editTitle = termTitle.getText().toString();

        SimpleDateFormat converter = new SimpleDateFormat("MM/dd/yyyy");

        int sDay = datePickerStart.getDayOfMonth();
        int sMonth = datePickerStart.getMonth();
        int sYear = datePickerStart.getYear();
        Calendar calStart = Calendar.getInstance();
        calStart.set(sYear, sMonth, sDay);
        String formattedStart = converter.format(calStart.getTime());

        int eDay = datePickerEnd.getDayOfMonth();
        int eMonth = datePickerEnd.getMonth();
        int eYear = datePickerEnd.getYear();
        Calendar calEnd = Calendar.getInstance();
        calEnd.set(eYear, eMonth, eDay);
        String formattedEnd = converter.format(calEnd.getTime());


        if (editTitle.trim().isEmpty()) {

            Toast.makeText(this, "Please enter a title", Toast.LENGTH_SHORT).show();
            return;

        }

        Intent data = new Intent(EditTermActivity.this, TermListActivity.class);

        data.putExtra(EXTRA_TERM_TITLE, editTitle);
        data.putExtra(EXTRA_TERM_START_DATE, formattedStart);
        data.putExtra(EXTRA_TERM_END_DATE, formattedEnd);

        int id = getIntent().getIntExtra(EXTRA_TERM_ID, -1);

        if(id != -1) {
            data.putExtra(EXTRA_TERM_ID, id);
        }

        setResult(RESULT_OK, data);

        Term term = new Term(editTitle, formattedStart, formattedEnd);
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