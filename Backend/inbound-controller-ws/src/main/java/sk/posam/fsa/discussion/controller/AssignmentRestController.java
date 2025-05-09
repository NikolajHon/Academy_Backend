package sk.posam.fsa.discussion.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import sk.posam.fsa.discussion.Assignment;
import sk.posam.fsa.discussion.TestCase;
import sk.posam.fsa.discussion.mapper.AssignmentMapper;
import sk.posam.fsa.discussion.mapper.TestCaseMapper;
import sk.posam.fsa.discussion.rest.api.AssignmentsApi;
import sk.posam.fsa.discussion.rest.dto.AssignmentDto;
import sk.posam.fsa.discussion.rest.dto.CreateTestCaseRequestDto;
import sk.posam.fsa.discussion.rest.dto.TestCaseDto;
import sk.posam.fsa.discussion.rest.dto.UpdateAssignmentRequestDto;
import sk.posam.fsa.discussion.service.AssignmentFacade;
import sk.posam.fsa.discussion.service.TestCaseFacade;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestController
public class AssignmentRestController implements AssignmentsApi {

    private final AssignmentFacade assignmentFacade;
    private final TestCaseFacade testCaseFacade;
    private final AssignmentMapper assignmentMapper;
    private final TestCaseMapper testCaseMapper;

    public AssignmentRestController(AssignmentFacade assignmentFacade,
                                    TestCaseFacade testCaseFacade,
                                    AssignmentMapper assignmentMapper,
                                    TestCaseMapper testCaseMapper) {
        this.assignmentFacade  = assignmentFacade;
        this.testCaseFacade    = testCaseFacade;
        this.assignmentMapper  = assignmentMapper;
        this.testCaseMapper    = testCaseMapper;
    }

    @Override
    public ResponseEntity<AssignmentDto> getAssignmentById(Long assignmentId) {
        Assignment a = assignmentFacade
                .findById(assignmentId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Assignment not found"));
        return ResponseEntity.ok(assignmentMapper.toDto(a));
    }

    @Override
    public ResponseEntity<AssignmentDto> updateAssignment(Long assignmentId,
                                                          UpdateAssignmentRequestDto req) {
        Assignment domain = assignmentMapper.toDomain(req);
        try {
            Assignment updated = assignmentFacade.update(assignmentId, domain);
            return ResponseEntity.ok(assignmentMapper.toDto(updated));
        } catch (NoSuchElementException ex) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Assignment not found", ex);
        }
    }


    @Override
    public ResponseEntity<Void> deleteAssignment(Long assignmentId) {
        try {
            assignmentFacade.delete(assignmentId);
            return ResponseEntity.noContent().build();
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Assignment not found", e);
        }
    }

    @Override
    public ResponseEntity<List<TestCaseDto>> getTestCasesByAssignment(Long assignmentId) {
        List<TestCase> cases = testCaseFacade.getByAssignment(assignmentId);
        List<TestCaseDto> dtos = cases.stream()
                .map(testCaseMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @Override
    public ResponseEntity<TestCaseDto> createTestCase(Long assignmentId,
                                                      CreateTestCaseRequestDto req) {
        TestCase tc = testCaseMapper.toDomain(req);
        try {
            TestCase created = testCaseFacade.create(assignmentId, tc);
            return new ResponseEntity<>(
                    testCaseMapper.toDto(created),
                    HttpStatus.CREATED);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Assignment not found", e);
        }
    }

    @Override
    public ResponseEntity<Void> deleteTestCase(Long assignmentId, Long testCaseId) {
        try {
            testCaseFacade.delete(assignmentId, testCaseId);
            return ResponseEntity.noContent().build();
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Assignment or TestCase not found", e);
        }
    }
}
