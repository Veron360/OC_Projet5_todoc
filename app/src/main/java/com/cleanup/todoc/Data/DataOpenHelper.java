package com.cleanup.todoc.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.ui.utils.MainUIModel;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class DataOpenHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "data_base.db";
    private static final int DB_VERSION = 1;

    //Task
    private static final String TASKS_TABLE_NAME = "tasks";
    private static final String KEY_ID_TASKS = "id";
    private static final String KEY_PROJECT_ID_TASKS = "project_id";
    private static final String Task_NAME_KEY = "task_name";
    private static final String KEY_CREATION_TIME_STAMP_TASKS = "creation_time_stamp"; // Convertir date/heure
    //projet
    private static final String PROJECTS_TABLE_NAME = "projects";
    private static final String KEY_PROJECT_ID = "project_id";
    private static final String KEY_NAME_PROJECTS = "name";
    private static final String KEY_COLOR_PROJECTS = "color";

    //Table Task
    private static final String TaskDB =
            "CREATE TABLE " + TASKS_TABLE_NAME + "("
            + KEY_ID_TASKS +" integer primary key autoincrement,"
            + KEY_PROJECT_ID_TASKS+ " INTEGER, "
            + Task_NAME_KEY + " text not null,"
            + KEY_CREATION_TIME_STAMP_TASKS + " LONG ,"
            + "FOREIGN KEY("+KEY_PROJECT_ID_TASKS+") REFERENCES "+PROJECTS_TABLE_NAME+"("+KEY_PROJECT_ID+"));";



    //Table Projet
    private static final String ProjectDB =
            "CREATE TABLE " + PROJECTS_TABLE_NAME + "("
            + KEY_PROJECT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_NAME_PROJECTS +" text not null, "
            + KEY_COLOR_PROJECTS +" INT)";

    private static final String QUERY_GET_ALL_TASKS = "SELECT * FROM " + TASKS_TABLE_NAME;
    private static final String QUERY_GET_ALL_PROJECTS = "SELECT * FROM " + PROJECTS_TABLE_NAME;
    private static final String QUERY_GET_TASKS_WITH_PROJECT_INFO = "SELECT * FROM tasks INNER JOIN projects ON tasks.project_id=projects.project_id;";



    /**
     * Constructeur
     * @param context
     */
    public DataOpenHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }


    /**
     * onCreate
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ProjectDB);
        Log.d("TOTO", "ProjectTable onCreate invoked");

        db.execSQL(TaskDB);
        Log.d("TOTO", "TaskTable onCreate invoked");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        String strSql = "drop table Task";
        db.execSQL(strSql);
        //ré-invoquer le onCreate
        this.onCreate(db);
        Log.d("TOTO", "Table onUpgrade invoked");
    }


    /**
     * Task Table
     */

    public List<Task> getAllTasks() {
        List<Task> result = new ArrayList<>();

        Cursor cursor = getReadableDatabase().rawQuery(QUERY_GET_ALL_TASKS, null);
        while (cursor.moveToNext()) {
            Task task = new Task(
                    cursor.getInt(cursor.getColumnIndex(KEY_ID_TASKS)),
                    cursor.getInt(cursor.getColumnIndex(KEY_PROJECT_ID_TASKS)),
                    cursor.getString(cursor.getColumnIndex(Task_NAME_KEY)),
                    cursor.getLong(cursor.getColumnIndex(KEY_CREATION_TIME_STAMP_TASKS))
            );
            result.add(task);
        }
        Log.d(TAG, result.size() + " tasks gotten from " + TASKS_TABLE_NAME + " table.");
        cursor.close();
        return result;
    }

    public List<MainUIModel> getAllTasksWithProjectInfo() {
        List<MainUIModel> result = new ArrayList<>();

        Cursor cursor = getReadableDatabase().rawQuery(QUERY_GET_TASKS_WITH_PROJECT_INFO, null);


        while (cursor.moveToNext()) {
            MainUIModel model = new MainUIModel(
                    cursor.getInt(cursor.getColumnIndex(KEY_ID_TASKS)),
                    cursor.getString(cursor.getColumnIndex(Task_NAME_KEY)),
                    cursor.getLong(cursor.getColumnIndex(KEY_CREATION_TIME_STAMP_TASKS)),
                    cursor.getString(cursor.getColumnIndex(KEY_NAME_PROJECTS)),
                    cursor.getInt(cursor.getColumnIndex(KEY_COLOR_PROJECTS))
            );
            result.add(model);
        }
        cursor.close();
        return result;
    }


    public void insertTask(Task task) {
        ContentValues values = new ContentValues();
        values.put(KEY_PROJECT_ID_TASKS, task.getProjectId());
        values.put(Task_NAME_KEY, task.getName());
        values.put(KEY_CREATION_TIME_STAMP_TASKS, task.getCreationTimestamp());
        Long answer = getWritableDatabase().insert(TASKS_TABLE_NAME, null, values);
        if (answer > 0) {
            Log.i(TAG, answer + " task inserted in " + TASKS_TABLE_NAME + " table with name : " + task.getName());
        } else {
            Log.i(TAG, "No Tasks inserted.");
        }
        close();
    }

    public void deleteTask(long id) {
        getWritableDatabase().delete(TASKS_TABLE_NAME, KEY_ID_TASKS + " = ?", new String[]{String.valueOf(id)});
        close();
    }






    /**
     * Project Table
     */
    public List<Project> getAllProjects() {
        List<Project> projectList = new ArrayList<>();
        Cursor cursor = getReadableDatabase().rawQuery(QUERY_GET_ALL_PROJECTS, null);

        while (cursor.moveToNext()) {
            Project project = new Project(
                    cursor.getLong(cursor.getColumnIndex(KEY_PROJECT_ID)),
                    cursor.getString(cursor.getColumnIndex(KEY_NAME_PROJECTS)),
                    cursor.getInt(cursor.getColumnIndex(KEY_COLOR_PROJECTS))
            );
            projectList.add(project);
        }
        cursor.close();

        return projectList;
    }


    public void insertProject(Project project) {
        ContentValues values = new ContentValues();
        values.put(KEY_NAME_PROJECTS, project.getName());
        values.put(KEY_COLOR_PROJECTS, project.getColor());
        getWritableDatabase().insert(PROJECTS_TABLE_NAME, null, values);
        close();
    }


}
