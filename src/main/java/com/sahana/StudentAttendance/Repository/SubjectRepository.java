package com.sahana.StudentAttendance.Repository;


import com.sahana.StudentAttendance.Model.SubjectUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SubjectRepository extends JpaRepository<SubjectUser,Integer> {

//    @Query("SELECT DISTINCT Subject FROM SubjectUser")
//    List<String> findAllDistinctSubjects();

    @Query("SELECT DISTINCT s.Subject FROM SubjectUser s WHERE s.licenseHolder.username = :username")
    List<String> findSubjectsByUsername(String username);

    @Query("SELECT DISTINCT s.USN FROM SubjectUser s WHERE s.licenseHolder.username = :username")
    List<String> findUSNByUsername(String username);


//    List<SubjectUser> findByLicenseHolder_Username(String username);


}
