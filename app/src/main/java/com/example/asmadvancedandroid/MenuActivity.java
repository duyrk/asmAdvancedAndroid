package com.example.asmadvancedandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.asmadvancedandroid.Services.UserService;
import com.example.asmadvancedandroid.models.AppUser;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONException;
import org.json.JSONObject;

public class MenuActivity extends AppCompatActivity {
    LinearLayout goCourses, goNews, goMaps, goSocial;
    Button btnLogout;
    Toolbar toolbar;
    GoogleSignInClient gsc;
    GoogleSignInAccount account;
    private  AppUser result;
    private String temp=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        goCourses = findViewById(R.id.goCoursesActivity);
        goMaps = findViewById(R.id.goMapsActivity);
        goNews = findViewById(R.id.goNewsActivity);
        goSocial = findViewById(R.id.goSocialAcivity);
        toolbar = findViewById(R.id.toolbar1);

        //google
        GoogleSignInOptions gso = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        gsc = GoogleSignIn.getClient(MenuActivity.this,gso);
        account = GoogleSignIn.getLastSignedInAccount(MenuActivity.this);
        //end google


        //facebook

        AccessToken accessToken = AccessToken.getCurrentAccessToken();
            if(accessToken!=null && accessToken.isExpired()==false){   //phai dang nhap bang facebook moi dc lay name tu facebook
                GraphRequest request = GraphRequest.newMeRequest(
                        accessToken,
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(
                                    JSONObject object,
                                    GraphResponse response) {
                                // Application code
                                try {
                                    String name = object.getString("name");
                                    Log.d(">>>>>>>>TAG FACEBOOK","NAME facebook:"+name);
                                    String email = object.getString("email");
                                    Log.d(">>>>>>>TAG FACEBOOk","EMAIL:"+email);
                                    onLoginWithGGCLick(email);
                                    temp = email;
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,link,email");
                request.setParameters(parameters);
                request.executeAsync();

            }


















        setSupportActionBar(toolbar);

        goCourses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getSharedPreferences("LOGIN_STATUS",MODE_PRIVATE);
               int role = sharedPreferences.getInt("role",0);

                if(role == 1){
                    Intent intent = new Intent(MenuActivity.this,FireBaseActivity.class);
                    startActivity(intent);
                }else {
                    Intent intent = new Intent(MenuActivity.this, coursesActivity.class);
                    startActivity(intent);
                }
            }
        });
        goMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent intent = new Intent(MenuActivity.this,MapActivity.class);
                    startActivity(intent);
            }
        });
        goNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent intent = new Intent(MenuActivity.this,newsActivity.class);
                    startActivity(intent);
            }
        });
        goSocial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
//        btnLogout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                onLogout(view);
//            }
//        });
    }
    public void onLoginWithGGCLick(String _email){
//        IntentFilter intentFilter = new IntentFilter(UserService.USER_SERVICE_EVENT);
//        LocalBroadcastManager.getInstance(this).registerReceiver(loginReceiver,intentFilter);

        Intent intent = new Intent(MenuActivity.this,UserService.class);
        intent.putExtra("email",_email);
        intent.putExtra("password", (String)null);
        intent.setAction(UserService.USER_SERVICE_ACTION_LOGIN);
        startService(intent);
        Log.d(">>>>>>>>>>>>>TAG","loginggrunning");

    }
    public void regWithGG(String _email){
        String email = _email;
        String password = null;
        Integer role = 0 ;
        Intent intent = new Intent(MenuActivity.this,UserService.class);
        intent.setAction(UserService.USER_SERVICE_ACTION_REGISTER);
        intent.putExtra("email",email);
        intent.putExtra("password",password);
        intent.putExtra("role",role);
        startService(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(">TAGGGG","resume");
        IntentFilter intentFilter = new IntentFilter(UserService.USER_SERVICE_EVENT);
        LocalBroadcastManager.getInstance(this).registerReceiver(loginReceiver,intentFilter);
        IntentFilter intentFilter1 = new IntentFilter(UserService.USER_SERVICE_EVENT);
        LocalBroadcastManager.getInstance(this).registerReceiver(registerReceiver,intentFilter1);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(">TAGGGG","onpause");
        LocalBroadcastManager.getInstance(this).unregisterReceiver(loginReceiver);
        LocalBroadcastManager.getInstance(this).unregisterReceiver(registerReceiver);
    }

    private BroadcastReceiver registerReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

        }
    };
    private BroadcastReceiver loginReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(">>>>>>>>>>>LOG","onReceive");
            String action = intent.getStringExtra("action");
     if(action.equals(UserService.USER_SERVICE_ACTION_LOGIN)){
                Log.d(">>>>>>>>>>>>TAG","loginGG");
                 result = (AppUser) intent.getSerializableExtra("appUser");
                if(result == null){
                    regWithGG(temp);
                }else{
                    Log.d(">>>>>>>>>>>>>>>>>>>TAG","not do anything");
                }
            }
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbtnlogout,menu);
        return super.onCreateOptionsMenu(menu);


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.btnLogout){
            new AlertDialog.Builder(this)
                    .setTitle("Xác nhận đăng xuất")
                    .setMessage("Bạn có chắc là muốn đăng xuất?")
                    .setIcon(R.drawable.ic_baseline_power_settings_new_24)
                            .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    onLogout(null);
                                }
                            })
                    .setNegativeButton("Không", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    }).show();

        }

        return super.onOptionsItemSelected(item);
    }

    public void onLogout(View view){
        SharedPreferences sharedPreferences = getSharedPreferences("LOGIN_STATUS",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("isLoggedIn");
        editor.remove("id");
        editor.remove("role");
        editor.remove("email");
        editor.apply();


        //google
        //kiểm tra xem có account google không ? Có thì đăng xuất google, không thì chạy vào else đăng xuất bình thường
        if(account!=null){
            gsc.signOut()
                    .addOnCompleteListener(MenuActivity.this,
                            new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Intent goLogin = new Intent(MenuActivity.this,LoginActivity.class);
                                    startActivity(goLogin);
                                    finish();
                                }
                            });
        }else{
            Intent goLogin = new Intent(MenuActivity.this,LoginActivity.class);
            startActivity(goLogin);
            finish();
        }
// facebook
        LoginManager.getInstance().logOut();
        Intent goLogin = new Intent(MenuActivity.this,LoginActivity.class);
        startActivity(goLogin);
        finish();

    }
}