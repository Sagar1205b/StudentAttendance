package com.sahana.StudentAttendance.Service;

import com.sahana.StudentAttendance.Model.Attendance;
import com.sahana.StudentAttendance.Model.SubjectUser;

import java.util.List;

public interface AttendanceService {

    void recordAttendance(SubjectUser student, double similarity);
    List<Attendance> findAll();
    SubjectUser findSubjectUserByUsn(String usn);
    List<Attendance>findByFilters(String branch,Integer sem,String dateStr);

    List<Attendance>findAttendanceByUser(SubjectUser user);

}
