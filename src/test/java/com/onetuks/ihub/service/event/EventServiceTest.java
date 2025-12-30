package com.onetuks.ihub.service.event;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.onetuks.ihub.TestcontainersConfiguration;
import com.onetuks.ihub.dto.communication.EventCreateRequest;
import com.onetuks.ihub.dto.communication.EventUpdateRequest;
import com.onetuks.ihub.entity.communication.Event;
import com.onetuks.ihub.entity.project.Project;
import com.onetuks.ihub.entity.user.User;
import com.onetuks.ihub.exception.AccessDeniedException;
import com.onetuks.ihub.repository.EventJpaRepository;
import com.onetuks.ihub.repository.ProjectJpaRepository;
import com.onetuks.ihub.repository.ProjectMemberJpaRepository;
import com.onetuks.ihub.repository.UserJpaRepository;
import com.onetuks.ihub.service.ServiceTestDataFactory;
import com.onetuks.ihub.service.communication.EventService;
import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
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
class EventServiceTest {

  @Autowired
  private EventService eventService;

  @Autowired
  private EventJpaRepository eventJpaRepository;
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
  void createEvent_success() {
    EventCreateRequest request = buildEventCreateRequest();

    Event result = eventService.create(creator, request);

    assertThat(result.getEventId()).isNotNull();
    assertThat(result.getTitle()).isEqualToIgnoringCase(request.title());
    assertThat(result.getProject().getProjectId()).isEqualToIgnoringCase(request.projectId());
    assertThat(result.getCreatedBy().getUserId()).isEqualToIgnoringCase(creator.getUserId());
  }

  @Test
  void updateEvent_success() {
    Event created = eventService.create(creator, buildEventCreateRequest());
    EventUpdateRequest updateRequest = new EventUpdateRequest(
        "Planning Updated",
        LocalDateTime.now().plusHours(1),
        LocalDateTime.now().plusHours(3),
        "Room3",
        "new content",
        10);

    Event result = eventService.update(creator, created.getEventId(), updateRequest);

    assertThat(result.getTitle()).isEqualToIgnoringCase(updateRequest.title());
    assertThat(result.getEndDatetime()).isEqualTo(updateRequest.endDatetime());
  }

  @Test
  void getEventById_exception() {
    // Given
    User hacker = ServiceTestDataFactory.createUser(userJpaRepository);
    Event created = eventService.create(creator, buildEventCreateRequest());

    // When & Then
    assertThatThrownBy(() -> eventService.getById(hacker, created.getEventId()))
        .isInstanceOf(AccessDeniedException.class);
  }

  @Test
  void getEvents_returnsAll() {
    Pageable pageable = PageRequest.of(0, 10);
    eventService.create(creator, buildEventCreateRequest());
    eventService.create(creator, buildEventCreateRequest());

    Page<Event> results = eventService.getAll(creator, project.getProjectId(), pageable);

    assertThat(results.getTotalElements()).isGreaterThanOrEqualTo(2);
  }

  @Test
  void deleteEvent_success() {
    Event created = eventService.create(creator, buildEventCreateRequest());

    eventService.delete(creator, created.getEventId());

    assertThrows(EntityNotFoundException.class,
        () -> eventService.getById(creator, created.getEventId()));
  }

  private EventCreateRequest buildEventCreateRequest() {
    return new EventCreateRequest(
        project.getProjectId(),
        "Kickoff",
        LocalDateTime.now(),
        LocalDateTime.now().plusHours(2),
        "Room1",
        "Content",
        30);
  }
}
