package ru.semenovmy.learning.todolist.model;

public class Goal {

    private long mId_;
    private String mName;
    private boolean mDone;

    public Goal(String name) {
        mName = name;
        mDone = false;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public boolean isDone() {
        return mDone;
    }

    public void setDone(boolean done) {
        mDone = done;
    }

    public long getId_() {
        return mId_;
    }

    public void setId_(long id_) {
        mId_ = id_;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Goal goal = (Goal) o;

        if (mId_ != goal.mId_) return false;
        if (mDone != goal.mDone) return false;
        return mName != null ? mName.equals(goal.mName) : goal.mName == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (mId_ ^ (mId_ >>> 32));
        result = 31 * result + (mName != null ? mName.hashCode() : 0);
        result = 31 * result + (mDone ? 1 : 0);
        return result;
    }
}
