package com.example.asmadvancedandroid.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.asmadvancedandroid.R;
import com.example.asmadvancedandroid.models.AppCourse;
import com.example.asmadvancedandroid.models.AppEnroll;
import com.example.asmadvancedandroid.models.MyCourseModel;
import com.example.asmadvancedandroid.models.NewsModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class EnrollsAdapter extends BaseAdapter {
    private List<AppCourse> course ;
    private List<AppEnroll> enrolls;
    private Context context;

    public EnrollsAdapter(List<AppCourse> course, List<AppEnroll> enrolls,Context context) {
        this.course = course;
        this.enrolls = enrolls;
        this.context = context;
    }

    @Override
    public int getCount() {
        return course.size();
    }

    @Override
    public Object getItem(int i) {
        return course.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View _view, ViewGroup viewGroup) {
        View view = _view;
        if(view==null){
            view = View.inflate(viewGroup.getContext(), R.layout.layout_mycourse,null);
            TextView courseCode = view.findViewById(R.id.course_code);
            TextView courseName = view.findViewById(R.id.course_name);
            TextView courseTime = view.findViewById(R.id.course_time);
            TextView courseRoom = view.findViewById(R.id.course_room);
            TextView txtJoined = view.findViewById(R.id.txtJoined);
            ViewHolder viewHolder = new ViewHolder(courseCode,courseName,courseRoom,courseTime,txtJoined);
            view.setTag(viewHolder);
        }

        AppCourse course = (AppCourse) getItem(i);
        ViewHolder viewHolder = (ViewHolder) view.getTag();
        viewHolder.courseCode.setText(course.getCode());
        viewHolder.courseName.setText(course.getName());
        viewHolder.courseRoom.setText(course.getRoom());
        viewHolder.courseTime.setText(course.getTime());


        for (AppEnroll e : enrolls){
            if (e.getCourseId().equals(course.getCourseId())){
                long thoigian = e.getJoined();
                Date date = new Date(thoigian*1000);
                SimpleDateFormat sd = new SimpleDateFormat("dd/MM/yyyy HH:MM:SS");
                TimeZone tz = TimeZone.getTimeZone("Vietnam/Hanoi");
                sd.setTimeZone(tz);
                String thoigian2 = sd.format(date);
                viewHolder.joinedTime.setText(thoigian2);
            }
        }
        return view;
    }


    private static class ViewHolder{
        final TextView courseCode, courseName, courseRoom, courseTime,joinedTime;


        private ViewHolder(TextView courseCode, TextView courseName, TextView courseRoom, TextView courseTime, TextView joinedTime) {
            this.courseCode = courseCode;
            this.courseName = courseName;
            this.courseRoom = courseRoom;
            this.courseTime = courseTime;
            this.joinedTime = joinedTime;
        }
    }
}
