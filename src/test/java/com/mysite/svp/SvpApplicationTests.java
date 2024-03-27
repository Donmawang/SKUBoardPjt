package com.mysite.svp;

import com.mysite.svp.dto.QuestionDTO;
import com.mysite.svp.entity.Question;
import com.mysite.svp.repository.QuestionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
class SvpApplicationTests {
	@Autowired
	private QuestionRepository questionRepository;

	@Test
	void jpaTest() {
		// Question 추가
		QuestionDTO questionDTO = QuestionDTO.builder()
				.title("님들 요즘 OP 챔피언이 무엇인가요?")
				.content("탑 라인입니다. OP챔 추천 좀 해주세요!")
				// 년/월/일/시/분
				.localDateTime(LocalDateTime.now())
				.build();

		Question question = questionDTO.toEntity();
		questionRepository.save(question);
	}
}
