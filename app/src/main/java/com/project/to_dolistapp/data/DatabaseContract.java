package com.project.to_dolistapp.data;

import android.provider.BaseColumns;

public class DatabaseContract {
    static String TABLE_NAME = "task";

    public static final class TaskColumn implements BaseColumns{
        public static String TITLE = "title";
        public static String DESCRIPTION = "description";
        public static String DUE_DATE = "dueData";
        public static String IS_COMPLETE = "isComplete";
    }
}
