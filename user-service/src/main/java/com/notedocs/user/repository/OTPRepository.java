package com.notedocs.user.repository;

import com.notedocs.user.entity.OTP;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface OTPRepository extends JpaRepository<OTP, Integer> {
    Optional<OTP> findByOtp(String otp);
    @Query("delete from OTP where OTP.user.id= :userID")
    void deleteByUserId(int userID);
}
