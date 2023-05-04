package com.example.asmadvancedandroid.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.asmadvancedandroid.R;
import com.example.asmadvancedandroid.adapters.courseAdapter;
import com.example.asmadvancedandroid.adapters.courseAdapter_user;
import com.example.asmadvancedandroid.models.AppCourse;

import java.util.ArrayList;


public class course_registration_fragment extends Fragment {
    private ArrayList<AppCourse> courses;
    private ListView listView;

    public course_registration_fragment() {
        // Required empty public constructor
    }
    //truyền dữ liệu từ ngoài vào
    public static course_registration_fragment newInstance(ArrayList<AppCourse> courses){
        course_registration_fragment fragment = new course_registration_fragment();
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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_course_registration_fragment, container, false);

    }

    // xử lý logic ở onViewCreated
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listView = view.findViewById(R.id.listView_course1);
        courseAdapter_user adapter = new courseAdapter_user(courses,getContext());
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
}