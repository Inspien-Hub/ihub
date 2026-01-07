package com.onetuks.ihub.entity.project;

import static com.onetuks.ihub.entity.project.FavoriteProject.TABLE_NAME;

import com.onetuks.ihub.entity.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
    name = TABLE_NAME,
    uniqueConstraints = @UniqueConstraint(
        name = "unq_project_user", columnNames = {"project_id", "user_id"}))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FavoriteProject {

  public static final String TABLE_NAME = "favorite_projects";

  @Id
  @Column(name = "favorite_project_id", nullable = false)
  private String favoriteProjectId;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "project_id", referencedColumnName = "project_id", nullable = false)
  private Project project;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
  private User user;

  @Column(name = "is_favorite", nullable = false)
  private Boolean isFavorite;
}
