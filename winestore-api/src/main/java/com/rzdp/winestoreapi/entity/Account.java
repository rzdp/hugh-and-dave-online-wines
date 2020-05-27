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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import static javax.persistence.CascadeType.DETACH;
import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.CascadeType.REFRESH;

@Entity
@Table(name = "Account")
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class Account extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AccountId")
    private Long accountId;

    @Column(name = "Email", unique = true)
    @NotBlank(message = "{account.email.not-blank}")
    @Size(max = 50, message = "{account.email.size}")
    @Email(message = "{account.email.email}")
    private String email;

    @Column(name = "Password")
    @NotBlank(message = "{account.password.not-blank}")
    @Size(min = 8, max = 128, message = "{account.password.size}")
    private String password;

    @Column(name = "Verified")
    private boolean verified;

    @OneToOne(fetch = FetchType.LAZY, cascade = {DETACH, MERGE, PERSIST, REFRESH})
    @JoinColumn(name = "RoleId")
    private Role role;

    @OneToOne(fetch = FetchType.LAZY, cascade = {DETACH, MERGE, PERSIST, REFRESH},
            mappedBy = "account")
    @ToString.Exclude
    private User user;
}
