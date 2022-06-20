package com.project.to_dolistapp.ui.add;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.project.to_dolistapp.R;
import com.project.to_dolistapp.data.DatabaseContract;
import com.project.to_dolistapp.data.TaskHelper;
import com.project.to_dolistapp.databinding.ActivityAddTaskBinding;
import com.project.to_dolistapp.ui.main.TaskActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

public class AddTaskActivity extends AppCompatActivity implements DatePickerFragment.DialogDateListener {

    private ActivityAddTaskBinding binding;

    private TaskHelper taskHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddTaskBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Objects.requireNonNull(getSupportActionBar()).setTitle(getString(R.string.add));

        taskHelper = TaskHelper.getInstance(getApplicationContext());

        binding.imgDueDate.setOnClickListener(view -> {
            DatePickerFragment dialogFragment = new DatePickerFragment();
            dialogFragment.show(getSupportFragmentManager(), "datePicker");
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_save){
            if (TextUtils.isEmpty(binding.addEdTitle.getText()) || TextUtils.isEmpty(binding.addEdDescription.getText()) || TextUtils.isEmpty(binding.addTvDueDate.getText())){
                Toast.makeText(this, R.string.field_not_blank, Toast.LENGTH_SHORT).show();
            }else {
                taskHelper.open();

                ContentValues values = new ContentValues();
                values.put(DatabaseContract.TaskColumn.TITLE, String.valueOf(binding.addEdTitle.getText()));
                values.put(DatabaseContract.TaskColumn.DESCRIPTION, String.valueOf(binding.addEdDescription.getText()));
                values.put(DatabaseContract.TaskColumn.DUE_DATE, String.valueOf(binding.addTvDueDate.getText()));
                values.put(DatabaseContract.TaskColumn.IS_COMPLETE, 0);

                taskHelper.insert(values);

                Intent intent = new Intent(AddTaskActivity.this, TaskActivity.class);
                startActivity(intent);

                taskHelper.close();
                finish();
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDialogDataSet(String tag, Integer year, Integer month, Integer dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, dayOfMonth);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

        binding.addTvDueDate.setText(sdf.format(calendar.getTime()));
    }
}