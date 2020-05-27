package com.rzdp.winestoreapi.entity;

import com.rzdp.winestoreapi.entity.base.BaseEntity;
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
@Table(name = "[Address]")
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class Address extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AddressId")
    private Long addressId;

    @Column(name = "Line1")
    @NotBlank(message = "{address.line1.not-blank}")
    @Size(max = 100, message = "{address.line1.size}")
    private String line1;

    @Column(name = "Line2")
    @Size(max = 100, message = "{address.line2.size}")
    private String line2;

    @Column(name = "Line3")
    @Size(max = 100, message = "{address.line3.size}")
    private String line3;

    @Column(name = "City")
    @NotBlank(message = "{address.city.not-blank}")
    @Size(max = 50, message = "{address.city.size}")
    private String city;

    @Column(name = "State")
    @NotBlank(message = "{address.state.not-blank}")
    @Size(max = 50, message = "{address.state.size}")
    private String state;

    @Column(name = "Country")
    @NotBlank(message = "{address.country.not-blank}")
    @Size(max = 50, message = "{address.country.size}")
    private String country;

    @Column(name = "Postal")
    @NotBlank(message = "{address.postal.not-blank}")
    @Size(max = 12, message = "{address.postal.size}")
    @Pattern(regexp = "^[0-9]*$", message = "{address.postal.pattern}")
    private String postal;


}
