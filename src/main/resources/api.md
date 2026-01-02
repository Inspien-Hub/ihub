Auth & users

- POST /api/auth/login -> { items: [{ email, password }] }; resp { items: [{ token, user: User }] }
- GET /api/users/me -> resp { items: [User] } // exclude status=DELETED
- GET /api/users -> resp { items: User[] } // exclude status=DELETED
- GET /api/users/search?query= -> resp { items: User[] } // exclude status=DELETED
- POST /api/users/invite -> {
  items: [{ loginId(email), name, password, company?, position?, phoneNumber?, profileImageUrl?, roles? }] };
  resp { items: [User] } (status=INACTIVE)
- PATCH /api/users/{userId} -> {
  items: [{ name?, company?, position?, phoneNumber?, profileImageUrl?, status?, roles? }] }; resp {
  items: [User] }
- PATCH /api/users/{userId}/password -> { items: [{ password }] }; resp { items: [User] }
- PATCH /api/users/{userId}/status -> { items: [{ status }] } // INACTIVE -> ACTIVE on first login

Roles & permissions

- GET /api/roles -> { items: RoleItem[] }
- GET /api/users/{userId}/roles -> { items: UserRole[] }
- PUT /api/users/{userId}/roles -> { items: UserRole[] } // replace all
- POST /api/users/{userId}/roles -> { items: [{ roleId }] } // add single
- DELETE /api/users/{userId}/roles/{roleId} -> 204

Projects & members

- GET /api/projects -> resp { items: Project[] } // exclude status=DELETED
- GET /api/users/me/projects -> resp { items: ProjectWithRole[] } // exclude status=DELETED
- POST /api/projects -> {
  items: [{ title, description?, startDate?, endDate?, currentAdminId, status? }] }; resp {
  items: [Project] }
- GET /api/projects/{projectId} -> { items: [Project] } // exclude status=DELETED
- PATCH /api/projects/{projectId} -> { items: [{ ...Project fields }] }
- PATCH /api/projects/{projectId}/favorite -> { items: [{ isFavorite }] }
- GET /api/projects/{projectId}/members -> { items: ProjectMemberView[] } // exclude
  status=DELETED (user join: name/email/status)
- POST /api/projects/{projectId}/members -> { items: [{ userId, role? }] }; resp { items:
  ProjectMemberView[] } // default role=VIEWER, project member can invite
- PATCH /api/projects/{projectId}/members/{memberId} -> { items: [{ role }] }; resp { items:
  ProjectMemberView[] } // project ADMIN only, ADMIN assign only if user has PROJECT_FULL_ACCESS
- DELETE /api/projects/{projectId}/members/{memberId} -> 204 // project ADMIN only

Interfaces & statuses

- GET /api/projects/{projectId}/interfaces?statusId=&systemId=&keyword= -> { items: Interface[],
  statuses: InterfaceStatus[] }
- POST /api/projects/{projectId}/interfaces -> { items: [Interface create payload] }
- GET /api/interfaces/{interfaceId} -> { items: [Interface] } (with latest status, systems,
  createdBy)
- PATCH /api/interfaces/{interfaceId} -> { items: [partial update] }
  O- POST /api/interfaces/{interfaceId}/status -> {
  items: [{ toStatusId, reason?, relatedTaskId? }] }; resp {
  items: [{ history: InterfaceHistory, interface: Interface }] }
  O- GET /api/projects/{projectId}/interface-statuses -> { items: InterfaceStatus[] }
  O- POST /api/projects/{projectId}/interface-statuses -> {
  items: [{ name, code?, seqOrder, isDefault? }] }
  O- PATCH /api/interface-statuses/{statusId} -> {
  items: [{ name?, code?, seqOrder?, isDefault?, status? }] }
- GET /api/projects/{projectId}/interface-status-transitions -> { items:
  InterfaceStatusTransition[] } // exclude status=DELETED
