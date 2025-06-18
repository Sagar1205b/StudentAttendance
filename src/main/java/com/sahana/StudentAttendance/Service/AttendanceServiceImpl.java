package com.sahana.StudentAttendance.Service;

import com.sahana.StudentAttendance.Model.Attendance;
import com.sahana.StudentAttendance.Model.SubjectUser;
import com.sahana.StudentAttendance.Repository.AttendanceRepository;
import com.sahana.StudentAttendance.Repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AttendanceServiceImpl implements AttendanceService {
    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private SubjectRepository subjectRepository;


    public void recordAttendance(SubjectUser studentName, double similarity) {
        if (similarity > 0.95) {
            LocalDateTime now = LocalDateTime.now().withSecond(0).withNano(0);

            // 🟡 Use a unique identifier from SubjectUser — assuming USN is the key
            String usn = studentName.getUsn();

            // ✅ Fetch the persistent SubjectUser entity
            SubjectUser persistedUser = subjectRepository.findByUsn(usn)
                    .orElseThrow(() -> new RuntimeException("SubjectUser not found with USN: " + usn));

            boolean alreadyPresent = attendanceRepository.existsByUSNAndTimestamp(usn, now);

            if (!alreadyPresent) {
                Attendance attendance = new Attendance();
                attendance.setSubjectUser(persistedUser);  // use managed entity
                attendance.setSimilarity(similarity);
                attendance.setTimestamp(now);
                attendance.setPresent(true);
                attendance.setUSN(studentName.getUsn());

                attendanceRepository.save(attendance);  // Now it's safe
            }
        }
    }


    @Override
    public List<Attendance> findAll() {
        return attendanceRepository.findAll();
    }

    @Override
    public SubjectUser findSubjectUserByUsn(String usn) {
        return subjectRepository.findByUsn(usn)
                .orElseThrow(() -> new RuntimeException("SubjectUser not found with USN: " + usn));
    }




    @Override
    public List<Attendance> findByFilters(String branch, Integer sem, String dateStr) {
        List<Attendance> all = attendanceRepository.findAll();

        return all.stream()
                .filter(a -> {
                    SubjectUser user = a.getSubjectUser();
                    boolean matches = true;

                    if (branch != null) {
                        matches &= branch.equalsIgnoreCase(user.getBranch());
                    }

                    if (sem != null && user.getSem() != null) {
                        matches &= sem.toString().equals(user.getSem().trim());
                    }

                    if (dateStr != null) {
                        LocalDate filterDate = LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                        LocalDate attendanceDate = a.getTimestamp().toLocalDate();
                        matches &= filterDate.equals(attendanceDate);
                    }

                    return matches;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<Attendance> findAttendanceByUser(SubjectUser user) {
        return attendanceRepository.findAll().stream()
                .filter(a -> a.getSubjectUser().getUsn().equals(user.getUsn()))
                .collect(Collectors.toList());
    }

}
