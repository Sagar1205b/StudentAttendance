package com.sahana.StudentAttendance.Model;

import java.time.LocalDateTime;

public class AttendanceDTO {
    private String usn;
    private String name;
    private String branch;
    private String semester;
    private boolean present;
    private LocalDateTime timestamp;

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    // Constructor
    public AttendanceDTO(String usn, String name, String branch, String semester, boolean present,LocalDateTime timestamp) {
        this.usn = usn;
        this.name = name;
        this.branch = branch;
        this.semester = semester;
        this.present = present;
        this.timestamp=timestamp;
    }

    public AttendanceDTO() {

    }

    // Getters and setters


    public String getUsn() {
        return usn;
    }

    public void setUsn(String usn) {
        this.usn = usn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public boolean isPresent() {
        return present;
    }

    public void setPresent(boolean present) {
        this.present = present;
    }
}

