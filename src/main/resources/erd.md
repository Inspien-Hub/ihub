///////////////////////////////////////////////////////
// Users & Roles
///////////////////////////////////////////////////////

Table users {
user_id varchar(255) [pk]
email varchar(255) [not null, unique]
password varchar(255) [not null]
name varchar(255) [not null]

company varchar(255)              // 회사명
position varchar(255)             // 직함
phone_number varchar(50)          // 휴대전화번호
profile_image_url varchar(500)    // 프로필 사진

status varchar(20) [not null, default: 'ACTIVE']
// 가능 값: ACTIVE, INACTIVE, LOCKED, DELETED

created_at timestamptz
updated_at timestamptz
}

///////////////////////////////////////////////////////
// Projects & Members
///////////////////////////////////////////////////////

Table projects {
project_id varchar2 [pk]
title varchar(255) [not null]
description text
start_date date
end_date date
created_by varchar(255) [not null]
current_admin_id varchar(255) [not null]
status varchar(20) [not null, default: 'ACTIVE']
// ACTIVE, INACTIVE, COMPLETED, ARCHIVED, DELETED
created_at timestamptz
updated_at timestamptz
}

Ref: projects.created_by - users.user_id
Ref: projects.current_admin_id - users.user_id

Table project_members {
project_member_id varchar(255) [pk]
project_id varchar2 [not null]
user_id varchar(255) [not null]
role varchar(50) [not null] // ADMIN, MEMBER, VIEWER
joined_at timestamptz
left_at timestamptz

Note: "UNIQUE(project_id, user_id)"
}

Ref: project_members.project_id - projects.project_id
Ref: project_members.user_id - users.user_id

///////////////////////////////////////////////////////
// Interface Status & Interfaces
///////////////////////////////////////////////////////
Table interfaces {
interface_id varchar2 [pk]
project_id varchar2 [not null]
ifid varchar(255)
source_system_id varchar2
target_system_id varchar2
module varchar(255)
interface_type varchar(255) //REALTIME, BATCH
pattern varchar(255)
sender_adapter varchar(255)
receiver_adapter varchar(255)
sa varchar(10) // "SYNC", "ASYNC"
status_id varchar2
batch_time_label varchar(255) // BATCH일 경우만 사용
remark text
created_by varchar(255) [not null] //최초 생성자 기록용
created_at timestamptz
updated_at timestamptz
}

Ref: interfaces.project_id - projects.project_id
Ref: interfaces.status_id - interface_statuses.status_id
Ref: interfaces.created_by - users.user_id
Ref: interfaces.source_system_id - systems.system_id
Ref: interfaces.target_system_id - systems.system_id

//“인터페이스 상태 탭”에서 다루는 상태값 정의
//프로젝트 ADMIN만 CUD 가능
Table interface_statuses {
status_id varchar2 [pk]
project_id varchar2 [not null]
name varchar(255) [not null]
code varchar(100)
seq_order int [not null] //상태 단계
is_default boolean [default: false]
created_at timestamptz
updated_at timestamptz
}

Ref: interface_statuses.project_id - projects.project_id

// 인터페이스 상태 전이 규칙
Table interface_status_transitions {
transition_id varchar2 [pk]
project_id varchar2 [not null]

from_status_id varchar2 [not null]   // 현재 상태
to_status_id varchar2 [not null]     // 이동 후 상태

// 권한/조건 까지 나중에 확장 가능성을 염두
allowed_role varchar(50)          // ADMIN, MEMBER 등

status varchar(30) [not null, default: 'ACTIVE']
// ACTIVE, INACTIVE, DELETED
created_at timestamptz
created_by varchar(255)
}

Ref: interface_status_transitions.project_id - projects.project_id
Ref: interface_status_transitions.from_status_id - interface_statuses.status_id
Ref: interface_status_transitions.to_status_id - interface_statuses.status_id
Ref: interface_status_transitions.created_by - users.user_id

