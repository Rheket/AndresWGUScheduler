package com.example.andreswguscheduler;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import static com.example.andreswguscheduler.Utilities.Constants.EXTRA_TERM_END_DATE;
import static com.example.andreswguscheduler.Utilities.Constants.EXTRA_TERM_START_DATE;
import static com.example.andreswguscheduler.Utilities.Constants.EXTRA_TERM_TITLE;

public class AddTermActivity extends AppCompatActivity {

    private EditText editTermTitle;
    private DatePicker editTermStartDate;
    private DatePicker editTermEndDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_term);

        editTermTitle = findViewById(R.id.add_text_term_title);
        editTermStartDate = findViewById(R.id.date_picker_add_term_start);
        editTermEndDate = findViewById(R.id.date_picker_add_term_end);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        setTitle("Add Term");

    }

    private void saveTerm() {

        String termTitle = editTermTitle.getText().toString();

        SimpleDateFormat converter = new SimpleDateFormat("MM/dd/yyyy");

        int sDay = editTermStartDate.getDayOfMonth();
        int sMonth = editTermStartDate.getMonth();
        int sYear = editTermStartDate.getYear();
        Calendar calStart = Calendar.getInstance();
        calStart.set(sYear, sMonth, sDay);
        String formattedStart = converter.format(calStart.getTime());

        int eDay = editTermEndDate.getDayOfMonth();
        int eMonth = editTermEndDate.getMonth();
        int eYear = editTermEndDate.getYear();
        Calendar calEnd = Calendar.getInstance();
        calEnd.set(eYear, eMonth, eDay);
        String formattedEnd = converter.format(calEnd.getTime());

        if (termTitle.trim().isEmpty()) {

            Toast.makeText(this, "All fields must be filled", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();

        data.putExtra(EXTRA_TERM_TITLE, termTitle);
        data.putExtra(EXTRA_TERM_START_DATE, formattedStart);
        data.putExtra(EXTRA_TERM_END_DATE, formattedEnd);

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

            case  R.id.save_menu_button:
                saveTerm();
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }

    }
}