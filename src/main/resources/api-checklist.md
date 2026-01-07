# API Checklist

> 체크박스 형태로 엔드포인트 구현/검증 진행 상황을 관리하기 위한 문서입니다.  
> 주석(약관) 예: _exclude status=DELETED_, _soft delete_, _multipart_ 등은 유지했습니다.

## Auth

- [x] **POST** `/api/auth/login`
- [x] **POST** `/api/auth/logout`
- [x] **GET** `/api/auth/me`  _(exclude status=DELETED)_

## User

- [x] **GET** `/api/users`  _(exclude status=DELETED)_
- [x] **POST** `/api/users/invitations`  _(status=INACTIVE)_
- [x] **GET** `/api/users/search?query=`  _(exclude status=DELETED)_
- [x] **GET** `/api/users/{userId}`  _(exclude status=DELETED)_
- [x] **PATCH** `/api/users/{userId}`
- [x] **PATCH** `/api/users/{userId}/password`
- [x] **PATCH** `/api/users/{userId}/status`  _(INACTIVE → ACTIVE (first login))_

## User Role

- [x] **GET** `/api/roles`
- [x] **GET** `/api/users/{userId}/roles`
- [x] **PUT** `/api/users/{userId}/roles`  _(replace all)_
- [x] **POST** `/api/users/{userId}/roles/{roleId}`  _(add single)_
- [x] **DELETE** `/api/users/{userId}/roles/{roleId}`

## Project

- [x] **GET** `/api/projects`  _(exclude status=DELETED)_
- [x] **POST** `/api/projects`
- [X] **GET** `/api/projects/my`  _(exclude status=DELETED)_
- [x] **GET** `/api/projects/{projectId}`  _(exclude status=DELETED)_
- [x] **PATCH** `/api/projects/{projectId}`
- [x] **PUT** `/api/projects/{projectId}/favorite`

## Project Member

- [x] **GET** `/api/projects/{projectId}/members`  _(exclude status=DELETED)_
- [x] **POST** `/api/projects/{projectId}/members`  _(default role=VIEWER)_
- [x] **PATCH** `/api/projects/{projectId}/members/{memberId}`
- [x] **DELETE** `/api/projects/{projectId}/members/{memberId}`

## Interface

- [ ] **GET** `/api/projects/{projectId}/interfaces`
- [ ] **POST** `/api/projects/{projectId}/interfaces`
- [ ] **GET** `/api/interfaces/{interfaceId}`
- [ ] **PATCH** `/api/interfaces/{interfaceId}`
- [ ] **GET** `/api/interfaces/{interfaceId}/history`
- [ ] **GET** `/api/interfaces/{interfaceId}/revisions`
- [ ] **POST** `/api/interfaces/{interfaceId}/revisions`

## Interface Status

- [ ] **GET** `/api/projects/{projectId}/interface-statuses`
- [ ] **POST** `/api/projects/{projectId}/interface-statuses`
- [ ] **PATCH** `/api/interface-statuses/{statusId}`

## Interface Status Transition

- [ ] **GET** `/api/projects/{projectId}/interface-status-transitions`  _(exclude status=DELETED)_
- [ ] **POST** `/api/projects/{projectId}/interface-status-transitions`
- [ ] **PATCH** `/api/interface-status-transitions/{transitionId}`

## Interface Status Change

- [ ] **POST** `/api/interfaces/{interfaceId}/status`

## Task

- [ ] **GET** `/api/projects/{projectId}/tasks`  _(exclude status=DELETED)_
- [ ] **POST** `/api/projects/{projectId}/tasks`
- [ ] **GET** `/api/tasks/{taskId}`
- [ ] **PATCH** `/api/tasks/{taskId}`
- [ ] **POST** `/api/tasks/{taskId}/status`
- [ ] **GET** `/api/tasks/{taskId}/subtasks`

## Task Search

- [ ] **GET** `/api/tasks/search`  _(exclude status=DELETED)_