Table interface_status_history {//인터페이스 이력관리를 위함
history_id varchar2 [pk]
interface_id varchar2 [not null]
from_status_id varchar2
to_status_id varchar2 [not null]
changed_by varchar(255) [not null]
changed_at timestamptz
related_task_id varchar2
reason text
}

Ref: interface_status_history.interface_id - interfaces.interface_id
Ref: interface_status_history.from_status_id - interface_statuses.status_id
Ref: interface_status_history.to_status_id - interface_statuses.status_id
Ref: interface_status_history.changed_by - users.user_id
Ref: interface_status_history.related_task_id - tasks.task_id

Table interface_revisions {//인터페이스 이력관리를 위함
revision_id varchar2 [pk]
interface_id varchar2 [not null]
version_no int [not null]
changed_by varchar(255) [not null]
changed_at timestamptz
snapshot jsonbt
reason text
}

Ref: interface_revisions.interface_id - interfaces.interface_id
Ref: interface_revisions.changed_by - users.user_id
Ref: interface_revisions.related_task_id - tasks.task_id

///////////////////////////////////////////////////////
// Tasks (상/하위 일감)
///////////////////////////////////////////////////////

Table tasks {
task_id varchar2 [pk]
project_id varchar2 [not null]
parent_task_id varchar2
task_type varchar(30) [not null, default: 'GENERAL_CHILD']
// PARENT, INTERFACE_CHILD, GENERAL_CHILD
interface_id varchar2
title varchar(255) [not null]
content text
status varchar(50) [not null]
// REQUEST, IN_PROGRESS, COMPLETED, CLOSED, CANCELED
assignee_id varchar(255)
requester_id varchar(255)
start_date date
due_date date
priority varchar(50) //LOW, MEDIUM, HIGH
progress int // %, 상위일감은 하위 평균/가중값으로 계산해도 됨
created_by varchar(255)
created_at timestamptz
updated_at timestamptz
}

Ref: tasks.project_id - projects.project_id
Ref: tasks.parent_task_id - tasks.task_id
Ref: tasks.assignee_id - users.user_id
Ref: tasks.requester_id - users.user_id
Ref: tasks.created_by - users.user_id
Ref: tasks.interface_id - interfaces.interface_id

Table task_filter_groups {
group_id varchar2 [pk]
user_id varchar(255) [not null]
name varchar(100) [not null]
project_id varchar2
assignee_keyword varchar(100)
author_keyword varchar(100)
date_type varchar(20) [not null, default: 'CREATED'] // CREATED, REGISTERED, START, DUE
date_from date
date_to date
created_at timestamptz
updated_at timestamptz
deleted_at timestamptz
}

Table task_filter_group_statuses {
group_status_id varchar2 [pk]
group_id varchar2 [not null]
status varchar(50) [not null] // REQUEST, IN_PROGRESS, COMPLETED, CLOSED, CANCELED
created_at timestamptz

Note: "UNIQUE(group_id, status)"
}

Ref: task_filter_group_statuses.group_id - task_filter_groups.group_id

///////////////////////////////////////////////////////
// Posts / Events (일정) / Feed /
///////////////////////////////////////////////////////

Table posts { //글
post_id varchar2 [pk]
project_id varchar2 [not null]
status varchar(30) [not null, default: 'ACTIVE'] //ACTIVE, DELETED
title varchar(255)
content text
created_by varchar2
created_at timestamptz
updated_at timestamptz
}

Ref: posts.project_id - projects.project_id
Ref: posts.created_by - users.user_id

Table events {//일정
event_id varchar2 [pk]
project_id varchar2 [not null]
title varchar(255)
start_datetime timestamptz
end_datetime timestamptz
location varchar(255)
content text
remind_before_minutes int
created_by varchar(255)
created_at timestamptz
updated_at timestamptz
}

Ref: events.project_id - projects.project_id
Ref: events.created_by - users.user_id

Table event_attendees {//일정 참여자
event_attendee_id varchar2 [pk]
event_id varchar2 [not null]
user_id varchar(255) [not null]
is_required boolean //필수 OR 선택 참여자
attend_status varchar(50) //INVITED, ACCEPTED, DECLINED, TENTATIVE, ATTENDED, ABSENT
}

