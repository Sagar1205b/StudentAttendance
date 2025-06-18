package com.sahana.StudentAttendance.Repository;


import com.sahana.StudentAttendance.Model.LicenseHolder;
import com.sahana.StudentAttendance.Model.SubjectUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SubjectRepository extends JpaRepository<SubjectUser,Integer> {

//    @Query("SELECT DISTINCT Subject FROM SubjectUser")
//    List<String> findAllDistinctSubjects();

    @Query("SELECT DISTINCT s.subject FROM SubjectUser s WHERE s.licenseHolder.username = :username")
    List<String> findSubjectsByUsername(String username);

    @Query("SELECT DISTINCT s.usn FROM SubjectUser s WHERE s.licenseHolder.username = :username")
    List<String> findUSNByUsername(String username);


    Optional<SubjectUser> findByUsn(String USN);


    @Query("SELECT COUNT(s) > 0 FROM SubjectUser s WHERE s.licenseHolder.username = :username")
    boolean existsByLicenseHolderUsername(String username);


//    List<SubjectUser> findByLicenseHolder_Username(String username);


}
