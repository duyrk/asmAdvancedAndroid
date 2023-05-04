package com.example.asmadvancedandroid.adapters;

import android.content.Context;
import android.media.Image;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.asmadvancedandroid.R;
import com.example.asmadvancedandroid.models.AppCourse;

import java.util.ArrayList;
import java.util.List;

public class courseAdapter extends BaseAdapter {
    private List<AppCourse> list ;
    private Context context;

    public courseAdapter(List<AppCourse> list, Context context) {
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
           view = View.inflate(viewGroup.getContext(), R.layout.layout_course_detail_item,null);
           TextView courseCode = view.findViewById(R.id.course_code);
           TextView courseName = view.findViewById(R.id.course_name);
           TextView courseTime = view.findViewById(R.id.course_time);
           TextView courseRoom = view.findViewById(R.id.course_room);
           ImageView btnEdit = view.findViewById(R.id.btnEdit);
           ImageView btnDelete = view.findViewById(R.id.btnDelete);
           ViewHolder viewHolder = new ViewHolder(courseCode,courseName,courseRoom,courseTime,btnEdit,btnDelete);
           view.setTag(viewHolder);
       }
        AppCourse course = (AppCourse) getItem(i);
        ViewHolder viewHolder = (ViewHolder) view.getTag();
        viewHolder.courseCode.setText(course.getCode());
        viewHolder.courseName.setText(course.getName());
        viewHolder.courseRoom.setText(course.getRoom());
        viewHolder.courseTime.setText(course.getTime());
        viewHolder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IAdapterClickEvent iAdapterClickEvent = (IAdapterClickEvent) viewGroup.getContext();
                iAdapterClickEvent.onEditCourseClick(course);
            }
        });
        viewHolder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IAdapterClickEvent iAdapterClickEvent = (IAdapterClickEvent) viewGroup.getContext();
                iAdapterClickEvent.onDeleteCourseClick(course);
            }
        });
        return view;
    }


    private static class ViewHolder{
        final TextView courseCode, courseName, courseRoom, courseTime;
        final ImageView btnEdit, btnDelete;
        private ViewHolder(TextView courseCode, TextView courseName, TextView courseRoom, TextView courseTime,ImageView btnEdit,ImageView btnDelete) {
            this.courseCode = courseCode;
            this.courseName = courseName;
            this.courseRoom = courseRoom;
            this.courseTime = courseTime;
            this.btnEdit = btnEdit;
            this.btnDelete = btnDelete;
        }
    }
}
