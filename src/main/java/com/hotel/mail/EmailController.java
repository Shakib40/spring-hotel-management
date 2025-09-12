package com.hotel.mail;

import com.hotel.mail.EmailService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/email")
@RequiredArgsConstructor
public class EmailController {

    private final EmailService emailService;

    @PostMapping("/send-text")
    public ResponseEntity<String> sendTextEmail(
            @RequestParam String to,
            @RequestParam String subject,
            @RequestParam String body) {
        emailService.sendSimpleEmail(to, subject, body);
        return ResponseEntity.ok("✅ Text email sent successfully!");
    }

    @PostMapping("/send-html")
    public ResponseEntity<String> sendHtmlEmail(
            @RequestParam String to,
            @RequestParam String subject,
            @RequestParam String htmlBody) throws MessagingException {
        emailService.sendHtmlEmail(to, subject, htmlBody);
        return ResponseEntity.ok("✅ HTML email sent successfully!");
    }

    @PostMapping("/send-attachment")
    public ResponseEntity<String> sendEmailWithAttachment(
            @RequestParam String to,
            @RequestParam String subject,
            @RequestParam String body,
            @RequestParam String filePath) throws MessagingException {
        emailService.sendEmailWithAttachment(to, subject, body, filePath);
        return ResponseEntity.ok("✅ Email with attachment sent successfully!");
    }
}
