package com.sahana.StudentAttendance.Service;

import com.sahana.StudentAttendance.Model.Attendance;
import com.sahana.StudentAttendance.Repository.AttendanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AttendanceService {
    @Autowired
    private AttendanceRepository attendanceRepository;

    public void recordAttendance(String studentName,double similarity){
        if(similarity>0.95){
            Attendance attendance=new Attendance();
            attendance.setStudentName(studentName);
            attendance.setSimilarity(similarity);
            attendance.setTimestamp(LocalDateTime.now());

            attendance.setPresent(true);

            attendanceRepository.save(attendance);
        }

    }
}
