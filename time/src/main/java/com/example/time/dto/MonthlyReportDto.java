package com.example.time.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class MonthlyReportDto {
    private List<DailyReport> dailyReports = new ArrayList<>();
    private String monthSum;
}
