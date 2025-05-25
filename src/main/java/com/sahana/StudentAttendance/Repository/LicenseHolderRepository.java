package com.sahana.StudentAttendance.Repository;


import com.sahana.StudentAttendance.Model.LicenseHolder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LicenseHolderRepository extends JpaRepository<LicenseHolder,Integer> {


    Optional<LicenseHolder> findByEmail(String email);
    Optional<LicenseHolder> findByUsername(String username);

}
