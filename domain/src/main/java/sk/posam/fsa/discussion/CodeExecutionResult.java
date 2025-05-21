package sk.posam.fsa.discussion;

public record CodeExecutionResult(int statusCode,
                                  String stdout,
                                  String stderr) {}