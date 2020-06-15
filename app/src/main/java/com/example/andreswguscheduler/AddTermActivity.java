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

import static com.example.andreswguscheduler.Utilities.Constants.EXTRA_TERM_END_DATE;
import static com.example.andreswguscheduler.Utilities.Constants.EXTRA_TERM_START_DATE;
import static com.example.andreswguscheduler.Utilities.Constants.EXTRA_TERM_TITLE;

public class AddTermActivity extends AppCompatActivity {

    private EditText editTermTitle;
    private DatePicker editTermStartDate;
    private DatePicker editTermEndDate;
    private EditText editTermStatus;

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
        String termStartDate = editTermStartDate.getMonth() + "/" + editTermStartDate.getDayOfMonth() + "/" + editTermStartDate.getYear();
        String termEndDate = editTermEndDate.getMonth() + "/" + editTermEndDate.getDayOfMonth() + "/" + editTermEndDate.getYear();

        if (termTitle.trim().isEmpty() || termStartDate.trim().isEmpty() || termEndDate.trim().isEmpty()) {

            Toast.makeText(this, "All fields must be filled", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();

        data.putExtra(EXTRA_TERM_TITLE, termTitle);
        data.putExtra(EXTRA_TERM_START_DATE, termStartDate);
        data.putExtra(EXTRA_TERM_END_DATE, termEndDate);

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