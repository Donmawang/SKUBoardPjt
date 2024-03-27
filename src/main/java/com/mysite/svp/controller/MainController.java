package com.mysite.svp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 메인 컨트롤러 기본 홈페이지 설정
 */
@Controller
public class MainController {
    @GetMapping("/foc")
    @ResponseBody
    public String index() {
        return "foc 게시판에 오신 것을 환영합니다!";
    }

    @GetMapping("/")
    public String root() {
        return "redirect:/question/lists";
    }

    @GetMapping("/developer-introduce")
    public String developerIntroduce() {
        return "developer_introduce";
    }
}
