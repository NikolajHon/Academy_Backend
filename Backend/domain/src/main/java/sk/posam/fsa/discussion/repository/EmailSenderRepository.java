package sk.posam.fsa.discussion.repository;

public interface EmailSenderRepository {
    void send(String to, String subject, String body);
}
