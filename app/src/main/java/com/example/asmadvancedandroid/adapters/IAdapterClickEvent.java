package com.example.asmadvancedandroid.adapters;

import com.example.asmadvancedandroid.models.AppCourse;

public interface IAdapterClickEvent {
    public void onEditCourseClick(AppCourse course);
    public void onDeleteCourseClick(AppCourse course);
}
