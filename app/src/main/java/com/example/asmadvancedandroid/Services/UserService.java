package com.example.asmadvancedandroid.Services;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.util.Log;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.asmadvancedandroid.DAO.UserDAO;
import com.example.asmadvancedandroid.models.AppUser;

public class UserService extends IntentService {

    private UserDAO userDAO = new UserDAO(this);
    public static final String USER_SERVICE_EVENT = "USER_SERVICE_EVENT";
    public static final String USER_SERVICE_ACTION_LOGIN = "USER_SERVICE_ACTION_LOGIN";
    public static final String USER_SERVICE_ACTION_REGISTER = "USER_SERVICE_ACTION_REGISTER";


    public UserService() {
        super("UserService");
    }




    @Override
    protected void onHandleIntent(Intent intent) {
        if(intent != null){

            String action = intent.getAction();

            switch (action){

                case USER_SERVICE_ACTION_LOGIN:{
                    Log.d(">>>>>>>>>>>TAG dao","chay nef");
                    String email = intent.getStringExtra("email");
                    String password = intent.getStringExtra("password");
                    Log.d(">TAGGGGGGGGGGGGGGGG DAO",email+"password"+password);
                    AppUser appUser = userDAO.login(email,password);

                    Intent outIntent = new Intent(USER_SERVICE_EVENT);
                    outIntent.putExtra("action",USER_SERVICE_ACTION_LOGIN);
                    outIntent.putExtra("appUser",appUser);

                    LocalBroadcastManager.getInstance(this).sendBroadcast(outIntent);

                    break;
                }


                case USER_SERVICE_ACTION_REGISTER:{
                    String email = intent.getStringExtra("email");
                    String password = intent.getStringExtra("password");
                    Integer role = intent.getIntExtra("role",0);
                    Boolean result = userDAO.register(email,password,role);
                    Intent outIntent = new Intent(USER_SERVICE_EVENT);
                    outIntent.putExtra("action",USER_SERVICE_ACTION_REGISTER);
                    outIntent.putExtra("result",result);
                    LocalBroadcastManager.getInstance(this).sendBroadcast(outIntent);


                    break;
                }
                default:break;
            }




        }
    }

}