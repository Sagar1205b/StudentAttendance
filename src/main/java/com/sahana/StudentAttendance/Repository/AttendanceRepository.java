package com.sahana.StudentAttendance.Repository;

import com.sahana.StudentAttendance.Model.Attendance;
import com.sahana.StudentAttendance.Model.AttendanceDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance,Long> {
    boolean existsByUSNAndTimestamp(String studentName, LocalDateTime now);

//
//    @Query("SELECT new AttendanceDTO(" +
//            "a.student.usn, a.student.name, a.student.branch, a.student.semester, a.present) " +
//            "FROM Attendance a")
//    List<AttendanceDTO> fetchAttendanceSummary();
@Query("SELECT a FROM Attendance a JOIN FETCH a.subjectUser")
List<Attendance> findAllWithSubjectUser();


}
