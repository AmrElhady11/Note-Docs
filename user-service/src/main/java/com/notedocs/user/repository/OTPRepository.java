package com.notedocs.user.repository;

import com.notedocs.user.entity.OTP;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OTPRepository extends JpaRepository<OTP, Integer> {
}
