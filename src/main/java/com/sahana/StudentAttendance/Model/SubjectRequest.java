package com.sahana.StudentAttendance.Model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class SubjectRequest {

    @NotBlank
    @Size(min = 1, max = 50)
    private String subject;
    private String USN;
    private String Branch;
    private String SEM;

    public String getUSN() {
        return USN;
    }

    public void setUSN(String USN) {
        this.USN = USN;
    }

    public String getBranch() {
        return Branch;
    }

    public void setBranch(String branch) {
        Branch = branch;
    }

    public String getSEM() {
        return String.valueOf(SEM);
    }

    public void setSEM(String SEM) {
        this.SEM = SEM;
    }

    public SubjectRequest() {
    }

    public SubjectRequest(String subject) {
        this.subject = subject;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
