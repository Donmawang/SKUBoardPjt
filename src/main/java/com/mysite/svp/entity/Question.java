package com.mysite.svp.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "questions")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String content;
    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User author; // User 테이블의 userId를 외래키로 사용
    private LocalDateTime localDateTime;
    private LocalDateTime modifiedDateTime;
    @OneToMany(mappedBy="question", cascade = CascadeType.REMOVE)
    private List<Answer> answerList;

    @Builder
    public Question(Long id, String title, String content, User author,
                    LocalDateTime localDateTime, LocalDateTime modifiedDateTime, List<Answer> answerList) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.author = author;
        this.localDateTime = localDateTime;
        this.modifiedDateTime = modifiedDateTime;
        // arraylist가 null이면 새로운 arraylist를 생성해주기
        this.answerList = answerList != null ? answerList : new ArrayList<>();
    }
}
