package com.mysite.svp.controller;

import com.mysite.svp.entity.Answer;
import com.mysite.svp.entity.Question;
import com.mysite.svp.entity.User;
import com.mysite.svp.form.AnswerForm;
import com.mysite.svp.service.AnswerServiceImpl;
import com.mysite.svp.service.QuestionServiceImpl;
import com.mysite.svp.service.UserServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/answer")
public class AnswerController {
    private final QuestionServiceImpl questionServiceImpl;
    private final AnswerServiceImpl answerService;
    private final UserServiceImpl userServiceImpl;

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create/{id}")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> createAnswer(@PathVariable("id") Long id,
                                                            Principal principal,
                                                            @Valid AnswerForm answerForm,
                                                            BindingResult bindingResult) {
        Map<String, Object> response = new ConcurrentHashMap<>();

        if (bindingResult.hasErrors()) {
            response.put("status", 400);
            response.put("message", "답변 내용을 입력해주세요.");
        } else {
            Question question = questionServiceImpl.getQuestion(id);
            User user = userServiceImpl.getUser(principal.getName());
            Answer answer = answerService.create(question, answerForm.getContent(), user);
            response.put("status", 200);
            response.put("message", "댓글이 정상적으로 등록되었습니다.");
        }

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping("/update/{answerId}")
    @ResponseBody
    public ResponseEntity<String> updateAnswer(@PathVariable("answerId") Long answerId,
                                               @Valid AnswerForm answerForm,
                                               BindingResult bindingResult,
                                               Principal principal) {
        Answer answer = this.answerService.getAnswer(answerId);

        if (!answer.getAuthor().getUserId().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다.");
        }

        if (bindingResult.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "답변 내용을 입력해주세요.");
        }

        this.answerService.modify(answer, answerForm.getContent());

        return ResponseEntity.ok("수정되었습니다.");
    }

    //@PreAuthorize("isAuthenticated()")
    @DeleteMapping("/delete/{answerId}")
    @ResponseBody
    public ResponseEntity<String> deleteAnswer(@PathVariable("answerId") Long answerId, Principal principal) {
        Answer answer = this.answerService.getAnswer(answerId);

        if (!answer.getAuthor().getUserId().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제 권한이 없습니다.");
        }

        this.answerService.deleteAnswer(answerId);

        return ResponseEntity.ok("삭제되었습니다.");
    }
}