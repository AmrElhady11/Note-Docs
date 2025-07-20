package com.notedocs.user.service;

import com.notedocs.user.entity.OTP;
import com.notedocs.user.entity.User;
import com.notedocs.user.repository.OTPRepository;
import com.notedocs.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.Random;

@AllArgsConstructor
@Service
public class OTPService {
    private final OTPRepository otpRepository;
    private final UserRepository userRepository;

    @Transactional
    public void sendOTP(String email) {
        OTP otp = OTP.builder()
                .otp(generateOtpCode())
                .user(getUser(email))
                .expirationTime(new Date(System.currentTimeMillis()+15*60*1000))
                .build();
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("OTP");
        message.setText("Your OTP is: " + otp.getOtp()+"Please dont share it with any one.");
        otpRepository.save(otp);

}

public Boolean validateOTP(String otpCode, String email) {
        var user = getUser(email);
        var otp = otpRepository.findByOtp(otpCode);
        // will be updated after adding globla exceptions

    return otp.get().getId().equals(user.getId()) && !isOTPExpired(otp.get());


}
@Transactional
public void deleteAllOTP(String email) {
        var user = getUser(email);
        otpRepository.deleteByUserId(user.getId());
}

    private String generateOtpCode() {

        return new DecimalFormat("000000").format(new Random().nextInt(999999));
    }
    private User getUser(String email) {
        return userRepository.findByEmail(email).orElseThrow(); // will be updated after adding global exception handling
    }
    private boolean isOTPExpired(OTP otp) {
        return otp.getExpirationTime().before(new Date());
    }

}
