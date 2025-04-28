package sk.posam.fsa.discussion;

public class Assignment {
    private Long id;
    private String description;
    private String templateCode;
    private String expectedOutput;
    private AssignmentOutputType outputType;
    private Lesson lesson;

    public Lesson getLesson() {
        return lesson;
    }

    public void setLesson(Lesson lesson) {
        this.lesson = lesson;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getExpectedOutput() {
        return expectedOutput;
    }

    public void setExpectedOutput(String expectedOutput) {
        this.expectedOutput = expectedOutput;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTemplateCode() {
        return templateCode;
    }

    public void setTemplateCode(String templateCode) {
        this.templateCode = templateCode;
    }

    public AssignmentOutputType getOutputType() {
        return outputType;
    }

    public void setOutputType(AssignmentOutputType outputType) {
        this.outputType = outputType;

    }

}