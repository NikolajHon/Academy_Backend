package sk.posam.fsa.discussion;

public record CodeExecutionRequest(String source,
                                   String language,
                                   String versionIndex) {}