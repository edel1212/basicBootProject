package com.yoo.basicBoot.entity.movie;

import com.yoo.basicBoot.entity.BaseEntity;
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

    private String replier;

    @ManyToOne
    @ToString.Exclude
    private Movie movie;

}
