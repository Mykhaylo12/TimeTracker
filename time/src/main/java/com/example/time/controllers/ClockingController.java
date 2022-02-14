package com.example.time.controllers;

import com.example.time.dto.ClockingDto;
import com.example.time.dto.MonthlyReportDto;
import com.example.time.dto.RequestReportDto;
import com.example.time.service.ClockingService;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.xml.bind.ValidationException;

@RestController
@RequestMapping("/clocking")
public class ClockingController {
    private final ClockingService clockingService;


    public ClockingController(ClockingService clockingService) {
        this.clockingService = clockingService;
    }


    @PostMapping("/last-user-clocking")
    public ClockingDto getLastUserClocking(@RequestBody ClockingDto clockingDto, BindingResult bindingResult) throws ValidationException {
        if (bindingResult.hasErrors()) {
            throw new ValidationException("clocking is not valid");
        }
        return clockingService.getLastUserClocking(clockingDto);
    }

    @PostMapping
    public ClockingDto clockInOut(@RequestBody ClockingDto clockingDto, BindingResult bindingResult) throws ValidationException {
        if (bindingResult.hasErrors()) {
            throw new ValidationException("clocking is not valid");
        }
        return clockingService.clockInOut(clockingDto);
    }

    @PostMapping("/report")
    public MonthlyReportDto getMonthlyReport(@RequestBody RequestReportDto requestReport, BindingResult bindingResult) throws ValidationException {
        if (bindingResult.hasErrors()) {
            throw new ValidationException("clocking is not valid");
        }
        return clockingService.getMonthlyReport(requestReport.getDate(), requestReport.getUserId());
    }

}
