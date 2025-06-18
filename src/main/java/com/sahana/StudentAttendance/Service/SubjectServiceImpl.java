package com.sahana.StudentAttendance.Service;


import com.sahana.StudentAttendance.Model.LicenseHolder;
import com.sahana.StudentAttendance.Model.SubjectRequest;
import com.sahana.StudentAttendance.Model.SubjectUser;
import com.sahana.StudentAttendance.Repository.LicenseHolderRepository;
import com.sahana.StudentAttendance.Repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class SubjectServiceImpl implements SubjectService {

    private final RestTemplate restTemplate;
    private final SubjectRepository subjectRepository;
    private final LicenseHolderRepository licenseHolderRepository;

    @Autowired
    public SubjectServiceImpl(RestTemplate restTemplate, SubjectRepository subjectRepository, LicenseHolderRepository licenseHolderRepository) {
        this.restTemplate = restTemplate;
        this.subjectRepository = subjectRepository;
        this.licenseHolderRepository = licenseHolderRepository;
    }

    @Value("${recognition.api.url}")
    private String apiUrl;

    @Value("${recognition.api.key}")
    private String apiKey;

    @Override
    public ResponseEntity<String> createSubject(String subjectName) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("x-api-key", apiKey);

        SubjectRequest request = new SubjectRequest(subjectName);
        HttpEntity<SubjectRequest> entity = new HttpEntity<>(request, headers);

        return restTemplate.postForEntity(apiUrl, entity, String.class);
    }

    @Override
    public SubjectUser save(SubjectUser subjectUser) {
        return subjectRepository.save(subjectUser);
    }

    @Override
    public SubjectUser createAndSaveSubject(SubjectRequest subjectRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName(); // assuming username is unique

        LicenseHolder licenseHolder = licenseHolderRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));


        SubjectUser user = new SubjectUser();
        user.setSubject(subjectRequest.getSubject());
        user.setLicenseHolder(licenseHolder);
        user.setBranch(subjectRequest.getBranch());
        user.setSem(subjectRequest.getSEM());
        user.setUsn(subjectRequest.getUSN());

         return  subjectRepository.save(user);
    }

    @Override
    public List<String> getAllSubjects() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName(); // Get the current username
        return subjectRepository.findSubjectsByUsername(username);
    }

    @Override
    public List<String> getAllUSNs() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return subjectRepository.findUSNByUsername(username);
    }

    @Override
    public List<SubjectUser> findAll() {
        return subjectRepository.findAll();
    }

    @Override
    public boolean hasUserCompletedRegistration(String username) {
        return subjectRepository.existsByLicenseHolderUsername(username);
    }





}
