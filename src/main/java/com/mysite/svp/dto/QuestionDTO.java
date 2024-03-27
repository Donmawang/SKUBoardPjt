package com.mysite.svp.dto;

import com.mysite.svp.entity.Question;
import com.mysite.svp.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class QuestionDTO {
    private Long id;
    private User author;
    private String title;
    private String content;
    private LocalDateTime localDateTime;
    private LocalDateTime modifiedDateTime;

    @Builder
    public QuestionDTO(Long id, User author, String title, String content,
                       LocalDateTime localDateTime, LocalDateTime modifiedDateTime) {
        this.id = id;
        this.author = author;
        this.title = title;
        this.content = content;
        this.localDateTime = localDateTime;
        this.modifiedDateTime = modifiedDateTime;
    }

    public Question toEntity() {
        return Question.builder()
                .id(id)
                .author(author)
                .title(title)
                .content(content)
                .localDateTime(localDateTime)
                .modifiedDateTime(modifiedDateTime)
                .build();
    }

    public QuestionDTO fromEntity(Question question) {
        return QuestionDTO.builder()
                .id(question.getId())
                .author(question.getAuthor())
                .title(question.getTitle())
                .content(question.getContent())
                .localDateTime(question.getLocalDateTime())
                .modifiedDateTime(question.getModifiedDateTime())
                .build();
    }
}
