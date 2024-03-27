package com.mysite.svp.controller;

import com.mysite.svp.entity.Question;
import com.mysite.svp.form.AnswerForm;
import com.mysite.svp.form.QuestionForm;
import com.mysite.svp.response.QuestionDetailResponse;
import com.mysite.svp.response.QuestionResponse;
import com.mysite.svp.service.QuestionServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/question")
public class QuestionController {
    private final QuestionServiceImpl questionServiceImpl;

    @GetMapping("/lists")
    public String getQuestionListPage(Model model, @RequestParam(defaultValue = "0") int page) {
        int pageSize = 10;
        Page<Question> questions = questionServiceImpl.getList(page, pageSize);
        model.addAttribute("questions", questions);
        return "question_lists";
    }

    @GetMapping(value = "/detail/{id}")
    public String detail(Model model, @PathVariable("id") Long id, AnswerForm answerForm) {
        Question question = questionServiceImpl.getQuestion(id);
        model.addAttribute("question", question);

        return "question_detail";
    }

    @GetMapping("/create")
    public String questionCreate(QuestionForm questionForm) {
        return "question_form";
    }

    @PostMapping("/create")
    public ResponseEntity<String> createAnswer(@RequestBody @Valid QuestionForm questionForm,
                                               BindingResult bindingResult, Principal principal) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("필수 항목을 입력해주세요.");
        }

        String username = principal != null ? principal.getName() : null;
        String title = questionForm.getTitle();
        String content = questionForm.getContent();

        Question savedQuestion = this.questionServiceImpl.createQuestion(username, title, content);

        return ResponseEntity.ok("id : " + savedQuestion.getId());
    }

    @GetMapping("/modify/{id}")
    public String showModifyForm(@PathVariable("id") Long id, Model model) {
        Question question = questionServiceImpl.getQuestion(id);
        model.addAttribute("question", question);

        return "question_modify";
    }

    @PutMapping("/modify/{id}")
    @ResponseBody
    public ResponseEntity<String> modifyAnswer(@RequestBody QuestionForm questionForm,
                                               @PathVariable("id") Long id,
                                               Principal principal) {
        Question question = questionServiceImpl.getQuestion(id);

        if (!question.getAuthor().getUserId().equals(principal.getName())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("자신의 글만 수정할 수 있습니다.");
        }

        questionServiceImpl.updateQuestion(question, questionForm.getTitle(), questionForm.getContent());

        return ResponseEntity.ok("id: " + question.getId());
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String questionDelete(Principal principal, @PathVariable("id") Long id) {
        Question question = questionServiceImpl.getQuestion(id);

        if (!question.getAuthor().getUserId().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "자신의 글만 삭제할 수 있습니다.");
        }

        questionServiceImpl.deleteQuestion(question);

        return "redirect:/question/list";
    }

    /**
     * 조회 및 개별 게시글 조회 API 영역
     */

    @GetMapping(value = "/api/list", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<QuestionResponse> getQuestionListApi() {
        List<Question> questions = questionServiceImpl.getList(0, 10).getContent();
        return questions.stream()
                .map(this::convertToQuestionResponse)
                .collect(Collectors.toList());
    }

    @GetMapping(value = "/api/detail/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public QuestionDetailResponse getQuestionApi(@PathVariable("id") Long id) {
        Question question = questionServiceImpl.getQuestion(id);
        return convertToQuestionDetailResponse(question);
    }

    private QuestionResponse convertToQuestionResponse(Question question) {
        return QuestionResponse.builder()
                .id(question.getId())
                .title(question.getTitle())
                .author(question.getAuthor().getUserId())
                .localDateTime(question.getLocalDateTime())
                .modifiedDateTime(question.getModifiedDateTime())
                .build();
    }

    private QuestionDetailResponse convertToQuestionDetailResponse(Question question) {
        return QuestionDetailResponse.builder()
                .id(question.getId())
                .title(question.getTitle())
                .content(question.getContent())
                .author(question.getAuthor().getUserId())
                .localDateTime(question.getLocalDateTime())
                .build();
    }
}
