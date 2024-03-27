package com.mysite.svp.form;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnswerForm {
    @NotEmpty(message = "내용은 필수 항목입니다.")
    @Size(min = 1, max = 500)
    private String content;
}
