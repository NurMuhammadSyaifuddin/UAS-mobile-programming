package com.project.to_dolistapp.ui.main;

import android.database.Cursor;

import com.project.to_dolistapp.data.DatabaseContract;
import com.project.to_dolistapp.data.Task;

import java.util.ArrayList;

public class MappingCursor {
    public static ArrayList<Task> mapCursorToArrayList(Cursor taskCursor) {
        ArrayList<Task> notesList = new ArrayList<>();
        while (taskCursor.moveToNext()) {
            int id = taskCursor.getInt(taskCursor.getColumnIndexOrThrow(DatabaseContract.TaskColumn._ID));
            String title = taskCursor.getString(taskCursor.getColumnIndexOrThrow(DatabaseContract.TaskColumn.TITLE));
            String description = taskCursor.getString(taskCursor.getColumnIndexOrThrow(DatabaseContract.TaskColumn.DESCRIPTION));
            Long date = taskCursor.getLong(taskCursor.getColumnIndexOrThrow(DatabaseContract.TaskColumn.DUE_DATE));
            notesList.add(new Task(id, title, description, date));
        }
        return notesList;
    }
}
