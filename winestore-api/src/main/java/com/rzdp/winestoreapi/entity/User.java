package com.rzdp.winestoreapi.entity;


import com.rzdp.winestoreapi.entity.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "[User]")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
public class User extends BaseEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UserId")
    private long userId;

    @Column(name = "Salutation")
    @Size(min = 3, max = 5, message = "{user.salutation.size}")
    @Pattern(regexp = "^(Mrs?|Ms).$", message = "{user.salutation.pattern}")
    private String salutation;

    @Column(name = "FirstName")
    @NotBlank(message = "{user.first-name.not-blank}")
    @Size(max = 50, message = "{user.first-name.size}")
    @Pattern(regexp = "^[A-Za-z\\u00f1\\u00d1 ]+$", message = "{user.first-name.pattern}")
    private String firstName;

    @Column(name = "MiddleName")
    @Size(max = 50, message = "{user.middle-name.size}")
    @Pattern(regexp = "^[A-Za-z\\u00f1\\u00d1 ]+$", message = "{user.middle-name.pattern}")
    private String middleName;

    @Column(name = "LastName")
    @NotBlank(message = "{user.last-name.not-blank}")
    @Size(max = 50, message = "{user.last-name.size}")
    @Pattern(regexp = "^[A-Za-z\\u00f1\\u00d1 ]+$", message = "{user.last-name.pattern}")
    private String lastName;

    @Column(name = "Suffix")
    @Size(min = 1, max = 5, message = "{user.suffix.size}")
    private String suffix;

    @Column(name = "BirthDate")
    @Temporal(TemporalType.DATE)
    private Date birthDate;

    @Column(name = "Active")
    private boolean active;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "AddressId")
    private Address address;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "ContactId")
    private Contact contact;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "AccountId")
    private Account account;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "user")
    private List<UserImage> userImages;

    public String getFullName() {
        return firstName + " " + middleName + " " + lastName;
    }
}
