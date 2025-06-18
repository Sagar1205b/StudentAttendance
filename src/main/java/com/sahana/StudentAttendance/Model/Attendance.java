package com.sahana.StudentAttendance.Model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "attendance")
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "usn")
    private String USN;
    private double similarity;
    private boolean present;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "usn", referencedColumnName = "usn", insertable = false, updatable = false)
    private SubjectUser subjectUser;

    public SubjectUser getSubjectUser() {
        return subjectUser;
    }

    public void setSubjectUser(SubjectUser subjectUser) {
        this.subjectUser = subjectUser;
    }

    public boolean isPresent() {
        return present;
    }

    public void setPresent(boolean present) {
        this.present = present;
    }

    public double getSimilarity() {
        return similarity;
    }

    public void setSimilarity(double similarity) {
        this.similarity = similarity;
    }

    private LocalDateTime timestamp;

    public String getUSN() {
        return USN;
    }

    public void setUSN(String USN) {
        this.USN = USN;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
