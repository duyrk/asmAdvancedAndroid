package com.example.asmadvancedandroid.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Database extends SQLiteOpenHelper {
    private static  Database instance;
    public static synchronized Database getInstance(Context context){
        if(instance == null)
                instance = new Database(context);
                return instance;

    }
    private Database(@Nullable Context context) {
        super(context, "myDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
            String sqlUsers = "CREATE TABLE IF NOT EXISTS USERS(ID INTEGER PRIMARY KEY AUTOINCREMENT, EMAIL TEXT, PASSWORD TEXT, ROLE INTEGER) ";
                   db.execSQL(sqlUsers);

        String sqlCourses = "CREATE TABLE IF NOT EXISTS COURSES(ID INTEGER PRIMARY KEY AUTOINCREMENT, CODE  TEXT, NAME TEXT, TIME TEXT,ROOM TEXT, AVAILABLE INTEGER ) ";
        db.execSQL(sqlCourses);
        String sqlEnrolls = "CREATE TABLE IF NOT EXISTS ENROLLS(ID INTEGER PRIMARY KEY AUTOINCREMENT, USER_ID INTEGER, COURSE_ID INTEGER, JOINED FLOAT, FOREIGN KEY (USER_ID) REFERENCES USERS(ID), FOREIGN KEY (COURSE_ID) REFERENCES COURSES(ID)) ";
        db.execSQL(sqlEnrolls);

        db.execSQL("insert into COURSES(CODE,NAME,TIME,ROOM,AVAILABLE) VALUES ('MOB201','JAVA NANG CAO','28-09-2022','309',1) ");
        db.execSQL("insert into COURSES(CODE,NAME,TIME,ROOM,AVAILABLE) VALUES ('MOB201','JAVA NANG CAO','28-09-2022','309',1) ");

        db.execSQL("insert into USERS (email, password, role) values ('hello@mysql.com', '123', 1)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
            if(i != i1){
               db.execSQL("DROP TABLE IF EXISTS ENROLLS");
                db.execSQL("DROP TABLE IF EXISTS COURSES");
                db.execSQL("DROP TABLE IF EXISTS USERS");
                onCreate(db);
            }
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        db.setForeignKeyConstraintsEnabled(true);
    }
}
