package com.mysite.svp.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class QuestionResponse {
    private Long id;
    private String title;
    private String author;
    private LocalDateTime localDateTime;
    private LocalDateTime modifiedDateTime;
}