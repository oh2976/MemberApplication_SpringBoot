package com.suja.member.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

// controller를 스프링 bin에 넣기 위해 설정
@Controller
public class MemberController {
    // 회원의 로그인, 회원가입 등 회원의 요청 처리를 하기 위한 controller이다.
    // 회원 가입 페이지 출력 요청
    @GetMapping("/member/save")
    public String saveForm(){
        return "save";
    }

    @PostMapping("/member/save")
    public String save(@RequestParam("memberEmail") String memberEmail,
                       @RequestParam("memberPassword") String memberPassword,
                       @RequestParam("memberName") String memberName){
        System.out.println("MemberController.save"); // soutm -> 현재 메소드가 무엇인지 출력
        System.out.println("memberEmail = " + memberEmail + ", memberPassword = " + memberPassword + ", memberName = " + memberName); // soutp -> 매개변수를 자동완성 print 문으로 만들어줌
        return "index";
    }

}
