package com.suja.member.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    // 기본 페이지 요청 메서드
    // 주소창에 /가 들어오면 아래 메서드를 실행한다.
    @GetMapping("/")
    public String index(){
         return "index"; // => templates 폴더의 index.html을 찾아감
    }

}