- POST /api/projects/{projectId}/interface-status-transitions -> {
  items: [{ fromStatusId, toStatusId, allowedRole?, status? }] }
- PATCH /api/interface-status-transitions/{transitionId} -> { items: [{ allowedRole?, status? }] }
- GET /api/interfaces/{interfaceId}/history -> { items: InterfaceHistory[] }
- GET /api/interfaces/{interfaceId}/revisions -> { items: InterfaceRevision[] }
- POST /api/interfaces/{interfaceId}/revisions -> { items: [{ snapshot (json), reason? }] }

Tasks & filters

- GET /api/projects/{projectId}/tasks?status=&assigneeId=&parentTaskId=&interfaceId= -> { items:
  TaskView[] } // exclude status=DELETED
- GET
  /api/tasks/search?page=&pageSize=&projectId=&assigneeIds=&authorIds=&dateType=&from=&to=&statuses= -> {
  items: TaskView[], total } // exclude status=DELETED
    * 로그인한 유저가 참여한 프로젝트 범위에서만 조회
    * /all-tasks는 TASK_FULL_ACCESS 권한 필요
- POST /api/projects/{projectId}/tasks -> {
  items: [{ parentTaskId?, taskType, interfaceId?, title, content?, status, assigneeId?, requesterId?, startDate?, dueDate?, priority?, progress? }] }
- GET /api/tasks/{taskId} -> { items: [Task] }
- PATCH /api/tasks/{taskId} -> { items: [{ ...partial fields incl status/progress }] }
- POST /api/tasks/{taskId}/status -> { items: [{ status, progress?, reason? }] }; resp {
  items: [Task] }
- GET /api/tasks/{taskId}/subtasks -> { items: Task[] }
- GET /api/projects/{projectId}/task-filters -> { items: TaskFilterGroup[] with statuses:
  TaskFilterGroupStatus[] }
- POST /api/projects/{projectId}/task-filters -> { items: [{ name, assigneeKeyword?, authorKeyword?,
  dateType?, dateFrom?, dateTo?, statuses: string[] }] }
- PATCH /api/task-filters/{groupId} -> { items: [{ name, assigneeKeyword?, authorKeyword?,
  dateType?, dateFrom?, dateTo?, statuses: string[] }] }
- DELETE /api/task-filters/{groupId} -> marks deletedAt

Posts, events, feed

- GET /api/projects/{projectId}/posts?page=&pageSize=&keyword=&authorId=&from=&to= -> { items:
  PostView[], total } // exclude status=DELETED
- POST /api/projects/{projectId}/posts -> { items: [{ title, content }] }
- GET /api/posts/{postId} -> { items: [PostView] } // exclude status=DELETED
- PATCH /api/posts/{postId} -> { items: [{ title?, content? }] }
- DELETE /api/posts/{postId} -> 204
- GET
  /api/projects/{projectId}/notices?page=&pageSize=&keyword=&authorId=&category=&importance=&isPinned=&from=&to= -> {
  items: NoticeView[], total } // exclude status=DELETED
- POST /api/projects/{projectId}/notices -> {
  items: [{ title, content, category, importance, isPinned }] }
- GET /api/notices/{noticeId} -> { items: [NoticeView] } // exclude status=DELETED
- PATCH /api/notices/{noticeId} -> {
  items: [{ title?, content?, category?, importance?, isPinned? }] }
- DELETE /api/notices/{noticeId} -> 204
- GET
  /api/projects/{projectId}/faqs?page=&pageSize=&keyword=&category=&answerStatus=&assigneeId=&isSecret=&from=&to= -> {
  items: FaqView[], total } // exclude status=DELETED
