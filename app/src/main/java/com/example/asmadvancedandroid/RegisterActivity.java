package com.example.asmadvancedandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.asmadvancedandroid.Services.UserService;

public class RegisterActivity extends AppCompatActivity {
    EditText edtEmail, edtPassword, edtCFPassword;
    Button btnRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        edtEmail = findViewById(R.id.register_username);
        edtPassword = findViewById(R.id.register_password);
        edtCFPassword = findViewById(R.id.register_cfpassword);
        btnRegister = findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onRegisterClick(view);
            }
        });
    }
    public void onRegisterClick(View view){
        String email = edtEmail.getText().toString();
        String password = edtPassword.getText().toString();
        Integer role = 0;
        Intent intent = new Intent(RegisterActivity.this,UserService.class);
        intent.setAction(UserService.USER_SERVICE_ACTION_REGISTER);//
        intent.putExtra("email",email);
        intent.putExtra("password",password);
        intent.putExtra("role",role);
        startService(intent);
    }
    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter(UserService.USER_SERVICE_EVENT);
        LocalBroadcastManager.getInstance(this).registerReceiver(registerReceiver   ,intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(registerReceiver);
    }
    BroadcastReceiver registerReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getStringExtra("action");
            if(action.equals(UserService.USER_SERVICE_ACTION_REGISTER)){
                Boolean result = intent.getBooleanExtra("result",false);
                if(result){
                    Toast.makeText(RegisterActivity.this, "Đăng ký thành công", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(RegisterActivity.this, "Đăng ký không thành công", Toast.LENGTH_LONG).show();
                }
            }
        }
    };
}