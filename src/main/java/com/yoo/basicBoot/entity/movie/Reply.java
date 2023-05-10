package com.yoo.basicBoot.entity.movie;

import com.yoo.basicBoot.entity.BaseEntity;
import com.yoo.basicBoot.entity.user.Member;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name ="tbl_reply")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Reply extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rno;

    private String text;

    @ManyToOne( fetch = FetchType.LAZY)
    @ToString.Exclude
    private Member member;

    @ManyToOne
    @ToString.Exclude
    private Movie movie;

}
