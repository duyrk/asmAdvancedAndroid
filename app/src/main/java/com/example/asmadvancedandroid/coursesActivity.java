package com.example.asmadvancedandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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
import android.view.MenuItem;
import android.widget.Toast;

import com.example.asmadvancedandroid.Services.CourseService;
import com.example.asmadvancedandroid.adapters.courseAdapter;
import com.example.asmadvancedandroid.fragments.course_exam_fragment;
import com.example.asmadvancedandroid.fragments.course_home_fragment;
import com.example.asmadvancedandroid.fragments.course_registration_fragment;
import com.example.asmadvancedandroid.fragments.course_schedule_fragment;
import com.example.asmadvancedandroid.models.AppCourse;
import com.example.asmadvancedandroid.models.AppEnroll;
import com.example.asmadvancedandroid.models.MyCourseModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class coursesActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{
    BottomNavigationView bottomNavigationView;
    courseAdapter cadapter;
    ArrayList<AppCourse> myCourse;
    ArrayList<AppEnroll> myEnroll;
    AppCourse course1;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
   private Integer id ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setOnNavigationItemSelectedListener((BottomNavigationView.OnNavigationItemSelectedListener) this);
        bottomNavigationView.setSelectedItemId(R.id.courseFrag);

        Log.d(">>>>>>TAG","MY ID ON FIREBASE"+id);
        SharedPreferences sharedPreferences = getSharedPreferences("LOGIN_STATUS",MODE_PRIVATE);
        id =  sharedPreferences.getInt("id",0);
        readData(id);
    }
    course_home_fragment home_fragment = new course_home_fragment();
    course_registration_fragment registration_fragment = new course_registration_fragment();
    course_exam_fragment exam_fragment = new course_exam_fragment();
//    course_schedule_fragment schedule_fragment = new course_schedule_fragment();
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){




            case R.id.scheduleFrag: {

                onReadData();
                return true;

            }
            case R.id.examFrag: {

                getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, course_exam_fragment.newInstance(myCourse,myEnroll)).commit();
                readData(id);
                return true;
            }
        }
        return false;
    }

//    public void onGetCourse(){
//        Intent intent = new Intent(this, CourseService.class);
//        intent.setAction(CourseService.COURSE_SERVICE_GET_ALL);
//        startService(intent);
//    }

    @Override
    public void onPause() {
        super.onPause();

//        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
    }

    @Override
    public void onResume() {
        super.onResume();
        onReadData();

//        onGetCourse();
//        Log.d(">>>TAG","onResume: llll");
//        IntentFilter intentFilter = new IntentFilter(CourseService.COURSE_SERVICE_EVENT);
//        LocalBroadcastManager.getInstance(this).registerReceiver(receiver,intentFilter);
    }
