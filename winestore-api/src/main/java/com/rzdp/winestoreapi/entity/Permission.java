package com.rzdp.winestoreapi.entity;

import com.rzdp.winestoreapi.entity.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
@Table(name = "Permission")
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class Permission extends BaseEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PermissionId")
    private Long permissionId;

    @Column(name = "Name")
    @NotBlank(message = "{permission.name.not-blank}")
    @Size(max = 50, message = "{permission.name.size}")
    @Pattern(regexp = "^[A-Za-z ]+$", message = "{permission.name.pattern}")
    private String name;


    @ManyToMany(fetch = FetchType.LAZY, cascade = {DETACH, MERGE, PERSIST, REFRESH})
    @JoinTable(
            name = "RolePermission",
            joinColumns = @JoinColumn(name = "PermissionId"),
            inverseJoinColumns = @JoinColumn(name = "RoleId")
    )
    @ToString.Exclude
    private List<Role> roles;

}
