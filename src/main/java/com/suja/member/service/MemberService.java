package com.suja.member.service;
import com.suja.member.dto.MemberDTO;
import com.suja.member.entity.MemberEntity;
import com.suja.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// 스프링이 관리하는 객체, 즉 스프링 bin으로 처리
// 서비스에서는 리파이토리를 호출해야 한다.
@Service
@RequiredArgsConstructor
public class MemberService {
    private  final MemberRepository memberRepository;
    public void save(MemberDTO memberDTO){
        // 1. dto -> entity
        // 2. repository의 save 메서드 호출

        MemberEntity memberEntity = MemberEntity.toMemberEntity(memberDTO);
        memberRepository.save(memberEntity);

        // repository의 save 메서드 호출 (조건. entity 객체를 넘겨줘야 함)


    }

    public MemberDTO login(MemberDTO memberDTO){
        /*
            1. 회원이 입력한 이메일로 DB에서 조회를 함
            2. DB에서 조회한 비밃번호와 사용자가 입력한 비밀번호가 일치하는지 판단
         */

        Optional<MemberEntity> byMemberEmail = memberRepository.findByMemberEmail(memberDTO.getMemberEmail());
        if(byMemberEmail.isPresent()){
            // 조회 결과가 있다.(해당 이메일을 가진 회원 정보가 있다.)
            // Optional은 entity를 하나의 포장지로 한 번 감싼 것이다. 즉, 두 개의 포장지로 감싸진 것
            // 아래 코드는 Optinal 포장지를 제거하는 코드
            MemberEntity memberEntity = byMemberEmail.get();
            if(memberEntity.getMemberPassword().equals(memberDTO.getMemberPassword())){
                // 비밀번호 일치
                // 비밀번호가 일치하니 정보를 controller 쪽으로 넘겨줘야 한다.
                // service, repository에서는 entity로 조회를 하고 비교를 했는데 controller에서는 dto로 정보를 넘겨줘야 한다.
                // entity -> dto 변환 후 리턴
                // 이 전에 dto -> entity로 변환할 때는 entity 클래스에서 정의해줌 이번에는 entity -> dto로 변환하는 메소드를 dto에서 작성
                MemberDTO dto = MemberDTO.toMemberDTO(memberEntity);
                return dto;
            } else{
                // 비밀번호 불일치(로그인 실패)
                return null;
            }
        } else{
            // 조회 결과가 없다.(해당 이메일을 가진 회원이 없다.)
            return null;
        }

    }

    public List<MemberDTO> findAll() {
        // Repository에서 기본으로 제공하는 메소드이다.
        // List 객체가 Repository에서 넘어올 때 entity로 넘어오게 된다.
        List<MemberEntity> memberEntityList = memberRepository.findAll();

        // 그러나 controller로 넘겨줄 때 dto 객체로 보내줘야 하므로 변환
        // 담아갈 객체를 dto 객체로 생성
        List<MemberDTO> memberDTOList = new ArrayList<>();
        // 위의 로그인과 달리 여러 개의 entity를 여러 개의 dto로 담아가야 한다.
        // memberEntityList의 데이터를 하나하나 꺼내서 memberDTOList로 옮겨 담아야 한다.
        for(MemberEntity memberEntity: memberEntityList) {
            // entity 객체 하나하나를 dro로 변환시켜 list에 넣는다.
            // MemberDTO memberDTO = MemberDTO.toMemberDTO(memberEntity);
            // memberDTOList.add(memberDTO);
            memberDTOList.add(MemberDTO.toMemberDTO(memberEntity));
        }
        return memberDTOList;

    }

    public MemberDTO findById(Long id) {
        // Repository에서 기본으로 제공하는 메소드이다.
        // 하나의 정보를 조회할 때는 보통 Optional로 감싸서 객체를 넘겨준다.
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(id);
        if(optionalMemberEntity.isPresent()){
            // optional로 감싸진 포장지를 벗겨낼 때 optionalMemberEntity.get()를 사용
            // entity -> dto
            // MemberEntity memberEntity = optionalMemberEntity.get();
            // MemberDTO memberDTO = MemberDTO.toMemberDTO(memberEntity);
            // return memberDTO;
            return MemberDTO.toMemberDTO(optionalMemberEntity.get());
        } else {
            return null;
        }
    }

    public MemberDTO updateForm(String myEmail) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findByMemberEmail(myEmail);
        if(optionalMemberEntity.isPresent()){
            return MemberDTO.toMemberDTO(optionalMemberEntity.get());
        } else{
            return null;
        }
    }

    public void update(MemberDTO memberDTO) {
        // memberRepository에는 update 메소드가 없기 때문에 save를 사용해야 한다.
        // save 메소드는 id가 없으면 insert 쿼리를 수행한다.
        // 그러나 DB에 있는 entity 객체가 넘어오면 update 쿼리를 날려준다.
        // 예전에 만들어준 MemberEntity.java의 toMemberEntity() 메서드를 보면 id는 자동으로 부여되어 세팅하는 과정이 없다.
        // 그렇기 때문에 toUpdateMemberEntity() 메소드를 만들어줘야 한다.
        memberRepository.save(MemberEntity.toUpdateMemberEntity(memberDTO));

    }

    public void deleteById(Long id) {
        memberRepository.deleteById(id);
    }
}
