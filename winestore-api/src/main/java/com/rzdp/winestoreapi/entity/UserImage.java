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
@Table(name = "UserImage")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UserImage extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, cascade = {DETACH, MERGE, PERSIST, REFRESH})
    @JoinColumn(name = "UserId")
    public User user;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UserImageId")
    private Long userImageId;
    @NotBlank(message = "{user-image.image-path.not-blank}")
    @Size(max = 150, message = "{user-image.image-path.size}")
    @Column(name = "ImagePath")
    private String imagePath;
}
