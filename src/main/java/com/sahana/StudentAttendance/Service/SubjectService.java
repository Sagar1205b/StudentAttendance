package com.sahana.StudentAttendance.Service;


import com.sahana.StudentAttendance.Model.SubjectRequest;
import com.sahana.StudentAttendance.Model.SubjectUser;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface SubjectService {
    ResponseEntity<String> createSubject(String subjectName);
    SubjectUser save(SubjectUser subjectUser);
    SubjectUser createAndSaveSubject(SubjectRequest subject);
    List<String> getAllSubjects();
    List<String> getAllUSNs();



}
