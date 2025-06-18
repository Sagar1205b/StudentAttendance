package com.sahana.StudentAttendance.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "user_subject", uniqueConstraints = @UniqueConstraint(columnNames = "usn"))
public class SubjectUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String subject;
    private String usn;
    private String branch;
    private String sem;

    @OneToOne
    @JoinColumn(name = "user_id")
    private LicenseHolder licenseHolder;

    public SubjectUser() {
    }

    // Constructor to initialize USN, branch and sem
    public SubjectUser(String usn, String branch, String sem) {
        this.usn = usn;
        this.branch = branch;
        this.sem = sem;
    }

    // Constructor to initialize subject and licenseHolder
    public SubjectUser(String subject, LicenseHolder licenseHolder) {
        this.subject = subject;
        this.licenseHolder = licenseHolder;
    }

    // Full constructor (optional)
    public SubjectUser(String subject, String usn, String branch, String sem, LicenseHolder licenseHolder) {
        this.subject = subject;
        this.usn = usn;
        this.branch = branch;
        this.sem = sem;
        this.licenseHolder = licenseHolder;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getUsn() {
        return usn;
    }

    public void setUsn(String usn) {
        this.usn = usn;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getSem() {
        return sem;
    }

    public void setSem(String sem) {
        this.sem = sem;
    }

    public LicenseHolder getLicenseHolder() {
        return licenseHolder;
    }

    public void setLicenseHolder(LicenseHolder licenseHolder) {
        this.licenseHolder = licenseHolder;
    }
}

