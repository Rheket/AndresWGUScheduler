package com.example.andreswguscheduler;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.andreswguscheduler.Entities.Course;
import com.example.andreswguscheduler.ViewModel.CourseViewModel;

import static com.example.andreswguscheduler.Utilities.Constants.EXTRA_COURSE;

public class NotesActivity extends AppCompatActivity {

    private TextView courseTitle;
    private EditText editTextNotes;
    private Course course;
    private CourseViewModel courseViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        courseTitle = findViewById(R.id.notes_course_title);
        editTextNotes = findViewById(R.id.edit_text_notes);

        Intent intent = getIntent();
        course = (Course)intent.getSerializableExtra(EXTRA_COURSE);

        courseViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(CourseViewModel.class);

        courseTitle.setText(course.getCourseTitle() + " notes");
        editTextNotes.setText(course.getCourseNotes());

        setTitle(course.getCourseTitle() + " notes");

    }

    private void saveNotes() {

        String notes = editTextNotes.getText().toString();
        courseViewModel.updateNotes(notes, course.getCourseId());

        Intent intent = new Intent(NotesActivity.this, CourseListActivity.class);

        startActivity(intent);

    }

    private boolean shareNotes() {

        String notes = editTextNotes.getText().toString();

        Intent sendIntent = new Intent();

        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, notes);

        sendIntent.putExtra(Intent.EXTRA_TITLE, "Notes");
        sendIntent.setType("text/plain");

        Intent shareIntent = Intent.createChooser(sendIntent, null) ;
        startActivity(shareIntent);
        return true;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.notes_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_save_note:
                saveNotes();
                return true;
            case R.id.action_share_note:
                shareNotes();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }


}