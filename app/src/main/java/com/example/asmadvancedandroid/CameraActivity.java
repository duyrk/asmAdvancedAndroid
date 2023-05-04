package com.example.asmadvancedandroid;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.BitSet;
import java.util.Calendar;

public class CameraActivity extends AppCompatActivity {
    int SELECT_PICTURE = 200;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

    }
    public void chupHinh(View view){
        Boolean isPermissionAllowed  = ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
        if(isPermissionAllowed){
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            photoTakenResult.launch(intent);
        }else{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.CAMERA},100);
            }
        }


    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 100:{
                if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    photoTakenResult.launch(intent);

                }else{
                    Toast.makeText(this,"Không cho phép sử dụng camera",Toast.LENGTH_LONG).show();
                }
                break;
            }

            default:
                break;

        }
    }
    ActivityResultLauncher<Intent> photoTakenResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                        Intent intent = result.getData();
                    Bitmap bitmap = (Bitmap) intent.getExtras().get("data");
                    ImageView iv1 = findViewById(R.id.iv1);
                    iv1.setImageBitmap(bitmap);
                    upload(bitmap);
                }
            }
    );
    public void chonHinh(View view){
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
//        ketquaChonHinh.launch(i);
        startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            if (requestCode == SELECT_PICTURE){
                Uri selectedImageUri = data.getData();
                if(null != selectedImageUri){
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(),selectedImageUri);
                        upload(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        }
    }

    //    ActivityResultLauncher<Intent> ketquaChonHinh = registerForActivityResult(
//            new ActivityResultContracts.StartActivityForResult(),
//            new ActivityResultCallback<ActivityResult>() {
//                @Override
//                public void onActivityResult(ActivityResult result) {
//                    Intent intent = result.getData();
//                    Bitmap bitmap = (Bitmap) intent.getExtras().get("Select Picture");
//                    upload(bitmap);
//                }
//            }
//
//    );
    //firebase storage
    private void upload(Bitmap bitmap){
        Calendar calendar = Calendar.getInstance();
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageReference = storage.getReference();
        StorageReference imageReference = storageReference.child(calendar.getTimeInMillis()+".jpg");
        ByteArrayOutputStream outputStream =  new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,outputStream);
        byte[] bytes = outputStream.toByteArray();
        UploadTask uploadTask = imageReference.putBytes(bytes);
        uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot,
                Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (task.isSuccessful()){
                    return imageReference.getDownloadUrl();
                }else {
                    return null;
                }
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()){
                    Uri downloadUri = task.getResult();
                    ImageView iv2 = findViewById(R.id.iv2);
                    Glide.with(CameraActivity.this)
                            .load(downloadUri)
                            .into(iv2);
                }
            }
        });
    }
}