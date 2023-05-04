package com.example.asmadvancedandroid.models;

public class AppEnroll {
    private Integer id;
    private Long joined,studentId;
    private String courseId;
    private String EnrollId;
    public AppEnroll() {
    }

    public AppEnroll(Integer id, Long joined, Long studentId, String courseId, String enrollId) {
        this.id = id;
        this.joined = joined;
        this.studentId = studentId;
        this.courseId = courseId;
        EnrollId = enrollId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getJoined() {
        return joined;
    }

    public void setJoined(Long joined) {
        this.joined = joined;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getEnrollId() {
        return EnrollId;
    }

    public void setEnrollId(String enrollId) {
        EnrollId = enrollId;
    }
}
