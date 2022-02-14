package com.example.time.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class DailyReport {
    private LocalDate date;
    private List<InOut> inOuts = new ArrayList<>();
    private String noteBody;
    private String daySum;
}