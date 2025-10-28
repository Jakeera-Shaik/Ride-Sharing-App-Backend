package com.example.verification.repository;

import com.example.verification.model.DriverDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional; // ✅ Add this import

public interface DriverDetailsRepository extends JpaRepository<DriverDetails, Long> {
    Optional<DriverDetails> findByEmail(String email);
}
