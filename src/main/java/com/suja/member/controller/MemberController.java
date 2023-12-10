package com.suja.member.controller;
import com.suja.member.dto.MemberDTO;
import com.suja.member.service.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/member/login")
    public String loginForm(){
        return "login";
    }

    @PostMapping("/member/login")
    public String login(@ModelAttribute MemberDTO memberDTO, HttpSession session){
       MemberDTO loginResult = memberService.login(memberDTO);
        if(loginResult != null){
            // login 성공
            // 로그인한 회원의 이메일 정보를 세션에 담아준다.
            session.setAttribute("loginEmail", loginResult.getMemberEmail());
            return "main";
        } else{
            // login 실패
            return "login";
        }
    }

    @GetMapping("/member/")
    public String findAll(Model model){
        // DB에 저장된 모든 데이터를 가져온다.
        // DB에서 하나의 데이터만 가져오는 것이 아니고 여러 개의 데이터를 가져오는 것이라서 List 형식을 사용
        // 다양한 데이터 타입을 같이 가져가는 경우는 map 사용
        List<MemberDTO> memberDTOList = memberService.findAll();

        // 어떤한 html로 가져갈 데이터가 있다면 model 사용
        // Model이란 List를 List.html로 가져가야할 때 사용
        model.addAttribute("memberList", memberDTOList);
        return "list";
    }

    @GetMapping("/member/{id}")
    // 일반적인 query string 방식을 쓸 때는 request parm를 많이 사요하고,
    // 경로 상에 있는 값을 가져올 때는 @PathVariable을 사용한다.
    // id에 해당하는 정보를 DB에서 가져와서 그 값을 다시 화면으로 가져가야 하기 때문에 Model 객체 필요
    public String findById(@PathVariable Long id, Model model){
        // 앞선 회원 목록은 여러 회원이기 때문에 List 타입으로 받았고,
        // 지금은 한 명이므로 DTO 타입으로 받아오면 된다.
        MemberDTO memberDTO = memberService.findById(id);
        model.addAttribute("member", memberDTO);
        return "detail";
    }

    @GetMapping("/member/update")
    public String updateForm(HttpSession session, Model model){
        String myEmail = (String) session.getAttribute("loginEmail");
        // 이메일을 통해서 DB를 조회하고 memberDTO에 값을 넣는다.
        MemberDTO memberDTO = memberService.updateForm(myEmail);
        model.addAttribute("updateMember", memberDTO);
        return "update";
    }

    @PostMapping("/member/update")
    public String update(@ModelAttribute MemberDTO memberDTO){
        memberService.update(memberDTO);
        // 그냥 return detail을 하면 같이 보낸 정보 값이 없어서 그냥 흰 화면을 보여준다.
        // 그럴 때 controller을 불러올 수도 있다.
        return "redirect:/member/" + memberDTO.getId();
    }

}
