package com.onetuks.ihub.service.communication;

import static org.assertj.core.api.Assertions.assertThat;

import com.onetuks.ihub.TestcontainersConfiguration;
import com.onetuks.ihub.dto.communication.EventAttendeeCreateRequest;
import com.onetuks.ihub.dto.communication.EventAttendeeCreateRequest.EventAttendeeCreateRequests;
import com.onetuks.ihub.dto.communication.EventAttendeeUpdateRequest;
import com.onetuks.ihub.dto.communication.EventCreateRequest;
import com.onetuks.ihub.entity.communication.Event;
import com.onetuks.ihub.entity.communication.EventAttendee;
import com.onetuks.ihub.entity.communication.EventAttendeeStatus;
import com.onetuks.ihub.entity.project.Project;
import com.onetuks.ihub.entity.user.User;
import com.onetuks.ihub.repository.ProjectJpaRepository;
import com.onetuks.ihub.repository.ProjectMemberJpaRepository;
import com.onetuks.ihub.repository.UserJpaRepository;
import com.onetuks.ihub.service.ServiceTestDataFactory;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@SpringBootTest
@Import(TestcontainersConfiguration.class)
class EventAttendeeServiceTest {

  @Autowired
  private EventAttendeeService eventAttendeeService;

  @Autowired
  private EventService eventService;
  @Autowired
  private ProjectJpaRepository projectJpaRepository;
  @Autowired
  private UserJpaRepository userJpaRepository;
  @Autowired
  private ProjectMemberJpaRepository projectMemberJpaRepository;

  private Project project;
  private User creator;

  @BeforeEach
  void setUp() {
    creator = ServiceTestDataFactory.createUser(userJpaRepository);
    project = ServiceTestDataFactory.createProject(projectJpaRepository, creator, creator,
        "EventProj");
    ServiceTestDataFactory.createProjectMember(projectMemberJpaRepository, project, creator);
  }

  @Test
  void manageAttendees_success() {
    // Given
    Event created = eventService.create(creator, project.getProjectId(), buildEventCreateRequest());
    EventAttendeeCreateRequests requests = new EventAttendeeCreateRequests(
        List.of(buildCreateRequest(created), buildCreateRequest(created)));

    // When
    List<EventAttendee> results =
        eventAttendeeService.manageAttendees(creator, created.getEventId(), requests);

    // Then
    assertThat(results).hasSize(2);
  }

  @Test
  void getAllAttendees_success() {
    // Given
    Pageable pageable = PageRequest.of(0, 10);
    Event created = eventService.create(creator, project.getProjectId(), buildEventCreateRequest());
    EventAttendeeCreateRequests requests = new EventAttendeeCreateRequests(
        List.of(buildCreateRequest(created), buildCreateRequest(created)));
    eventAttendeeService.manageAttendees(creator, created.getEventId(), requests);

    // When
    Page<EventAttendee> results =
        eventAttendeeService.getAllAttendees(creator, created.getEventId(), pageable);

    // Then
    assertThat(results.getTotalElements()).isEqualTo(2);
  }

  @Test
  void addAttendee_success() {
    // Given
    Event event = eventService.create(creator, project.getProjectId(), buildEventCreateRequest());
    EventAttendeeCreateRequest created = buildCreateRequest(event);

    // When
    EventAttendee result = eventAttendeeService.addAttendee(creator, event.getEventId(), created);

    // Then
    assertThat(result.getEventAttendeeId()).isNotNull();
    assertThat(result.getAttendStatus()).isEqualTo(created.attendStatus());
  }

  @Test
  void updateAttendee_success() {
    // Given
    Event event = eventService.create(creator, project.getProjectId(), buildEventCreateRequest());
    EventAttendee created =
        eventAttendeeService.addAttendee(creator, event.getEventId(), buildCreateRequest(event));
    EventAttendeeUpdateRequest request =
        new EventAttendeeUpdateRequest(null, EventAttendeeStatus.ABSENT);

    // When
    EventAttendee result =
        eventAttendeeService.updateAttendee(creator, created.getEventAttendeeId(), request);

    // Then
    assertThat(result.getIsMandatory()).isEqualTo(created.getIsMandatory());
    assertThat(result.getAttendStatus()).isEqualTo(request.attendStatus());
  }

  private EventCreateRequest buildEventCreateRequest() {
    return new EventCreateRequest(
        "Kickoff",
        LocalDateTime.now(),
        LocalDateTime.now().plusHours(2),
        "Room1",
        "Content",
        30);
  }

  private EventAttendeeCreateRequest buildCreateRequest(Event event) {
    return new EventAttendeeCreateRequest(
        event.getEventId(),
        ServiceTestDataFactory.createUser(userJpaRepository).getUserId(),
        true, EventAttendeeStatus.ACCEPTED);
  }
}