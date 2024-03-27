package com.mysite.svp.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "answers")
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Setter
    private String content;
    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User author;
    private LocalDateTime localDateTime;
    @Setter
    private LocalDateTime modifiedDateTime;
    @ManyToOne(fetch = FetchType.LAZY)
    private Question question;

    @Builder
    public Answer(String content, User author,
                  LocalDateTime localDateTime, LocalDateTime modifiedDateTime, Question question) {
        this.content = content;
        this.author = author;
        this.localDateTime = localDateTime;
        this.modifiedDateTime = modifiedDateTime;
        this.question = question;
    }

}
