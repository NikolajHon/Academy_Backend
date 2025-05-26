package sk.posam.fsa.duscussion.sevice;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import sk.posam.fsa.discussion.Assignment;
import sk.posam.fsa.discussion.exceptions.EducationAppException;
import sk.posam.fsa.discussion.exceptions.ResourceAlreadyExistsException;
import sk.posam.fsa.discussion.exceptions.ResourceNotFoundException;
import sk.posam.fsa.discussion.repository.AssignmentRepository;
import sk.posam.fsa.discussion.service.AssignmentService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class AssignmentServiceTest {

    @Mock
    private AssignmentRepository assignmentRepository;

    @InjectMocks
    private AssignmentService assignmentService;

    private Assignment assignment;

    @BeforeEach
    public void setUp() {
        assignment = new Assignment();
        assignment.setId(1L);
    }

    @Test
    public void AssignmentService_CreateNewAssignment_AssignmentCreated() {
        Assignment newAssignment = new Assignment();
        newAssignment.setId(null);
        Mockito.when(assignmentRepository.create(newAssignment))
                .thenReturn(newAssignment);

        Assignment result = assignmentService.create(newAssignment);

        assertEquals(newAssignment, result);
        Mockito.verify(assignmentRepository, Mockito.times(1))
                .create(newAssignment);
    }

    @Test
    public void AssignmentService_CreateExistingAssignment_ThrowsResourceAlreadyExistsException() {
        Assignment existing = new Assignment();
        existing.setId(2L);
        Mockito.when(assignmentRepository.findById(2L))
                .thenReturn(Optional.of(existing));

        assertThrows(ResourceAlreadyExistsException.class,
                () -> assignmentService.create(existing)
        );
        Mockito.verify(assignmentRepository, Mockito.never())
                .create(existing);
    }

    @Test
    public void AssignmentService_CreateAssignment_WhenRepoThrowsRuntime_ThrowsEducationAppException() {
        Assignment newAssignment = new Assignment();
        newAssignment.setId(null);
        RuntimeException cause = new RuntimeException("DB error");
        Mockito.when(assignmentRepository.create(newAssignment))
                .thenThrow(cause);

        EducationAppException ex = assertThrows(EducationAppException.class,
                () -> assignmentService.create(newAssignment)
        );
        assertEquals("Failed to create assignment", ex.getMessage());
        assertTrue(ex.getCause() instanceof RuntimeException);
    }

    @Test
    public void AssignmentService_FindById_ReturnsOptionalAssignment() {
        Mockito.when(assignmentRepository.findById(3L))
                .thenReturn(Optional.of(assignment));

        Optional<Assignment> result = assignmentService.findById(3L);

        assertTrue(result.isPresent());
        assertEquals(assignment, result.get());
        Mockito.verify(assignmentRepository, Mockito.times(1))
                .findById(3L);
    }

    @Test
    public void AssignmentService_GetAssignmentsByCourse_ReturnsList() {
        List<Assignment> list = Arrays.asList(assignment);
        Mockito.when(assignmentRepository.getAssignmentsByCourse(5L))
                .thenReturn(list);

        List<Assignment> result = assignmentService.getAssignmentsByCourse(5L);

        assertEquals(list, result);
        Mockito.verify(assignmentRepository, Mockito.times(1))
                .getAssignmentsByCourse(5L);
    }

    @Test
    public void AssignmentService_UpdateExistingAssignment_AssignmentUpdated() {
        Assignment updated = new Assignment();
        Mockito.when(assignmentRepository.findById(1L))
                .thenReturn(Optional.of(assignment));
        Mockito.when(assignmentRepository.update(Mockito.any(Assignment.class)))
                .thenReturn(assignment);

        Assignment result = assignmentService.update(1L, updated);

        // the service should set the ID on the passed-in object and call update with it
        assertEquals(1L, updated.getId());
        Mockito.verify(assignmentRepository, Mockito.times(1))
                .update(updated);
        assertEquals(assignment, result);
    }

    @Test
    public void AssignmentService_UpdateNonExistingAssignment_ThrowsResourceNotFoundException() {
        Mockito.when(assignmentRepository.findById(4L))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> assignmentService.update(4L, assignment)
        );
        Mockito.verify(assignmentRepository, Mockito.never())
                .update(Mockito.any());
    }

    @Test
    public void AssignmentService_UpdateAssignment_WhenRepoThrowsRuntime_ThrowsEducationAppException() {
        Mockito.when(assignmentRepository.findById(1L))
                .thenReturn(Optional.of(assignment));
        Mockito.when(assignmentRepository.update(assignment))
                .thenThrow(new RuntimeException("DB error"));

        assertThrows(EducationAppException.class,
                () -> assignmentService.update(1L, assignment)
        );
    }

    @Test
    public void AssignmentService_DeleteExistingAssignment_Succeeds() {
        Mockito.when(assignmentRepository.findById(6L))
                .thenReturn(Optional.of(assignment));

        assertDoesNotThrow(() -> assignmentService.delete(6L));
        Mockito.verify(assignmentRepository, Mockito.times(1))
                .deleteById(6L);
    }

    @Test
    public void AssignmentService_DeleteNonExistingAssignment_ThrowsResourceNotFoundException() {
        Mockito.when(assignmentRepository.findById(7L))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> assignmentService.delete(7L)
        );
        Mockito.verify(assignmentRepository, Mockito.never())
                .deleteById(7L);
    }

    @Test
    public void AssignmentService_DeleteAssignment_WhenRepoThrowsRuntime_ThrowsEducationAppException() {
        Mockito.when(assignmentRepository.findById(8L))
                .thenReturn(Optional.of(assignment));
        Mockito.doThrow(new RuntimeException("DB error"))
                .when(assignmentRepository).deleteById(8L);

        assertThrows(EducationAppException.class,
                () -> assignmentService.delete(8L)
        );
    }
}