- POST /api/projects/{projectId}/faqs -> { items: [{ category, question, isSecret, assigneeId? }] }
- GET /api/faqs/{faqId} -> { items: [FaqView] } // exclude status=DELETED
- PATCH /api/faqs/{faqId} -> { items: [{ category?, question?, isSecret?, assigneeId? }] }
- PATCH /api/faqs/{faqId}/answer -> { items: [{ answer }] }
- DELETE /api/faqs/{faqId} -> 204
- GET /api/projects/{projectId}/events -> { items: Event[] } // 로그인 유저가 참여한 프로젝트만 조회
- POST /api/projects/{projectId}/events -> { items: [{ title, startDatetime, endDatetime?,
  location?, content?, remindBeforeMinutes?, attendees?: EventAttendee[] }] }
- GET /api/events/{eventId} -> { items: [Event with attendees (EventAttendeeView[])] }
- PATCH /api/events/{eventId} -> { items: [{ ...partial event fields }] }
- PUT /api/events/{eventId}/attendees -> { items: [{ attendees: EventAttendee[] }] }
- DELETE /api/events/{eventId} -> 204
- GET /api/projects/{projectId}/feed -> { items: FeedItem[] }

Comments & mentions

- GET /api/{targetType}/{targetId}/comments -> { items: CommentView[] }
- POST /api/{targetType}/{targetId}/comments -> { items: [{ content, parentCommentId?, mentions?:
  string[] }] }; resp { items: [CommentView], mentions: Mention[] }
- PATCH /api/comments/{commentId} -> { items: [{ content }] }
- DELETE /api/comments/{commentId} -> soft delete or status
    * UI uses targetType=POST/EVENT/NOTICE/FAQ for detail comment threads.
    * Reply is created with parentCommentId (no separate endpoint).

Mentions & my posts

- GET /api/users/me/mentions?page=&pageSize=&projectId=&authorIds=&targetTypes=&from=&to= -> {
  items: MentionView[], total } // 로그인 유저가 참여한 프로젝트만 조회 (status=DELETED 제외)
- GET /api/users/me/my-posts?page=&pageSize=&projectId=&targetTypes=&from=&to= -> { items:
  MyPostItem[], total } // 로그인 유저가 참여한 프로젝트만 조회 (status=DELETED 제외)

Systems & connections

- GET /api/projects/{projectId}/systems -> { items: System[] } // exclude status=DELETED
- POST /api/projects/{projectId}/systems -> {
  items: [{ systemCode?, status?, description?, systemType?, environment?, createdBy }] }
- PATCH /api/systems/{systemId} -> { items: [{ ...partial fields }] }
- GET /api/projects/{projectId}/system-owners?userId= -> { items: SystemOwner[] } // project members
  only
- POST /api/projects/{projectId}/system-owners -> { items: [{ userId, systemId }] }
- DELETE /api/system-owners/{systemOwnerId} -> 204
- GET /api/systems/{systemId}/connections -> { items: Connection[] } // exclude status=DELETED
- POST /api/systems/{systemId}/connections -> {
  items: [{ name?, protocol?, host?, port?, path?, username?, authType?, extraConfig?, status?, description? }] }
- PATCH /api/connections/{connectionId} -> { items: [{ ...partial fields }] }

Files, folders, attachments

- GET /api/projects/{projectId}/folders?parentFolderId= -> { items: Folder[] }
- POST /api/projects/{projectId}/folders -> { items: [{ name, parentFolderId? }] }
- GET /api/folders/{folderId}/files -> { items: File[] } // exclude status=DELETED
- POST /api/projects/{projectId}/files -> multipart { items: [{ file, folderId? }] }; resp {
  items: [File] }
- PATCH /api/files/{fileId} -> { items: [{ status? (ACTIVE/DELETED), folderId? }] }
- POST /api/attachments -> { items: [{ projectId, fileId, targetType, targetId }] }
- GET /api/{targetType}/{targetId}/attachments -> { items: Attachment[] }

Event attendees (if separate management)

- POST /api/events/{eventId}/attendees -> { items: [{ userId, isRequired?, attendStatus? }] }
- PATCH /api/event-attendees/{eventAttendeeId} -> { items: [{ attendStatus?, isRequired? }] }