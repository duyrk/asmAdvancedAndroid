package com.example.asmadvancedandroid;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.asmadvancedandroid.Services.UserService;
import com.example.asmadvancedandroid.models.AppUser;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import java.util.Arrays;

public class LoginActivity extends AppCompatActivity {
    EditText edtUsername,edtPassword;
    Button btnLogin,btnGoRegister;
    CallbackManager callbackManager;
    LoginButton fbLoginButton;
   private int flag = -1;
   private String temp=null;
    private static final String EMAIL = "email";
    //google
    GoogleSignInClient gsc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("TAg","onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        readLogin();
        edtPassword = findViewById(R.id.password);
        edtUsername = findViewById(R.id.username);
        btnLogin = findViewById(R.id.btnLogin);
        btnGoRegister = findViewById(R.id.btnShowRegisterAc);
        fbLoginButton = findViewById(R.id.fb_login_button);


/*
Thêm tính năng đăng nhập google
sử dụng firebase project: https://console.firebase.google.com/project/asm001-e1852/overview


*/
        //google
        GoogleSignInOptions gso = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        gsc = GoogleSignIn.getClient(LoginActivity.this,gso);
        // kiểm tra có login hay chưa
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(LoginActivity.this);

        if(account!=null){
            //chuyển qua home
            Intent homeIntent = new Intent(LoginActivity.this,MenuActivity.class);
            startActivity(homeIntent);
            finish();
        }
        SignInButton sib = findViewById(R.id.btnGoogle);
        sib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent googleIntent = gsc.getSignInIntent();
                    googleLauncher.launch(googleIntent);

            }
        });
        //end google


        //facebook


        //neu account login san thi chay thang vao menu
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        if(accessToken!=null && accessToken.isExpired()==false){
            Intent homeIntent = new Intent(LoginActivity.this,MenuActivity.class);
            startActivity(homeIntent);
            finish();
        }

        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        // App code
                        //chay qua man hinh home
                        Log.d(">>>>>>>>>>TAG","onSuccess facebook: working");
                        Intent homeIntent = new Intent(LoginActivity.this,MenuActivity.class);
                        startActivity(homeIntent);
                        finish();
                    }

                    @Override
                    public void onCancel() {
                        // App code
                        Log.d(">>>>>>>>>>TAG","onCancel facebook: working");
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                        Log.d(">>>>>>>>>>TAG","onError facebook: working");
                    }
                });

        fbLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginManager.getInstance().logInWithReadPermissions(LoginActivity.this, Arrays.asList("public_profile"));
                LoginManager.getInstance().logInWithReadPermissions(LoginActivity.this,Arrays.asList("email"));
            }
        });

        //end fb
        btnGoRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onGoRegisterClick(view);
            }
        });
btnLogin.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        onLoginClick(view);
    }
});


    }
 //fb activity result
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    //google activity result
    ActivityResultLauncher<Intent> googleLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                        Intent data = result.getData();
                    Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                    try {
                        GoogleSignInAccount account = task.getResult(ApiException.class);
                        String email = account.getEmail();
                        String name = account.getDisplayName();
                        Log.d(">>>>>>>>>TAG","onActivityResult:"+email);
                        Log.d(">>>>>>>>>TAG","onActivityResult:"+name);
                        onLoginWithGGCLick(email);
                        temp = email;
                        //qua home
                        Intent homeIntent = new Intent(LoginActivity.this,MenuActivity.class);
                        startActivity(homeIntent);
                        finish();
                    }catch (Exception e){
                        Log.d(">>>>>>>>>ERROR TAG","onActivity error "+e.getMessage());
                    }
                }
            }
    );
        public void onLoginWithGGCLick(String _email){
            IntentFilter intentFilter = new IntentFilter(UserService.USER_SERVICE_EVENT);
            LocalBroadcastManager.getInstance(this).registerReceiver(loginReceiver,intentFilter);
            flag=1;
            Intent intent = new Intent(LoginActivity.this,UserService.class);
            intent.putExtra("email",_email);
            intent.putExtra("password", (String)null);
            intent.setAction(UserService.USER_SERVICE_ACTION_LOGIN);
            startService(intent);
            Log.d(">>>>>>>>>>>>>TAG","loginggrunning"+flag);

        }
        public void regWithGG(String _email){
                String email = _email;
                String password = null;
                Integer role = 0 ;
            Intent intent = new Intent(LoginActivity.this,UserService.class);
            intent.setAction(UserService.USER_SERVICE_ACTION_REGISTER);
            intent.putExtra("email",email);
            intent.putExtra("password",password);
            intent.putExtra("role",role);
            startService(intent);
        }
        public void onLoginClick(View view){
            flag=0;
        String username = edtUsername.getText().toString();
        String password = edtPassword.getText().toString();
        Intent intent = new Intent(this,UserService.class);
        intent.putExtra("email",username);
        intent.putExtra("password",password);
        intent.setAction(UserService.USER_SERVICE_ACTION_LOGIN);
        startService(intent);
        }
        public void onGoRegisterClick(View view){
                Intent intent = new Intent(this,RegisterActivity.class);
                startActivity(intent);
        }
    @Override
    protected void onResume() {
        super.onResume();
        // kiểm tra trạng thái login
        readLogin();
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
                Log.d(">>>>>>>>>>>LOG",""+flag);
                String action = intent.getStringExtra("action");
                if(action.equals(UserService.USER_SERVICE_ACTION_LOGIN) && flag==0) {
                    AppUser result = (AppUser) intent.getSerializableExtra("appUser");
                    if (result == null) {
                        Toast.makeText(LoginActivity.this, "Đăng nhập không thành công", Toast.LENGTH_LONG).show();
                    } else {
                        writeLogin(result);
                        Intent menuIntent = new Intent(LoginActivity.this, MenuActivity.class);
                        startActivity(menuIntent);
                        finish();

                    }
                }else if(action.equals(UserService.USER_SERVICE_ACTION_LOGIN) && flag==1){
                Log.d(">>>>>>>>>>>>TAG","loginGG");
                AppUser result = (AppUser) intent.getSerializableExtra("appUser");
                if(result == null){
                    regWithGG(temp);
                }else{
                    Log.d(">>>>>>>>>>>>>>>>>>>TAG","not do anything");
                }
            }
        }
    };
    public void readLogin(){
        SharedPreferences preferences = getSharedPreferences("LOGIN_STATUS",MODE_PRIVATE);
        Boolean isLoggedIn = preferences.getBoolean("isLoggedIn",false);
        if(isLoggedIn){
            Intent homeIntent = new Intent(LoginActivity.this,MenuActivity.class);
            startActivity(homeIntent);
            finish();
        }
    }
    public void writeLogin(AppUser appUser){
        SharedPreferences sharedPreferences = getSharedPreferences("LOGIN_STATUS",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isLoggedIn",true);
        editor.putInt("id",appUser.getId());
        editor.putInt("role",appUser.getRole());
        editor.putString("email",appUser.getEmail());
        editor.commit();
    }
}