//    private BroadcastReceiver receiver = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//             result = (ArrayList<AppCourse>) intent.getSerializableExtra("result");
////           cadapter = new courseAdapter(result,coursesActivity.this);
////            listView.setAdapter(adapter);
//
//            getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, course_schedule_fragment.newInstance(result)).commit();
//
//        }
//    };
//    public void onItemClick(AppCourse appCourse){
//      new AlertDialog.Builder(this)
//              .setTitle("Xác nhận xóa")
//              .setMessage("Xóa mất không khôi phục được?")
//              .setIcon(android.R.drawable.ic_dialog_alert)
//              .setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
//                  @Override
//                  public void onClick(DialogInterface dialogInterface, int i) {
//                      Intent intent = new Intent(coursesActivity.this,CourseService.class);
//                      intent.setAction(CourseService.COURSE_SERVICE_DELETE);
//                      intent.putExtra("id",appCourse.getId());
//                      startService(intent);
//
//                  }
//              })
//              .setNegativeButton("Hủy",null).show();
//        }
    //course
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
                                .replace(R.id.flFragment, course_registration_fragment.newInstance(list))
                                .commit();
                    } else {

                    }
                }
            });
}
//course


 public void onRegisClicked(AppCourse appCourse){
//        course1 = appCourse;
     SharedPreferences sharedPreferences = getSharedPreferences("LOGIN_STATUS",MODE_PRIVATE);
        Integer currentUser_id = sharedPreferences.getInt("id",0);
     db.collection("enrolls")
             .get()
             .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                 @Override
                 public void onComplete(@NonNull Task<QuerySnapshot> task) {
                     if (task.isSuccessful()) {
                       ArrayList<AppEnroll> enRollList = new ArrayList<AppEnroll>();
                         for (QueryDocumentSnapshot document : task.getResult()) {

                             Map<String, Object> map = document.getData();
                             Long joinedDate = (Long) map.get("date");
                             String course_id = map.get("course_id").toString();
                             Long student_id = (Long) map.get("student_id");
                            if (appCourse.getCourseId().equals(course_id) && currentUser_id == Integer.parseInt(student_id.toString()) ){
                                AppEnroll enroll = new AppEnroll(null,joinedDate,student_id,course_id,null);
                                 enroll.setEnrollId(document.getId());
                                enRollList.add(enroll);
                            }

                         }
                         if(enRollList.size()==0){
                             Map<String,Object> item = new HashMap<>();
                             item.put("course_id",appCourse.getCourseId());
                             item.put("student_id",currentUser_id);
                             Long epochTime = System.currentTimeMillis()/1000;
                             item.put("date",epochTime);
                             db.collection("enrolls")
                                     .add(item)
                                     .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                         @Override
                                         public void onSuccess(DocumentReference documentReference) {
                                             Toast.makeText(coursesActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();

                                         }
                                     })
                                     .addOnFailureListener(new OnFailureListener() {
                                         @Override
                                         public void onFailure(@NonNull Exception e) {
                                             Toast.makeText(coursesActivity.this, "Đăng ký không thành công", Toast.LENGTH_SHORT).show();
                                         }
                                     });
                         }else{
                             db.collection("enrolls").document(enRollList.get(0).getEnrollId())
                                     .delete()
                                     .addOnSuccessListener(new OnSuccessListener<Void>() {
                                         @Override
                                         public void onSuccess(Void aVoid) {

                                         }
                                     })
                                     .addOnFailureListener(new OnFailureListener() {
                                         @Override
                                         public void onFailure(@NonNull Exception e) {
                                         }
                                     });
                         }

}

                 }
             });


 }
 public void readData(Integer std_ID){

     db.collection("enrolls")
             .get()
             .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                 @Override
                 public void onComplete(@NonNull Task<QuerySnapshot> task) {
                     if (task.isSuccessful()) {
                         ArrayList<AppEnroll> list = new ArrayList<>();
                         for (QueryDocumentSnapshot document : task.getResult()) {

                             Map<String, Object> map = document.getData();
                             Long date = (Long) map.get("date");
                             String course_id = map.get("course_id").toString();
                             Long student_id = (Long) map.get("student_id");
                             Log.d(">>>>>>>TAG","id ben firebase"+student_id.toString());
                             Log.d(">>>>>>TAG","id truyen vao"+std_ID);
                             if (std_ID == Integer.parseInt(student_id.toString())){
                                 AppEnroll enroll = new AppEnroll(null,date,student_id,course_id,null);
                                 enroll.setEnrollId(document.getId());
                                 list.add(enroll); // lay dc list enroll chua student id bang voi shared
                                 Log.d(">>>>>TAG","da them vao list");
                             }

                         }
                         List<String> courseID_list = new ArrayList<>();
                         for (AppEnroll e: list ){
                             courseID_list.add(e.getCourseId());
                             Log.d(">>>>TAG","course id ne"+e.getCourseId());
                         }
                     readDataCourse(courseID_list,list);
                     }
                 }
             });
 }
  private void readDataCourse(List<String> courseID_list, ArrayList<AppEnroll> enrollList){
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
                              if (courseID_list.contains(course.getCourseId())){
                                  list.add(course);
                                  Log.d(">>>TAG","DA them cid vao list");
                              }


                          }
                            myCourse = list;
                            myEnroll = enrollList;
//                          getSupportFragmentManager()
//                                  .beginTransaction()
//                                  .replace(R.id.flFragment, course_exam_fragment.newInstance(list,enrollList))
//                                  .commit();
                      } else {

                      }
                  }
              });
  }
}