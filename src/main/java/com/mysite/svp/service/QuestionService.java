package com.mysite.svp.service;

import com.mysite.svp.entity.Answer;
import com.mysite.svp.entity.Question;
import com.mysite.svp.entity.User;
import org.springframework.data.domain.Page;

import java.util.List;

public interface QuestionService {
    Page<Question> getList(int page, int pageSize);
    Question getQuestion(Long id);
    Question createQuestion(String username, String title, String content);
    void updateQuestion(Question question, String title, String content);
    void deleteQuestion(Question question);
    List<Answer> getAnswers();

    User getUser(String name);
}
