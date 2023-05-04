package com.example.asmadvancedandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.asmadvancedandroid.adapters.IAdapterClickEvent;
import com.example.asmadvancedandroid.fragments.course_schedule_fragment;
import com.example.asmadvancedandroid.models.AppCourse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FireBaseActivity extends AppCompatActivity implements IAdapterClickEvent {
    private EditText txtTenMon, txtMaMon, txtPhongHoc, txtGioMon;
    private Button btnLuu, btnHuy;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private AppCourse course = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fire_base);
        txtTenMon = findViewById(R.id.txtTenMon);
        txtMaMon = findViewById(R.id.txtMaMon);
        txtPhongHoc = findViewById(R.id.txtPhongHoc);
        txtGioMon = findViewById(R.id.txtGioHoc);
        btnLuu = findViewById(R.id.btnLuu);
        btnHuy = findViewById(R.id.btnHuy);


    }

    @Override
    protected void onResume() {
        super.onResume();
        onReadData();
    }

    public void onSaveClick(View view){
        String name = txtTenMon.getText().toString();
        String code = txtMaMon.getText().toString();
        String room = txtPhongHoc.getText().toString();
        String time = txtGioMon.getText().toString();
        Map<String,Object> item = new HashMap<>();
        item.put("name",name);
        item.put("code",code);
        item.put("room",room);
        item.put("time",time);
        if (course==null){
            db.collection("courses")
                    .add(item)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Toast.makeText(FireBaseActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                            onReadData();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(FireBaseActivity.this, "Thêm không thành công", Toast.LENGTH_SHORT).show();
                        }
                    });
        }else{
            db.collection("courses")
                    .document(course.getCourseId())
                    .set(item)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            onCancelClick(null);
                            Toast.makeText(FireBaseActivity.this, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
                            onReadData();
                            course=null;
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(FireBaseActivity.this, "Cập nhật không thành công", Toast.LENGTH_SHORT).show();
                            course=null;
                        }
                    });
        }





    }
    public void onCancelClick(View view){
        txtMaMon.setText(null);
        txtTenMon.setText(null);
        txtGioMon.setText(null);
        txtPhongHoc.setText(null);
    }
    public void onReadData(){
        db.collection("courses")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            ArrayList<AppCourse> list = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                Map<String, Object> map = document.getData();
                                String name = map.get("name").toString();
                                String code = map.get("code").toString();
                                String room = map.get("room").toString();
                                String time = map.get("time").toString();
                                AppCourse course = new AppCourse(-1,1,code,name,time,room);
                                course.setCourseId(document.getId());
                                list.add(course);
                            }
                            getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.courseFrag, course_schedule_fragment.newInstance(list))
                                    .commit();
                        } else {

                        }
                    }
                });
    }

    @Override
    public void onEditCourseClick(AppCourse course) {
            txtMaMon.setText(course.getCode());
            txtTenMon.setText(course.getName());
            txtPhongHoc.setText(course.getRoom());
            txtGioMon.setText(course.getTime());
            this.course = course;
    }

    @Override
    public void onDeleteCourseClick(AppCourse course) {
        new AlertDialog.Builder(FireBaseActivity.this)
                .setTitle("Xác nhận")
                .setMessage("Xóa không phục hồi được!")
                .setNegativeButton("Hủy",null)
                .setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        db.collection("courses").document(course.getCourseId())
                                .delete()
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                       Toast.makeText(FireBaseActivity.this,"Xóa thành công!",Toast.LENGTH_SHORT).show();
                                       onReadData();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(FireBaseActivity.this,"Xóa không thành công!",Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                }).show();
    }

}