package com.onetuks.ihub.entity.role;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = Role.TABLE_NAME)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Role {

  public static final String TABLE_NAME = "roles";

  @Id
  @Column(name = "role_id", nullable = false)
  private String roleId;

  @Column(name = "role_name", nullable = false)
  private String roleName;

  @Column(name = "description")
  private String description;

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Role role = (Role) o;
    return roleId.equals(role.roleId);
  }

  @Override
  public int hashCode() {
    return roleId.hashCode();
  }
}
