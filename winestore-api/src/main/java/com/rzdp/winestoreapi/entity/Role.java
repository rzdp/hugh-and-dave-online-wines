package com.rzdp.winestoreapi.entity;

import com.rzdp.winestoreapi.entity.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

import static javax.persistence.CascadeType.DETACH;
import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.CascadeType.REFRESH;

@Entity
@Table(name = "Role")
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class Role extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RoleId")
    private Long roleId;

    @Column(name = "Name")
    @NotBlank(message = "{role.name.not-blank}")
    @Size(max = 50, message = "{role.name.size}")
    @Pattern(regexp = "^[A-Za-z ]+$", message = "{role.name.pattern}")
    private String name;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {DETACH, MERGE, PERSIST, REFRESH})
    @JoinTable(
            name = "RolePermission",
            joinColumns = @JoinColumn(name = "RoleId"),
            inverseJoinColumns = @JoinColumn(name = "PermissionId")
    )
    private List<Permission> permissions;
}
