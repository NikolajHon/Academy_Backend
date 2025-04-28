package sk.posam.fsa.discussion.controller;

import org.springframework.http.ResponseEntity;
import sk.posam.fsa.discussion.rest.api.AssignmentsApi;
import sk.posam.fsa.discussion.rest.dto.AssignmentDto;
import sk.posam.fsa.discussion.rest.dto.UpdateAssignmentRequestDto;

public class AssignmentRestController implements AssignmentsApi {
    @Override
    public ResponseEntity<Void> deleteAssignment(Long assignmentId) {
        return null;
    }

    @Override
    public ResponseEntity<AssignmentDto> getAssignmentById(Long assignmentId) {
        return null;
    }

    @Override
    public ResponseEntity<AssignmentDto> updateAssignment(Long assignmentId, UpdateAssignmentRequestDto updateAssignmentRequestDto) {
        return null;
    }
}
