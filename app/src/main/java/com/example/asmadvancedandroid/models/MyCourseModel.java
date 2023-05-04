package com.example.asmadvancedandroid.models;

public class MyCourseModel {
        private String courseID, code_enroll, name_enroll , time_enroll , room_enroll;
        private Float joined;

    public MyCourseModel(String courseID, String code_enroll, String name_enroll, String time_enroll, String room_enroll, Float joined) {
        this.courseID = courseID;
        this.code_enroll = code_enroll;
        this.name_enroll = name_enroll;
        this.time_enroll = time_enroll;
        this.room_enroll = room_enroll;
        this.joined = joined;
    }

    public String getCourseID() {
        return courseID;
    }

    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }

    public String getCode_enroll() {
        return code_enroll;
    }

    public void setCode_enroll(String code_enroll) {
        this.code_enroll = code_enroll;
    }

    public String getName_enroll() {
        return name_enroll;
    }

    public void setName_enroll(String name_enroll) {
        this.name_enroll = name_enroll;
    }

    public String getTime_enroll() {
        return time_enroll;
    }

    public void setTime_enroll(String time_enroll) {
        this.time_enroll = time_enroll;
    }

    public String getRoom_enroll() {
        return room_enroll;
    }

    public void setRoom_enroll(String room_enroll) {
        this.room_enroll = room_enroll;
    }

    public Float getJoined() {
        return joined;
    }

    public void setJoined(Float joined) {
        this.joined = joined;
    }
}