Ref: event_attendees.event_id - events.event_id
Ref: event_attendees.user_id - users.user_id

// Feed를 아래 기능과 같이 사용하고 싶으면 테이블을 만들어야함
//[김예진] 님이 [일감 #123]을 생성했습니다
//[김예진] 님이 [인터페이스 ABC] 상태를 '개발' → '테스트' 으로 변경했습니다
//[김예진] 님이 [게시글 “DB 정합성 조사”]을 등록했습니다
Table feed_items {
feed_id varchar2 [pk]
project_id varchar2 [not null]
event_type varchar(100)
actor_id varchar(255)
target_type varchar(50)
target_id varchar2
summary text
created_at timestamptz
}

///////////////////////////////////////////////////////
// Comments / Mentions
///////////////////////////////////////////////////////

Table comments { //댓글
comment_id varchar2 [pk]
project_id varchar2 [not null]
parent_comment_id varchar2
target_type varchar(50) // POST, TASK, EVENT, FAQ, NOTICE
target_id varchar2 //target_id 에 글, 일감, 일정, FAQ, NOTICE id를 사용
content text
created_by varchar(255)
created_at timestamptz
updated_at timestamptz
}

Ref: comments.project_id - projects.project_id
Ref: comments.parent_comment_id - comments.comment_id
Ref: comments.created_by - users.user_id

Table mentions {//언급
mention_id varchar2 [pk]
project_id varchar2 [not null]
target_type varchar(50) // POST, TASK, EVENT, FAQ, NOTICE
target_id varchar2 //target_id 에 글, 일감, 일정, FAQ, NOTICE id를 사용
mentioned_user_id varchar(255)
created_by varchar(255)
created_at timestamptz
}

Ref: mentions.project_id - projects.project_id
Ref: mentions.mentioned_user_id - users.user_id
Ref: mentions.created_by - users.user_id

///////////////////////////////////////////////////////
// Systems & Connection Info
///////////////////////////////////////////////////////

Table systems {
system_id varchar2 [pk]
project_id varchar2 [not null]
system_code varchar(100) //excel import, export 시 사용자 친화
status varchar(30) [not null, default: 'ACTIVE'] //ACTIVE, INACTIVE, DELETED
description text
system_type varchar(50) //SAP, DB, FTP, SFTP 등
environment varchar(50) //DEV, QA, PRD 등
created_by varchar(255) [not null]
updated_by varchar(255) [not null]
created_at timestamptz
updated_at timestamptz

//CREATE UNIQUE INDEX ux_systems_project_code_active
//ON systems(project_id, system_code)
//WHERE status <> 'DELETED';

}

Ref: systems.project_id - projects.project_id
Ref: systems.created_by - users.user_id
Ref: systems.updated_by - users.user_id

Table connections {
connection_id varchar2 [pk]
project_id varchar2 [not null]
system_id varchar2 [not null]
name varchar(255)
protocol varchar(50)  // HTTP, SOAP, JDBC, SFTP, RFC ...
host varchar(255)
port int
path varchar(500)
username varchar(255) //계정
auth_type varchar(50)
extra_config jsonb
status varchar(30) [not null, default: 'ACTIVE'] //ACTIVE, INACTIVE, DEPRECATED, ERROR, PENDING,
TEST_ONLY, DELETED
description text
created_by varchar(255) [not null]
updated_by varchar(255) [not null]
created_at timestamptz
updated_at timestamptz
}

Ref: connections.project_id - projects.project_id
Ref: connections.system_id - systems.system_id
Ref: connections.created_by - users.user_id
Ref: connections.updated_by - users.user_id
Ref: "connections"."connection_id" < "connections"."project_id"

///////////////////////////////////////////////////////
// Files / Folders
///////////////////////////////////////////////////////

Table folders {
folder_id varchar2 [pk]
project_id varchar2 [not null]
parent_folder_id varchar2
name varchar(255)
created_by varchar(255)
created_at timestamptz
updated_at timestamptz
}

