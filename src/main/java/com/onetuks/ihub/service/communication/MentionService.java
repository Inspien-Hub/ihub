package com.onetuks.ihub.service.communication;

import com.onetuks.ihub.entity.communication.Mention;
import com.onetuks.ihub.entity.communication.TargetType;
import com.onetuks.ihub.entity.user.User;
import com.onetuks.ihub.repository.MentionJpaRepository;
import com.onetuks.ihub.service.project.ProjectMemberCheckComponent;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MentionService {

  private final MentionJpaRepository mentionJpaRepository;
  private final ProjectMemberCheckComponent projectMemberCheckComponent;

  @Transactional(readOnly = true)
  public Page<Mention> getMyMentions(
      User currentUser,
      String projectId,
      List<String> authorIds,
      List<TargetType> targetTypes,
      LocalDateTime from,
      LocalDateTime to,
      Pageable pageable
  ) {
    if (projectId != null) {
      projectMemberCheckComponent.checkIsProjectMember(currentUser, projectId);
    }

    List<Mention> mentions = mentionJpaRepository.findAllByMentionedUser_UserId(
        currentUser.getUserId());

    List<Mention> filtered = mentions.stream()
        .filter(mention -> projectId == null
            || Objects.equals(projectId, mention.getProject().getProjectId()))
        .filter(mention -> authorIds == null || authorIds.isEmpty()
            || (mention.getCreatedBy() != null
            && authorIds.contains(mention.getCreatedBy().getUserId())))
        .filter(mention -> targetTypes == null || targetTypes.isEmpty()
            || targetTypes.contains(mention.getTargetType()))
        .filter(mention -> from == null
            || (mention.getCreatedAt() != null && !mention.getCreatedAt().isBefore(from)))
        .filter(mention -> to == null
            || (mention.getCreatedAt() != null && !mention.getCreatedAt().isAfter(to)))
        .sorted(Comparator.comparing(Mention::getCreatedAt, Comparator.nullsLast(Comparator.naturalOrder())))
        .toList();

    List<Mention> pageContent = slice(filtered, pageable);
    return new PageImpl<>(pageContent, pageable, filtered.size());
  }

  private List<Mention> slice(List<Mention> mentions, Pageable pageable) {
    int start = (int) pageable.getOffset();
    if (start >= mentions.size()) {
      return List.of();
    }
    int end = Math.min(start + pageable.getPageSize(), mentions.size());
    return mentions.subList(start, end);
  }
}
