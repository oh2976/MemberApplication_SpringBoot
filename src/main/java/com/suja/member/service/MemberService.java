package com.suja.member.service;
import com.suja.member.dto.MemberDTO;
import com.suja.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

// 스프링이 관리하는 객체, 즉 스프링 bin으로 처리
// 서비스에서는 리파이토리를 호출해야 한다.
@Service
@RequiredArgsConstructor
public class MemberService {
    private  final MemberRepository memberRepository = null;
    public void save(MemberDTO memberDTO){

    }
}
