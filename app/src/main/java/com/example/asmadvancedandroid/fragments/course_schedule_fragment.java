package com.example.asmadvancedandroid.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.asmadvancedandroid.R;
import com.example.asmadvancedandroid.Services.CourseService;
import com.example.asmadvancedandroid.adapters.courseAdapter;
import com.example.asmadvancedandroid.adapters.courseAdapter_user;
import com.example.asmadvancedandroid.coursesActivity;
import com.example.asmadvancedandroid.models.AppCourse;

import java.util.ArrayList;


public class course_schedule_fragment extends Fragment {
    private ArrayList<AppCourse> courses;
    private ListView listView;

    public course_schedule_fragment() {
        // Required empty public constructor
    }
    //truyền dữ liệu từ ngoài vào
    public static course_schedule_fragment newInstance(ArrayList<AppCourse> courses){
        course_schedule_fragment fragment = new course_schedule_fragment();
        Bundle args = new Bundle();
        args.putSerializable("courses",courses); //lưu trữ list bên ngoài vào argument

        fragment.setArguments(args);
        return fragment;
    }
//     đọc dữ liệu từ ngoài truyền vô
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            courses = (ArrayList<AppCourse>) getArguments().getSerializable("courses");
            // lấy dữ liệu argument từ ngoài truyển vào rồi gán vào frag hiện tại

        }
    }
// onCreate chỉ nên trả về view ko xử lý logic
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_course_schedule_fragment, container, false);

        return view;
    }
// xử lý logic ở onViewCreated
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listView = view.findViewById(R.id.listView_course);
       courseAdapter adapter = new courseAdapter(courses,getContext());
       listView.setAdapter(adapter);

//       listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() { // co the lay id o day
//           @Override
//           public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
//               AppCourse course = (AppCourse) adapterView.getItemAtPosition(i);
//               ((coursesActivity)  view.getContext()).onItemClick(course);
//               return false;
//           }
//       });
//      onGetCourse();
    }
//
//    public void onGetCourse(){
//        Intent intent = new Intent(getContext(),CourseService.class);
//        intent.setAction(CourseService.COURSE_SERVICE_GET_ALL);
//        getActivity().startService(intent);
//    }
//    @Override
//    public void onPause() {
//        super.onPause();
//        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(receiver);
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        onGetCourse();
//        IntentFilter intentFilter = new IntentFilter(CourseService.COURSE_SERVICE_EVENT);
//        LocalBroadcastManager.getInstance(getContext()).registerReceiver(receiver,intentFilter);
//    }
//    private BroadcastReceiver receiver = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            ArrayList<AppCourse> result = (ArrayList<AppCourse>) intent.getSerializableExtra("result");
//            adapter = new courseAdapter(result,getContext());
//            listView.setAdapter(adapter);
//        }
//    };
}