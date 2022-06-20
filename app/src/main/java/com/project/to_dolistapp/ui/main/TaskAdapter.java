package com.project.to_dolistapp.ui.main;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.to_dolistapp.data.Task;
import com.project.to_dolistapp.databinding.TaskItemBinding;
import com.project.to_dolistapp.ui.detail.DetailTaskActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.*;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {

    private final ArrayList<Task> listTask = new ArrayList<>();

    public void setListTask(ArrayList<Task> listTask) {
        if (listTask.size() > 0) {
            this.listTask.clear();
        }
        this.listTask.addAll(listTask);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TaskItemBinding binding = TaskItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Task task = listTask.get(position);
        holder.bind(task);
    }

    @Override
    public int getItemCount() {
        return listTask.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitle;

        private final TaskItemBinding binding;

        public ViewHolder(TaskItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            this.tvTitle = binding.itemTvTitle;
        }

        Task getTask;

        public void bind(Task task) {
            getTask = task;
            tvTitle.setText(task.getTitle());
            binding.itemTvDate.setText(converterMillisToString(task.getDueDate()));

            itemView.setOnClickListener(view -> {
                Intent intent = new Intent(itemView.getContext(), DetailTaskActivity.class);
                intent.putExtra(DetailTaskActivity.TASK_ID, task);
                itemView.getContext().startActivity(intent);
            });
        }
    }

    public static String converterMillisToString(Long timeMillis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeMillis);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        return sdf.format(calendar.getTime());
    }
}
