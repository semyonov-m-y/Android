package ru.semenovmy.learning.todolist.database;

public class DatabaseSchema {

    public static final class GoalTable {

        public static final String NAME = "goal";

        public static final class Columns {

            public static final String NAME = "name";
            public static final String DONE = "done";
        }
    }
}
