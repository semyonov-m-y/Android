package ru.semenovmy.learning.todolist;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ru.semenovmy.learning.todolist.model.Goal;

public class GoalAdapter extends RecyclerView.Adapter<GoalAdapter.GoalHolder> {

    private List<Goal> mGoalList = new ArrayList<>();
    private GoalsManager mGoalsManager;

    public GoalAdapter(GoalsManager goalsManager) {
        mGoalsManager = goalsManager;
    }

    public void setGoalList(List<Goal> goalList) {
        mGoalList = goalList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public GoalHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view, parent, false);
        return new GoalHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull GoalHolder holder, int position) {
        holder.bind(mGoalList.get(position));
    }

    @Override
    public int getItemCount() {
        return mGoalList.size();
    }

    class GoalHolder extends RecyclerView.ViewHolder {

        private Goal mGoal;
        private TextView mName;
        private CheckBox mCheckBox;

        GoalHolder(@NonNull final View itemView) {
            super(itemView);
            mName = itemView.findViewById(R.id.text_view);
            mCheckBox = itemView.findViewById(R.id.check_box);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    PopupMenu menu = new PopupMenu(view.getContext(), view);
                    menu.inflate(R.menu.menu);
                    menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem menuItem) {
                            switch (menuItem.getItemId()) {
                                case R.id.menu_item_delete:
                                    mGoalsManager.deleteGoal(mGoal);
                                    break;
                                default:
                            }
                            return true;
                        }
                    });
                    menu.show();
                    return true;
                }
            });

            mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean done) {
                    if (compoundButton.getId() == R.id.check_box) {
                        mGoalsManager.setAsDone(mGoal, done);
                    }
                }
            });
        }

        void bind(Goal goal) {
            mGoal = goal;
            mName.setText(goal.getName());
            mCheckBox.setChecked(mGoal.isDone());
        }
    }

    public interface GoalsManager {
        void deleteGoal(Goal goal);
        void setAsDone(Goal goal, boolean done);
    }
}
