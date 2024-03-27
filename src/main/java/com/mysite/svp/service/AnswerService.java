package com.mysite.svp.service;

import com.mysite.svp.entity.Answer;
import com.mysite.svp.entity.Question;
import com.mysite.svp.entity.User;

public interface AnswerService {
    Answer create(Question question, String content, User user);
    Answer getAnswer(Long id);
    void modify(Answer answer, String content);
    void deleteAnswer(Long answerId);
}
