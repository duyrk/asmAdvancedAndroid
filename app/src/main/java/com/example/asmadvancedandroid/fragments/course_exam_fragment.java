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
import com.example.asmadvancedandroid.adapters.EnrollsAdapter;
import com.example.asmadvancedandroid.models.AppCourse;
import com.example.asmadvancedandroid.models.AppEnroll;
import com.example.asmadvancedandroid.models.MyCourseModel;

import java.util.ArrayList;


public class course_exam_fragment extends Fragment {
    private ArrayList<AppCourse> myCourse;
    private ArrayList<AppEnroll> myEnroll;
    private ListView listView;


    public course_exam_fragment() {
        // Required empty public constructor
    }
    public static course_exam_fragment newInstance(ArrayList<AppCourse> myCourse,ArrayList<AppEnroll> myEnroll){
        course_exam_fragment fragment = new course_exam_fragment();
        Bundle args = new Bundle();
        args.putSerializable("mycourses",myCourse); //lưu trữ list bên ngoài vào argument
        args.putSerializable("myenrolls",myEnroll);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
     myCourse = (ArrayList<AppCourse>) getArguments().getSerializable("mycourses");
     myEnroll = (ArrayList<AppEnroll>) getArguments().getSerializable("myenrolls");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_course_exam_fragment, container, false);
        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listView = view.findViewById(R.id.lv_mycourse);
        EnrollsAdapter adapter = new EnrollsAdapter(myCourse,myEnroll,getContext());
        listView.setAdapter(adapter);
//        EnrollsAdapter adapter = new EnrollsAdapter(myCourseModels,getContext());
//        listView.setAdapter(adapter);
    }
}