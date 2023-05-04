package com.example.asmadvancedandroid.Services;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.asmadvancedandroid.DAO.courseDAO;
import com.example.asmadvancedandroid.models.AppCourse;

import java.util.ArrayList;


public class CourseService extends IntentService {
    public static final String COURSE_SERVICE_EVENT = "COURSE_SERVICE_EVENT";
    public static final String COURSE_SERVICE_INSERT = "COURSE_SERVICE_INSERT";
    public static final String COURSE_SERVICE_UPDATE = "COURSE_SERVICE_UPDATE";
    public static final String COURSE_SERVICE_DELETE = "COURSE_SERVICE_DELETE";
    public static final String COURSE_SERVICE_GET_ALL = "COURSE_SERVICE_GET_ALL";
    private courseDAO coursedao;

    public CourseService() {
        super("CourseService");
        coursedao = new courseDAO(this);
    }



    @Override
    protected void onHandleIntent(Intent intent) {
        if(intent != null){
            String action = intent.getAction();
            switch (action){
                case COURSE_SERVICE_INSERT:{
                    String code = intent.getStringExtra("code");
                    String name = intent.getStringExtra("name");
                    String time = intent.getStringExtra("time");
                    String room = intent.getStringExtra("room");
                    Integer available = intent.getIntExtra("available",1);
                    AppCourse course = new AppCourse(-1,available,code,name,time,room);
                    Boolean result = coursedao.insert(course);
                    Intent outIntent = new Intent(COURSE_SERVICE_EVENT);
                    outIntent.putExtra("result",result);
                    LocalBroadcastManager.getInstance(this).sendBroadcast(outIntent);
                    break;
                }
                case COURSE_SERVICE_UPDATE:{
                    int id = intent.getIntExtra("id",0);
                    String code = intent.getStringExtra("code");
                    String name = intent.getStringExtra("name");
                    String time = intent.getStringExtra("time");
                    String room = intent.getStringExtra("room");
                    Integer available = intent.getIntExtra("available",1);
                    AppCourse course = new AppCourse(id,available,code,name,time,room);
                    Boolean result = coursedao.update(course);
                    Intent outIntent = new Intent(COURSE_SERVICE_EVENT);
                    outIntent.putExtra("result",result);
                    LocalBroadcastManager.getInstance(this).sendBroadcast(outIntent);

                    break;
                }
                case COURSE_SERVICE_DELETE:{
                            Integer id = intent.getIntExtra("id",0);
                            if (id>0){
                                Boolean res = coursedao.delete(id);
                                if (res==true){
                                    ArrayList<AppCourse> result = (ArrayList<AppCourse>) coursedao.getAll();
                                    Intent outIntent = new Intent(COURSE_SERVICE_EVENT);
                                    outIntent.putExtra("result",result);
                                    LocalBroadcastManager.getInstance(this).sendBroadcast(outIntent);
                                }
                            }
                    break;
                }
                case COURSE_SERVICE_GET_ALL:{
                    ArrayList<AppCourse> result = (ArrayList<AppCourse>) coursedao.getAll();
                    Intent outIntent = new Intent(COURSE_SERVICE_EVENT);
                    outIntent.putExtra("result",result);
                    LocalBroadcastManager.getInstance(this).sendBroadcast(outIntent);
                    break;
                }
            }
        }
    }


}