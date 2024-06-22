package com.example.mailtest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;


@Service
public class OtpService {

    @Autowired
    private OtpRepository otpRepository;

    @Autowired
    private EmailService emailService;

    public String sendOtp(String email) {
        String otp = OtpUtil.generateNumericOtp(6);
        Otp otpEntity = new Otp();
        otpEntity.setEmail(email);
        otpEntity.setOtp(otp);
        int expirationTime= (int) Instant.now().plus(5, ChronoUnit.MINUTES).toEpochMilli();
        otpEntity.setExpirationtime(expirationTime);

        otpRepository.save(otpEntity);
        emailService.sendOtpEmail(email, "Your OTP Code", "Your OTP code is: " + otp);
        return "Otp Successfully Sent";
    }

    public boolean verifyOtp(String email, String otp) {
        Otp otpEntity = otpRepository.findByEmail(email);
        if (otpEntity != null && otpEntity.getOtp().equals(otp) &&
                otpEntity.getExpirationtime() > Instant.now().toEpochMilli()) {
            otpRepository.delete(otpEntity);
            return true;
        }
        return false;
    }
}