## Task Filter

- [ ] **GET** `/api/projects/{projectId}/task-filters`
- [ ] **POST** `/api/projects/{projectId}/task-filters`
- [ ] **PATCH** `/api/task-filters/{groupId}`
- [ ] **DELETE** `/api/task-filters/{groupId}`  _(soft delete)_

## Post

- [x] **GET** `/api/projects/{projectId}/posts`  _(exclude status=DELETED)_
- [x] **POST** `/api/projects/{projectId}/posts`
- [x] **GET** `/api/posts/{postId}`  _(exclude status=DELETED)_
- [x] **PATCH** `/api/posts/{postId}`
- [x] **DELETE** `/api/posts/{postId}`

## Notice

- [x] **GET** `/api/projects/{projectId}/notices`  _(exclude status=DELETED)_
- [x] **POST** `/api/projects/{projectId}/notices`
- [x] **GET** `/api/notices/{noticeId}`  _(exclude status=DELETED)_
- [x] **PATCH** `/api/notices/{noticeId}`
- [x] **DELETE** `/api/notices/{noticeId}`

## Faq

- [x] **GET** `/api/projects/{projectId}/faqs`  _(exclude status=DELETED)_
- [x] **POST** `/api/projects/{projectId}/faqs`
- [x] **GET** `/api/faqs/{faqId}`  _(exclude status=DELETED)_
- [x] **PATCH** `/api/faqs/{faqId}`
- [x] **PATCH** `/api/faqs/{faqId}/answer`
- [x] **DELETE** `/api/faqs/{faqId}`

## Event

- [x] **GET** `/api/projects/{projectId}/events`
- [x] **POST** `/api/projects/{projectId}/events`
- [x] **GET** `/api/events/{eventId}`
- [x] **PATCH** `/api/events/{eventId}`
- [x] **DELETE** `/api/events/{eventId}`

## Event Attendee

- [x] **PUT** `/api/events/{eventId}/attendees`
- [x] **POST** `/api/events/{eventId}/attendees`
- [x] **PATCH** `/api/event-attendees/{eventAttendeeId}`

## Feed

- [ ] **GET** `/api/projects/{projectId}/feed`

## Comment

- [x] **GET** `/api/{targetType}/{targetId}/comments`
- [x] **POST** `/api/{targetType}/{targetId}/comments`
- [x] **PATCH** `/api/comments/{commentId}`
- [x] **DELETE** `/api/comments/{commentId}`  _(soft delete)_

## Mention

- [ ] **GET** `/api/users/me/mentions`  _(exclude status=DELETED)_
- [ ] **GET** `/api/users/me/my-posts`  _(exclude status=DELETED)_

## System

- [x] **GET** `/api/projects/{projectId}/systems`  _(exclude status=DELETED)_
- [x] **POST** `/api/projects/{projectId}/systems`
- [x] **PATCH** `/api/systems/{systemId}`

## System Owner

- [x] **GET** `/api/projects/{projectId}/system-owners`
- [x] **POST** `/api/projects/{projectId}/system-owners`
- [x] **DELETE** `/api/system-owners/{systemOwnerId}`

## Connection

- [x] **GET** `/api/systems/{systemId}/connections`  _(exclude status=DELETED)_
- [x] **POST** `/api/systems/{systemId}/connections`
- [x] **PATCH** `/api/connections/{connectionId}`

## File

- [ ] **GET** `/api/projects/{projectId}/folders`
- [ ] **POST** `/api/projects/{projectId}/folders`
- [ ] **GET** `/api/folders/{folderId}/files`  _(exclude status=DELETED)_
- [ ] **POST** `/api/projects/{projectId}/files`  _(multipart)_
- [ ] **PATCH** `/api/files/{fileId}`

## Attachment

- [ ] **POST** `/api/attachments`
- [ ] **GET** `/api/{targetType}/{targetId}/attachments`
