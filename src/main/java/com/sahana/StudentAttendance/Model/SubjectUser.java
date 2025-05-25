package com.sahana.StudentAttendance.Model;


import jakarta.persistence.*;

@Entity
@Table(name = "user_subject")
public class SubjectUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  int id;

    private String Subject;
    private String USN;
    private String Branch;
    private String SEM;

    public SubjectUser(String USN, String branch, String SEM) {
        this.USN = USN;
        Branch = branch;
        this.SEM = SEM;
    }

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
        return SEM;
    }

    public void setSEM(String SEM) {
        this.SEM = SEM;
    }

    @OneToOne
    @JoinColumn(name = "user_id")
    private LicenseHolder licenseHolder;

    public SubjectUser() {
    }

    public SubjectUser(String subject, LicenseHolder licenseHolder) {
        Subject = subject;
        this.licenseHolder = licenseHolder;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSubject() {
        return Subject;
    }

    public void setSubject(String subject) {
        Subject = subject;
    }

    public LicenseHolder getLicenseHolder() {
        return licenseHolder;
    }

    public void setLicenseHolder(LicenseHolder licenseHolder) {
        this.licenseHolder = licenseHolder;
    }
}
