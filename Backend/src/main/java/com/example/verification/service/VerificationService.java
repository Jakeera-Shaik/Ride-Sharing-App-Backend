package com.example.verification.service;

import com.example.verification.model.VerificationCode;
import com.example.verification.repository.VerificationCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
public class VerificationService {

    @Autowired
    private VerificationCodeRepository codeRepository;

    public String generateCode(String email) {
    // Delete any existing code for this email before saving a new one
    codeRepository.deleteByEmail(email);

    String code = String.valueOf(100000 + new Random().nextInt(900000));

    VerificationCode verificationCode = new VerificationCode();
    verificationCode.setEmail(email);
    verificationCode.setCode(code);
    verificationCode.setExpiryTime(LocalDateTime.now().plusMinutes(5));
    codeRepository.save(verificationCode);

    return code;
}



    public boolean verifyCode(String email, String code) {
        List<VerificationCode> codes = codeRepository.findAllByEmail(email);

        if (codes.isEmpty()) {
            System.out.println("❌ No code found for " + email);
            return false;
        }

        // ✅ Always check the most recent (in case old ones remain)
        VerificationCode latest = codes.get(codes.size() - 1);
        System.out.println("🔍 Verifying latest code for " + email + ": expected=" + latest.getCode() + ", got=" + code);

        return latest.getCode().equals(code) && latest.getExpiryTime().isAfter(LocalDateTime.now());
    }
}
