//package com.hotel.notification.email;
//
//import jakarta.mail.MessagingException;
//import jakarta.mail.internet.MimeMessage;
//import lombok.RequiredArgsConstructor;
//import org.springframework.core.io.FileSystemResource;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.MimeMessageHelper;
//import org.springframework.stereotype.Service;
//
//import java.io.File;
//
//@Service
//@RequiredArgsConstructor
//public class EmailService {
//
//    private final JavaMailSender mailSender;
//
//    // ✅ Send simple text email
//    public void sendSimpleEmail(String to, String subject, String text) {
//        var message = new org.springframework.mail.SimpleMailMessage();
//        message.setFrom("your_email@gmail.com");
//        message.setTo(to);
//        message.setSubject(subject);
//        message.setText(text);
//
//        mailSender.send(message);
//    }
//
//    // ✅ Send HTML email
//    public void sendHtmlEmail(String to, String subject, String htmlBody) throws MessagingException {
//        MimeMessage message = mailSender.createMimeMessage();
//        MimeMessageHelper helper = new MimeMessageHelper(message, true);
//
//        helper.setFrom("your_email@gmail.com");
//        helper.setTo(to);
//        helper.setSubject(subject);
//        helper.setText(htmlBody, true); // true = HTML
//
//        mailSender.send(message);
//    }
//
//    // ✅ Send email with attachment
//    public void sendEmailWithAttachment(String to, String subject, String body, String filePath) throws MessagingException {
//        MimeMessage message = mailSender.createMimeMessage();
//        MimeMessageHelper helper = new MimeMessageHelper(message, true);
//
//        helper.setFrom("your_email@gmail.com");
//        helper.setTo(to);
//        helper.setSubject(subject);
//        helper.setText(body);
//
//        FileSystemResource file = new FileSystemResource(new File(filePath));
//        helper.addAttachment(file.getFilename(), file);
//
//        mailSender.send(message);
//    }
//}
