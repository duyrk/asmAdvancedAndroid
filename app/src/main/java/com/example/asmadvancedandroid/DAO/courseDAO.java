package com.example.asmadvancedandroid.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.asmadvancedandroid.Database.Database;
import com.example.asmadvancedandroid.models.AppCourse;
import com.example.asmadvancedandroid.models.AppUser;

import java.util.ArrayList;
import java.util.List;

public class courseDAO {
    private Database database;
    public courseDAO(Context context){
        database = Database.getInstance(context);
    }
    public List<AppCourse> getAll(){
        List<AppCourse> list = new ArrayList<>();
        SQLiteDatabase db = database.getReadableDatabase();
        String sql = "SELECT ID, CODE, NAME, TIME, ROOM, AVAILABLE FROM COURSES";
        Cursor cursor = db.rawQuery(sql,null);
        try{
            if(cursor.moveToFirst()){
                while(!cursor.isAfterLast()){
                    Integer _id = cursor.getInt(0);
                    String _code = cursor.getString(1);
                    String _name = cursor.getString(2);
                    String _time = cursor.getString(3);
                    String _room = cursor.getString(4);
                    Integer _available =  cursor.getInt(5);
                    AppCourse appCourse = new AppCourse(_id,_available,_code,_name,_time,_room);
                    list.add(appCourse);
                    cursor.moveToNext();
                }

            }
        }catch(Exception e){
            Log.d(">>>>>TAG","login:"+e.getMessage());
        }finally {
            if(cursor !=null && !cursor.isClosed()) cursor.close();
        }


        return list;
    }
    public Boolean insert(AppCourse course){
        Boolean result =false;
        SQLiteDatabase db = database.getWritableDatabase();
        db.beginTransaction();
        try{
            ContentValues values = new ContentValues();
            values.put("CODE",course.getCode());
            values.put("NAME",course.getName());
            values.put("TIME",course.getTime());
            values.put("ROOM",course.getRoom());
            values.put("AVAILABLE",course.getAvailable());
            long rows = db.insert("COURSES",null,values);
            result = rows >=1;
            db.setTransactionSuccessful();
        }catch (Exception e){
            Log.d(">>>Loi","insert : "+e.getMessage());
        }finally {
            db.endTransaction();
        }


        return result;
    }

    public Boolean update(AppCourse course){
        Boolean result =false;
        SQLiteDatabase db = database.getWritableDatabase();
        db.beginTransaction();
        try{
            ContentValues values = new ContentValues();
            values.put("CODE",course.getCode());
            values.put("NAME",course.getName());
            values.put("TIME",course.getTime());
            values.put("ROOM",course.getRoom());
            values.put("AVAILABLE",course.getAvailable());
            long rows = db.update("COURSES",values,"ID = ?",new String[]{course.getId().toString()});
            result = rows >=1;
            db.setTransactionSuccessful();
        }catch (Exception e){
            Log.d(">>>Loi","insert : "+e.getMessage());
        }finally {
            db.endTransaction();
        }


        return result;
    }
    public Boolean delete(Integer id){
        Boolean result = false;
        SQLiteDatabase db = database.getWritableDatabase();
        db.beginTransaction();
        try{
            long rows = db.delete("COURSES","ID = ?",new String[]{id.toString()});
            result = rows >=1;
            db.setTransactionSuccessful();
        }catch (Exception e){
            Log.d(">>>Loi","insert : "+e.getMessage());
        }finally {
            db.endTransaction();
        }

        return result;

    }
}