Ref: folders.project_id - projects.project_id
Ref: folders.parent_folder_id - folders.folder_id
Ref: folders.created_by - users.user_id

Table files {
file_id varchar2 [pk]
project_id varchar2 [not null]
folder_id varchar2
status varchar(30) [not null, default: 'ACTIVE'] //ACTIVE, DELETED
original_name varchar(255)
stored_name varchar(255)
size_bytes bigint
mime_type varchar(255)
uploaded_by varchar(255)
uploaded_at timestamptz
deleted_at timestamptz //삭제 후 얼마 기간이 지나면 물리 삭제를 하기 위함
}

Ref: files.project_id - projects.project_id
Ref: files.folder_id - folders.folder_id
Ref: files.uploaded_by - users.user_id

Table attachments {
attachment_id varchar2 [pk]
project_id varchar2 [not null]
file_id varchar2 [not null]         // files.file_id
target_type varchar(30) [not null] // 'TASK', 'POST', 'EVENT', 'FAQ', 'NOTICE'
target_id varchar2 [not null]       // task_id, post_id, event_id, notice_id, faq_id 등
attached_by varchar(255) [not null]     // users.user_id
attached_at timestamptz
}

Ref: attachments.file_id - files.file_id
Ref: attachments.attached_by - users.user_id
Ref: attachments.project_id - projects.project_id

////////////////////추가///////////////////////

Table roles {
role_id varchar2 [pk]
role_name varchar2 [not null, unique]
description varchar2
}

Table user_roles {
user_role_id varchar2 [pk]
user_id varchar(255) [not null]
role_id varchar2 [not null]

    Note: "UNIQUE(user_id, role_id)"

}

Ref: user_roles.user_id - users.user_id
Ref: user_roles.role_id - roles.role_id

Table notices {
notice_id varchar2 [pk]
project_id varchar2  [not null]
title varchar(255) [not null]
content text [not null]

    category varchar(50) // OPTIONAL: GENERAL, RELEASE, MAINTENANCE, POLICY 등
    importance varchar(20) [not null, default: 'NORMAL']
    // NORMAL, IMPORTANT, CRITICAL

    is_pinned boolean [not null, default: false] // 상단 고정
    pinned_at timestamptz

    status varchar(30) [not null, default: 'ACTIVE'] //ACTIVE, DELETED

    created_by varchar(255) [not null]
    updated_by varchar(255) [not null]
    created_at timestamptz
    updated_at timestamptz
    deleted_at timestamptz

}

Ref: notices.project_id - projects.project_id
Ref: notices.created_by - users.user_id
Ref: notices.updated_by - users.user_id

//물리삭제해버려
Table system_owners {
system_owner_id varchar2 [pk]
project_id varchar2 [not null]
system_id varchar2 [not null]
user_id varchar(255) [not null]
created_at timestamptz

Note: "UNIQUE(project_id, system_id, user_id)"
}

Ref: system_owners.project_id - projects.project_id
Ref: system_owners.system_id - systems.system_id
Ref: system_owners.user_id - users.user_id

// FAQs
Table faqs {
faq_id varchar2 [pk]

project_id varchar2 // 프로젝트별 FAQ, NULL이면 전체
category varchar(100)               // 계정, 권한, 일감, 일정, 인터페이스 등

question varchar(500) [not null]
answer text // NULL이면 미답변

assignee_id varchar(255)                // 답변 담당자 (users.user_id)
answered_at timestamptz // 답변 완료 시점

is_secret boolean [not null, default: false]
// true  : 비밀글 (작성자, 담당자만 조회)
// false : 공개 FAQ

status varchar(30) [not null, default: 'ACTIVE']
// ACTIVE, DELETED

created_by varchar(255) [not null]  // 질문 작성자
updated_by varchar(255) [not null]  // 질문 작성자

created_at timestamptz
updated_at timestamptz
}

Ref: faqs.assignee_id - users.user_id
Ref: faqs.created_by - users.user_id
Ref: faqs.updated_by - users.user_id
Ref: faqs.project_id - projects.project_id