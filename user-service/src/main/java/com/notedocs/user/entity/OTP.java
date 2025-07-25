package com.notedocs.user.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "otp")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OTP {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "OTP", length = 20, nullable = false)
    private String otp;

    @Column(name = "expiration_time", nullable = false)
    private Date expirationTime;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}