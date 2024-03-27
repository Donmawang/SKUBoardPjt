package com.mysite.svp.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class QuestionDetailResponse {
    private Long id;
    private String title;
    private String content;
    private String author;
    private LocalDateTime localDateTime;
}
