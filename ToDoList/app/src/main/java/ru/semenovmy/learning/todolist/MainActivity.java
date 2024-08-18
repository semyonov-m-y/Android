package ru.semenovmy.learning.todolist;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.BaseColumns;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import ru.semenovmy.learning.todolist.database.DatabaseHelper;
import ru.semenovmy.learning.todolist.database.DatabaseSchema;
import ru.semenovmy.learning.todolist.model.Goal;

public class MainActivity extends AppCompatActivity implements AddListener, GoalAdapter.GoalsManager {

    private final ExecutorService mExecutorService = Executors.newSingleThreadExecutor();

    private FloatingActionButton mFloatingActionButton;
    private RecyclerView mRecyclerView;
    private GoalAdapter mGoalAdapter;
    private List<Goal> mGoals;
    private SQLiteDatabase mSqLiteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads()
                .detectDiskWrites()
                .penaltyDialog()
                .build());

        mExecutorService.execute(new Runnable() {
            @Override
            public void run() {
                mSqLiteDatabase = new DatabaseHelper(MainActivity.this).getWritableDatabase();
                initData();
            }
        });
    }

    private void initViews() {
        mFloatingActionButton = findViewById(R.id.floating_action_button);
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dialog = new AddDialog();
                dialog.show(getSupportFragmentManager(), null);
            }
        });
        mRecyclerView = findViewById(R.id.recycler_view);
        mGoalAdapter = new GoalAdapter(this);
        mRecyclerView.setAdapter(mGoalAdapter);
    }

    private void initData() {
        try (Cursor cursor = mSqLiteDatabase.query(
                DatabaseSchema.GoalTable.NAME,
                new String[]{BaseColumns._ID, DatabaseSchema.GoalTable.Columns.NAME, DatabaseSchema.GoalTable.Columns.DONE},
                null,
                null,
                null,
                null,
                null
        )) {
            List<Goal> goals = new ArrayList<>();
            while (cursor.moveToNext()) {
                Goal goal = new Goal(cursor.getString(cursor.getColumnIndex(DatabaseSchema.GoalTable.Columns.NAME)));
                goal.setId_(cursor.getLong(cursor.getColumnIndex(BaseColumns._ID)));

                int done = cursor.getInt(cursor.getColumnIndex(DatabaseSchema.GoalTable.Columns.DONE));
                if (done == 0) {
                    goal.setDone(false);
                } else if (done == 1) {
                    goal.setDone(true);
                }

                goals.add(goal);
            }
            mGoals = new ArrayList<>(goals);
            setGoalsList();
        }
    }

    private void setGoalsList() {
        mRecyclerView.post(new Runnable() {
            @Override
            public void run() {
                mGoalAdapter.setGoalList(mGoals);
            }
        });
    }

    @Override
    public void addGoal(final String name) {
        mExecutorService.execute(new Runnable() {
            @Override
            public void run() {
                Goal goal = new Goal(name);
                ContentValues cv = new ContentValues();
                cv.put(DatabaseSchema.GoalTable.Columns.NAME, goal.getName());
                goal.setId_(mSqLiteDatabase.insert(DatabaseSchema.GoalTable.NAME, null, cv));
                initData();
            }
        });
    }

    @Override
    public void deleteGoal(final Goal goal) {
        mExecutorService.execute(new Runnable() {
            @Override
            public void run() {
                mSqLiteDatabase.delete(DatabaseSchema.GoalTable.NAME,
                        BaseColumns._ID + " = ?", new String[]{String.valueOf(goal.getId_())});
                initData();
            }
        });
    }

    @Override
    public void setAsDone(final Goal goal, final boolean done) {
        mExecutorService.execute(new Runnable() {
            @Override
            public void run() {
                Goal updatedGoal = new Goal(goal.getName());
                updatedGoal.setId_(goal.getId_());
                updatedGoal.setDone(done);
                ContentValues cv = new ContentValues();
                cv.put(DatabaseSchema.GoalTable.Columns.NAME, goal.getName());
                cv.put(DatabaseSchema.GoalTable.Columns.DONE, done);
                mSqLiteDatabase.update(DatabaseSchema.GoalTable.NAME,
                        cv, BaseColumns._ID + " = ?", new String[]{String.valueOf(goal.getId_())});
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSqLiteDatabase.close();
    }
}


