package com.suja.member.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name = "member_table") // 테이블 이름
public class MemberEntity {
    @Id // Pk 설정
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
    private Long id;

    @Column(unique = true) // unique 제약 조건 추가
    private String memberEmail;

    @Column
    private String memberPassword;

    @Column
    private String memberName;

}
