package com.mysite.svp.dto;

import com.mysite.svp.entity.Answer;
import com.mysite.svp.entity.Question;
import com.mysite.svp.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class AnswerDTO {
    private Long id;
    private User author;
    private String content;
    private LocalDateTime localDateTime;
    private LocalDateTime modifiedDateTime;
    private Question question;

    @Builder
    public AnswerDTO(Long id, User author, String content,
                     LocalDateTime localDateTime, LocalDateTime modifiedDateTime, Question question) {
        this.id = id;
        this.author = author;
        this.content = content;
        this.localDateTime = localDateTime;
        this.modifiedDateTime = modifiedDateTime;
        this.question = question;
    }

    public Answer toEntity() {
        return Answer.builder()
                .author(author)
                .content(content)
                .localDateTime(localDateTime)
                .modifiedDateTime(modifiedDateTime)
                .question(question)
                .build();
    }

    public static AnswerDTO fromEntity(Answer answer) {
        return AnswerDTO.builder()
                .id(answer.getId())
                .author(answer.getAuthor())
                .content(answer.getContent())
                .localDateTime(answer.getLocalDateTime())
                .question(answer.getQuestion())
                .build();
    }
}
