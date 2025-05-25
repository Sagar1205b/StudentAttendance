package com.sahana.StudentAttendance.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "license_holder")
public class LicenseHolder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    private String username;

    private String email;

    private String password;

    private String role;  // Added role field

    @Transient
    private Integer otp;

    public Integer getOtp() {
        return otp;
    }

    public void setOtp(Integer otp) {
        this.otp = otp;
    }

    public LicenseHolder() {
    }

    public LicenseHolder(String username, String email, String password, String role) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;  // Include role in constructor
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;  // Get role
    }

    public void setRole(String role) {
        this.role = role;  // Set role
    }

    @Override
    public String toString() {
        return "LicenseHolder{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +  
                '}';
    }
}
