package com.project.to_dolistapp.ui.main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;

import com.project.to_dolistapp.R;
import com.project.to_dolistapp.data.Task;
import com.project.to_dolistapp.data.TaskHelper;
import com.project.to_dolistapp.databinding.ActivityTaskBinding;
import com.project.to_dolistapp.ui.add.AddTaskActivity;

import java.util.ArrayList;
import java.util.Objects;


public class TaskActivity extends AppCompatActivity {

    private ActivityTaskBinding binding;

    private TaskHelper taskHelper;

    private TaskAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTaskBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Objects.requireNonNull(getSupportActionBar()).setTitle(getString(R.string.task));

        taskHelper = TaskHelper.getInstance(getApplicationContext());

        taskHelper.open();

        adapter = new TaskAdapter();

        loadTaskAsync();

        binding.fab.setOnClickListener(view -> {
            Intent intent = new Intent(TaskActivity.this, AddTaskActivity.class);
            startActivity(intent);
        });
    }

    private void loadTaskAsync() {

        Cursor cursor = taskHelper.queryAll();
        ArrayList<Task> task = MappingCursor.mapCursorToArrayList(cursor);

        if (task.size() > 0) {
            binding.rvTask.setVisibility(View.VISIBLE);
            adapter.setListTask(task);
            binding.rvTask.setHasFixedSize(true);
            binding.rvTask.setAdapter(adapter);
            binding.tvNoTask.setVisibility(View.GONE);
        } else {
            binding.rvTask.setVisibility(View.GONE);
            binding.tvNoTask.setVisibility(View.VISIBLE);
        }

        taskHelper.close();
    }
}