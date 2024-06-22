package com.example.mailtest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OtpController {

    @Autowired
    private EmailService emailService;
    @Autowired
    private OtpService otpService;

    @GetMapping("/sendOtp")
    public String sendOtp(@RequestParam String email) {
        return otpService.sendOtp(email);
    }
}