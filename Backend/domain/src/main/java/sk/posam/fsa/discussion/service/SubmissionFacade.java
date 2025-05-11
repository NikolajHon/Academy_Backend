package sk.posam.fsa.discussion.service;

import sk.posam.fsa.discussion.SubmissionResult;

public interface SubmissionFacade {
    SubmissionResult submit(Long assignmentId, String studentCode);
}