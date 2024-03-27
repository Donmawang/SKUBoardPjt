package com.mysite.svp.service;

import com.mysite.svp.dto.AnswerDTO;
import com.mysite.svp.entity.Answer;
import com.mysite.svp.entity.Question;
import com.mysite.svp.entity.User;
import com.mysite.svp.exception.DataNotFoundException;
import com.mysite.svp.repository.AnswerRepository;
import com.mysite.svp.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AnswerServiceImpl implements AnswerService {
    private final AnswerRepository answerRepository;

    @Override
    public Answer create(Question question, String content, User user) {
        AnswerDTO answerDTO = AnswerDTO.builder()
                .question(question)
                .author(user)
                .content(content)
                .localDateTime(LocalDateTime.now())
                .build();
        answerRepository.save(answerDTO.toEntity());
        return answerDTO.toEntity();
    }

    @Override
    public Answer getAnswer(Long id) {
        Optional<Answer> answer = this.answerRepository.findById(id);
        if (answer.isPresent()) {
            return answer.get();
        } else {
            throw new DataNotFoundException("답변이 존재하지 않습니다.");
        }
    }

    @Override
    public void modify(Answer answer, String content) {
        answer.setContent(content);
        answer.setModifiedDateTime(LocalDateTime.now());

        answerRepository.save(answer);
    }

    @Override
    public void deleteAnswer(Long answerId) {
        Answer answer = answerRepository.findById(answerId)
                .orElseThrow(() -> new DataNotFoundException("답변이 존재하지 않습니다."));

        if (!answerId.equals(answer.getId())) {
            throw new DataNotFoundException("답변이 존재하지 않습니다.");
        }

        answerRepository.delete(answer);
    }
}
