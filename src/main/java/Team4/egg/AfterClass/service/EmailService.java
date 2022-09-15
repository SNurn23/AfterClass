//package Team4.egg.AfterClass.service;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Value;
//
//import org.springframework.mail.SimpleMailMessage;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.scheduling.annotation.Async;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//public class EmailService {
//
//    private final JavaMailSender sender;
//
//    @Value("${spring.mail.username}")
//    private String from;
//
//    private static final String SUBJECT = "Welcome To AfterClass!";
//    private static final String TEXT = "Thank you for choosing us! We welcome you from the Afterclass team";
//
//    @Async
//    public void send(String to) {
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setTo(to);
//        message.setFrom(from);
//        message.setSubject(SUBJECT);
//        message.setText(TEXT);
//        sender.send(message);
//    }
//}
