package com.example.asmadvancedandroid.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.asmadvancedandroid.R;
import com.example.asmadvancedandroid.coursesActivity;
import com.example.asmadvancedandroid.models.AppCourse;

import java.util.List;

public class courseAdapter_user extends BaseAdapter {
    private List<AppCourse> list ;
    private Context context;

    public courseAdapter_user(List<AppCourse> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View _view, ViewGroup viewGroup) {
        View view = _view;
        if(view==null){
            view = View.inflate(viewGroup.getContext(), R.layout.layout_course_for_user,null);
            TextView courseCode = view.findViewById(R.id.course_code);
            TextView courseName = view.findViewById(R.id.course_name);
            TextView courseTime = view.findViewById(R.id.course_time);
            TextView courseRoom = view.findViewById(R.id.course_room);
            Button btnDangky = view.findViewById(R.id.btnDangky);
            ViewHolder viewHolder = new ViewHolder(courseCode,courseName,courseRoom,courseTime,btnDangky);
            view.setTag(viewHolder);
        }
        //




        //
        AppCourse course = (AppCourse) getItem(i);
        ViewHolder viewHolder = (ViewHolder) view.getTag();
        viewHolder.courseCode.setText(course.getCode());
        viewHolder.courseName.setText(course.getName());
        viewHolder.courseRoom.setText(course.getRoom());
        viewHolder.courseTime.setText(course.getTime());
        viewHolder.btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((coursesActivity)viewGroup.getContext()).onRegisClicked(course);

            }
        });
        return view;
    }


    private static class ViewHolder{
        final TextView courseCode, courseName, courseRoom, courseTime;
        final Button btnDangKy;

        private ViewHolder(TextView courseCode, TextView courseName, TextView courseRoom, TextView courseTime, Button btnDangKy) {
            this.courseCode = courseCode;
            this.courseName = courseName;
            this.courseRoom = courseRoom;
            this.courseTime = courseTime;
            this.btnDangKy = btnDangKy;
        }
    }

}
