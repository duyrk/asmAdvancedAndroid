package com.example.asmadvancedandroid.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.asmadvancedandroid.Database.Database;
import com.example.asmadvancedandroid.models.AppUser;

public class UserDAO {

    private Database database;
    public UserDAO(Context context){
        database = Database.getInstance(context);
    }

    public boolean register(String email, String password, Integer role){
        Boolean result = false;
        SQLiteDatabase db = database.getWritableDatabase();
        db.beginTransaction();
        try{
            ContentValues values = new ContentValues();
            values.put("EMAIL",email); //check unique email
            values.put("PASSWORD",password); //password hash
            values.put("ROLE",role);
            long rows = db.insert("USERS",null,values);
            result = rows >=1;
        db.setTransactionSuccessful();
        }catch (Exception e){
            Log.d(">>>Loi","register : "+e.getMessage());
        }finally {
        db.endTransaction();
        }

        return  result;


    }
    public AppUser login(String email, String password){
        AppUser appUser = null;
        SQLiteDatabase db = database.getReadableDatabase();
        String sql = "SELECT ID, EMAIL,PASSWORD, ROLE FROM USERS WHERE EMAIL = ?";
        Cursor cursor = db.rawQuery(sql,new String[]{email});
        Log.d("check login",email);
        try{
            if(cursor.moveToFirst()){
                while(!cursor.isAfterLast()){
                    Integer _id = cursor.getInt(0);
                    String _email = cursor.getString(1);
                    String _password = cursor.getString(2);
                    Integer _role = cursor.getInt(3);
                    Log.d("check pass",password+"and"+_password);

                   if(_password == null){
                       if (password != _password){ Log.d(">>>>>>>>check pass:","dmmmmmm"); break;}
                   }else{
                       if (!password.equals(_password)){break;}
                   }

                    appUser = new AppUser(_id,_role,_email,_password);

                    cursor.moveToNext();
                }

            }
        }catch(Exception e){
            Log.d(">>>>>TAG","login:"+e.getMessage());
        }finally {
            if(cursor !=null && !cursor.isClosed()) cursor.close();
        }
        return appUser;
    }
}
