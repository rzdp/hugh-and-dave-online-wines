package com.rzdp.winestoreapi.entity;

import com.rzdp.winestoreapi.entity.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Table(name = "Contact")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
public class Contact extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ContactId")
    private Long contactId;

    @Column(name = "Mobile")
    @NotBlank(message = "{contact.mobile.not-blank}")
    @Size(min = 10, max = 10, message = "{contact.mobile.size}")
    @Pattern(regexp = "^[0-9]*$", message = "{contact.mobile.pattern}")
    private String mobile;

    @Column(name = "Tel")
    @Size(min = 7, max = 10, message = "{contact.tel.size}")
    @Pattern(regexp = "^[0-9]*$", message = "{contact.tel.pattern}")
    private String tel;

    @Column(name = "Fax")
    @Size(min = 7, max = 20, message = "{contact.fax.size}")
    @Pattern(regexp = "^[0-9]*$", message = "{contact.fax.pattern}")
    private String fax;

}
