package com.project.to_dolistapp.ui.detail;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuItem;

import com.project.to_dolistapp.R;
import com.project.to_dolistapp.data.DatabaseContract;
import com.project.to_dolistapp.data.Task;
import com.project.to_dolistapp.data.TaskHelper;
import com.project.to_dolistapp.databinding.ActivityDetailTaskBinding;
import com.project.to_dolistapp.ui.main.TaskActivity;
import com.project.to_dolistapp.ui.main.TaskAdapter;

import java.util.Objects;

public class DetailTaskActivity extends AppCompatActivity {

    public static String TASK_ID = "task_id";

    private TaskHelper taskHelper;

    private ActivityDetailTaskBinding binding;

    private Task mTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailTaskBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Objects.requireNonNull(getSupportActionBar()).setTitle(getString(R.string.detail));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        taskHelper = TaskHelper.getInstance(getApplicationContext());

        showDetail();

        binding.detailEdTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence text, int i, int i1, int i2) {
                String value = String.valueOf(text);
                setButton(value);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.detailEdDescription.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence text, int i, int i1, int i2) {
                String value = String.valueOf(text);
                setButton(value);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    private void setButton(String value) {
        if (!value.equals(mTask.getTitle()) && !value.equals(mTask.getDescription())){
            if (!TextUtils.isEmpty(value)){
                binding.btnUpdateTask.setEnabled(true);
                binding.btnUpdateTask.setBackgroundColor(getResources().getColor(R.color.teal_200));
                binding.btnDeleteTask.setEnabled(true);
                binding.btnDeleteTask.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
            }else {
                binding.btnUpdateTask.setEnabled(false);
                binding.btnUpdateTask.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
                binding.btnDeleteTask.setEnabled(false);
                binding.btnDeleteTask.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
            }
        }else {
            binding.btnUpdateTask.setEnabled(false);
            binding.btnUpdateTask.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
        }
    }

    private void showDetail() {
        taskHelper.open();

        Task task = getIntent().getExtras().getParcelable(TASK_ID);
        mTask = task;

        binding.detailEdTitle.setText(task.getTitle());
        binding.detailEdDescription.setText(task.getDescription());
        binding.detailEdDueDate.setText(TaskAdapter.converterMillisToString(task.getDueDate()));

        binding.btnDeleteTask.setOnClickListener(view -> {

            if (binding.btnDeleteTask.getText().equals(getString(R.string.delete_task))) {
                taskHelper.deleteById(String.valueOf(task.getId()));
                Intent intent = new Intent(DetailTaskActivity.this, TaskActivity.class);
                startActivity(intent);
            }else {
                ContentValues values = new ContentValues();
                values.put(DatabaseContract.TaskColumn.TITLE, String.valueOf(binding.detailEdTitle.getText()));
                values.put(DatabaseContract.TaskColumn.DESCRIPTION, String.valueOf(binding.detailEdDescription.getText()));

                taskHelper.update(String.valueOf(mTask.getId()), values);

                Intent intent = new Intent(DetailTaskActivity.this, TaskActivity.class);
                startActivity(intent);
            }
            finish();
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            super.onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        taskHelper.close();
    }
}