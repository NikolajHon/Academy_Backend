package sk.posam.fsa.discussion;

import java.util.ArrayList;
import java.util.List;

public class Assignment {
    private Long id;
    private String title;
    private String description;
    private String templateCode;
    private String teacherCode;
    private String language;
    private AssignmentOutputType outputType;
    private List<TestCase> testCases = new ArrayList<>();


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
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

    public String getTeacherCode() {
        return teacherCode;
    }

    public void setTeacherCode(String teacherCode) {
        this.teacherCode = teacherCode;
    }

    public AssignmentOutputType getOutputType() {
        return outputType;
    }

    public void setOutputType(AssignmentOutputType outputType) {
        this.outputType = outputType;

    }

    public List<TestCase> getTestCases() {
        return testCases;
    }

    public void setTestCases(List<TestCase> testCases) {
        this.testCases = testCases;
    }
}