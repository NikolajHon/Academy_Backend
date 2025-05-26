package sk.posam.fsa.discussion.exceptions;

public class EducationAppException extends RuntimeException {
    public EducationAppException() { super(); }
    public EducationAppException(String message) { super(message); }
    public EducationAppException(String message, Throwable cause) { super(message, cause); }
    public EducationAppException(Throwable cause) { super(cause); }
}
