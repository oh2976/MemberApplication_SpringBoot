package com.suja.member.dto;

import com.suja.member.entity.MemberEntity;
import lombok.*;


// lomgok에서 제공하는 어노테이션
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MemberDTO {
    // DTO는 회원 정보의 필요한 내용을 필드를 private으로 정의한 후 getter, setter 메소드를 이용해서 간접적으로 사용

    private Long id;
    private String memberEmail;
    private String memberPassword;
    private String memberName;

    public static MemberDTO toMemberDTO(MemberEntity memberEntity){
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setId(memberEntity.getId());
        memberDTO.setMemberEmail(memberEntity.getMemberEmail());
        memberDTO.setMemberPassword(memberEntity.getMemberPassword());
        memberDTO.setMemberName(memberEntity.getMemberName());
        return memberDTO;
    }

}
