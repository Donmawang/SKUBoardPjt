package com.mysite.svp.util;

import org.springframework.context.annotation.Bean;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * 댓글의 시간과 관련된 기능입니다.
 * LocalDateTime과 Duration을 이용하여 방금 전, 몇 분 전, 몇 시간 전, 몇 일 전 등으로 표시합니다.
 * 일, 시, 분, 그리고 방금 전으로 표시하며 days, hours, minutes 변수를 이용하여 표시합니다.
 */
public class TimeUtil {
    public static String timeAgo(LocalDateTime dateTime) {
        LocalDateTime now = LocalDateTime.now();
        Duration duration = Duration.between(dateTime, now);

        long days = duration.toDays();
        if (days > 0) {
            return days + "일 전";
        }

        long hours = duration.toHours();
        if (hours > 0) {
            return hours + "시간 전";
        }

        long minutes = duration.toMinutes();
        if (minutes > 0) {
            return minutes + "분 전";
        }

        return "방금 전";
    }
}