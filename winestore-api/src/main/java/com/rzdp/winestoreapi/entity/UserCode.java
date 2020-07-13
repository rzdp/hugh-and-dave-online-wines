package com.rzdp.winestoreapi.entity;

import com.rzdp.winestoreapi.entity.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import static javax.persistence.CascadeType.DETACH;
import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.CascadeType.REFRESH;

@Entity
@Table(name = "UserCode")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
public class UserCode extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UserCodeId")
    private Long userCodeId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {DETACH, MERGE, PERSIST, REFRESH})
    @JoinColumn(name = "UserId")
    public User user;

    @NotBlank(message = "{user-code.code.not-blank}")
    @Size(min = 6, max = 6, message = "{user-code.code-path.size}")
    @Column(name = "Code")

    private String code;
    @Column(name = "Active")
    private boolean active;

}
