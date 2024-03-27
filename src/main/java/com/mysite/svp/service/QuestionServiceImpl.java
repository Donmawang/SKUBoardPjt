package com.mysite.svp.service;

import com.mysite.svp.dto.QuestionDTO;
import com.mysite.svp.entity.Answer;
import com.mysite.svp.entity.Question;
import com.mysite.svp.entity.User;
import com.mysite.svp.exception.DataNotFoundException;
import com.mysite.svp.repository.AnswerRepository;
import com.mysite.svp.repository.QuestionRepository;
import com.mysite.svp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService{
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private final UserRepository userRepository;

    @Override
    public List<Answer> getAnswers() {
        return answerRepository.findAll();
    }

    @Override
    public User getUser(String name) {
        return userRepository.findByUserId(name)
                .orElseThrow(() -> new DataNotFoundException("User not found"));
    }

    @Override
    public Question getQuestion(Long id) {
        Optional<Question> question = questionRepository.findById(id);

        if (question.isPresent()) {
            return question.get();
        } else {
            throw new DataNotFoundException("Question not found");
        }
    }

    @Override
    public Page<Question> getList(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by("id").descending());
        return questionRepository.findAll(pageable);
    }

    @Override
    public Question createQuestion(String username, String title, String content) {
        User user = this.userRepository.findByUserId(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        QuestionDTO questionDTO = QuestionDTO.builder()
                .author(user)
                .title(title)
                .content(content)
                .localDateTime(LocalDateTime.now())
                .build();

        return questionRepository.save(questionDTO.toEntity());
    }

    @Override
    public void updateQuestion(Question question, String title, String content) {
        QuestionDTO questionDTO = QuestionDTO.builder()
                .id(question.getId())
                .author(question.getAuthor())
                .title(title)
                .content(content)
                .localDateTime(question.getLocalDateTime())
                .modifiedDateTime(LocalDateTime.now())
                .build();

        questionRepository.save(questionDTO.toEntity());
    }

    @Override
    public void deleteQuestion(Question question) {
        questionRepository.delete(question);
    }
}
