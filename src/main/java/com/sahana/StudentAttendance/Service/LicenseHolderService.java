package com.sahana.StudentAttendance.Service;


import com.sahana.StudentAttendance.Model.LicenseHolder;
import com.sahana.StudentAttendance.Repository.LicenseHolderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service

public class LicenseHolderService implements LicenseHolderServiceImpl{
    @Autowired
    private LicenseHolderRepository licenseHolderRepository;

    @Autowired
    private EmailSenderService emailSenderService;

    private  final BCryptPasswordEncoder passwordEncoder=new BCryptPasswordEncoder();

    public LicenseHolderService(LicenseHolderRepository licenseHolderRepository, EmailSenderService emailSenderService) {
        this.licenseHolderRepository = licenseHolderRepository;
        this.emailSenderService = emailSenderService;
    }


    @Override
    public LicenseHolder save(LicenseHolder theLicenseHolder) {
        Optional<LicenseHolder> existingUser=licenseHolderRepository.findByUsername(theLicenseHolder.getUsername());
        Optional<LicenseHolder> existingEmail=licenseHolderRepository.findByEmail(theLicenseHolder.getEmail());

        if(existingUser.isPresent()){
            throw  new RuntimeException("username is already taken");
        }
        if(existingEmail.isPresent()){
            throw  new RuntimeException("Email is already Used");
        }


        theLicenseHolder.setPassword(passwordEncoder.encode(theLicenseHolder.getPassword()));
        if (theLicenseHolder.getRole() == null || theLicenseHolder.getRole().isEmpty()) {
            theLicenseHolder.setRole("ROLE_USER");  // Default role
        }
        return licenseHolderRepository.save(theLicenseHolder);
    }
}
