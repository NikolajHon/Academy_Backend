package sk.posam.fsa.discussion.jpa;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import sk.posam.fsa.discussion.repository.EmailSenderRepository;

@Component
public class SmtpEmailSenderAdapter implements EmailSenderRepository {

    private final JavaMailSender mailSender;

    @Value("${discussion.mail.from}")
    private String from;

    public SmtpEmailSenderAdapter(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void send(String to, String subject, String body) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom(from);
        msg.setTo(to);
        msg.setSubject(subject);
        msg.setText(body);
        mailSender.send(msg);
    }
}
