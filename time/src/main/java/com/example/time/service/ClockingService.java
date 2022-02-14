package com.example.time.service;

import com.example.time.dto.ClockingDto;
import com.example.time.dto.MonthlyReportDto;

public interface ClockingService {
    ClockingDto clockInOut(ClockingDto clocking);

    ClockingDto getLastUserClocking(ClockingDto clocking);

    MonthlyReportDto getMonthlyReport(String date, Long userId);
}
