package com.example.time.jobs;

import com.example.time.model.Clocking;
import com.example.time.repository.ClockingRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Component

public class Jobs {
    private final ClockingRepository clockingRepository;

    public Jobs(ClockingRepository clockingRepository) {
        this.clockingRepository = clockingRepository;
    }

    @Scheduled(cron = "0 0/5 * * * ?")// fixme change to once per day at midnight  0 0 0 * * *
    public void fixClockInOutJob() {
        LocalDateTime startDayBefore = LocalDate.now().minusDays(1).atStartOfDay();
        LocalDateTime endDayBefore = LocalDate.now().minusDays(1).atTime(LocalTime.MAX).minusMinutes(1);
        List<Long> allActiveUserId = clockingRepository.getUserIdsForClockingFix(startDayBefore, endDayBefore);

        for (Long userId : allActiveUserId) {
            Clocking finishDay = new Clocking();
            finishDay.setClockOutTime(endDayBefore);
            finishDay.setStatus(false);
            finishDay.setUserId(userId);

            Clocking savedFinishDay = clockingRepository.save(finishDay);
            savedFinishDay.setId(null);
            savedFinishDay.setClockOutTime(null);
            savedFinishDay.setStatus(true);
            savedFinishDay.setClockInTime(startDayBefore.plusDays(1));
            clockingRepository.save(savedFinishDay);
        }

    }
}