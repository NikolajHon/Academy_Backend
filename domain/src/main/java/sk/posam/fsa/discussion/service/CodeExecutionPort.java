package sk.posam.fsa.discussion.service;

import sk.posam.fsa.discussion.CodeExecutionRequest;
import sk.posam.fsa.discussion.CodeExecutionResult;

public interface CodeExecutionPort {
    CodeExecutionResult execute(CodeExecutionRequest request);
}