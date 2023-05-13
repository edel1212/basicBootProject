package com.yoo.basicBoot.entity.user;

import com.yoo.basicBoot.entity.BaseEntity;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_member")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Member extends BaseEntity {
    @Id
    private String email;

    private String password;

    private String name;

    private boolean fromSocial;

    @Column(nullable = false)
    private String state;

    @Column(nullable = false)
    private String role;

}
