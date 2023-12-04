package com.suja.member.controller;
import com.suja.member.dto.MemberDTO;
import com.suja.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

// controller를 스프링 bin에 넣기 위해 설정
@Controller
@RequiredArgsConstructor
public class MemberController {
    // 생성자 주입
    private final MemberService memberService;


    // 회원의 로그인, 회원가입 등 회원의 요청 처리를 하기 위한 controller이다.
    // 회원 가입 페이지 출력 요청
    @GetMapping("/member/save")
    public String saveForm(){
        return "save";
    }

    @PostMapping("/member/save")
    public String save(@ModelAttribute MemberDTO memberDTO){
        System.out.println("MemberController.save"); // soutm -> 현재 메소드가 무엇인지 출력
        System.out.println("memberDTO" + memberDTO);
        memberService.save(memberDTO);

        return "login";

    }

}
