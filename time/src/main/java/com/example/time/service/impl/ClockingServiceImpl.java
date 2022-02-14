package com.example.time.service.impl;

import com.example.time.dto.ClockingDto;
import com.example.time.dto.DailyReport;
import com.example.time.dto.InOut;
import com.example.time.dto.MonthlyReportDto;
import com.example.time.model.Clocking;
import com.example.time.model.Note;
import com.example.time.repository.ClockingRepository;
import com.example.time.repository.NoteRepository;
import com.example.time.service.ClockingService;
import com.example.time.service.NoteService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class ClockingServiceImpl implements ClockingService {
    private final ClockingRepository clockingRepository;
    private final NoteRepository noteRepository;
    private final NoteService noteService;

    public ClockingServiceImpl(ClockingRepository clockingRepository, NoteRepository noteRepository, NoteService noteService) {
        this.clockingRepository = clockingRepository;
        this.noteRepository = noteRepository;
        this.noteService = noteService;
    }

    @Override
    public ClockingDto clockInOut(ClockingDto clockingDto) {
        Clocking clocking = new Clocking();
        if (clockingDto.getStatus()) {
            clocking.setClockInTime(clockingDto.getDateTime());
        } else {
            clocking.setClockOutTime(clockingDto.getDateTime());
        }
        clocking.setUserId(clockingDto.getUserId());
        clocking.setStatus(clockingDto.getStatus());

        String note = noteService.saveNote(clockingDto);
        Clocking savedClocking = clockingRepository.save(clocking);

        return ClockingDto.toClockingDto(savedClocking, note);
    }


    @Override
    public ClockingDto getLastUserClocking(ClockingDto clockingDto) {
        Note note = noteRepository.findNoteByUserIdAndDate(clockingDto.getUserId(), clockingDto.getDateTime().toLocalDate()).orElse(null);
        Clocking clocking = clockingRepository.findLastClockingByUserId(clockingDto.getUserId()).orElse(null);

        if (clocking != null) {
            if (note != null && note.getDate().equals(clockingDto.getDateTime().toLocalDate())) {
                return ClockingDto.toClockingDto(clocking, note.getBody());
            } else {
                return ClockingDto.toClockingDto(clocking, null);
            }
        } else {
            return null;
        }

    }

    @Override
    public MonthlyReportDto getMonthlyReport(String date, Long userId) {
        LocalDate localDate = LocalDate.parse(date + "-01");
        LocalDateTime startOfMonth = localDate.atStartOfDay();
        LocalDateTime endOfMonth = localDate.withDayOfMonth(localDate.lengthOfMonth()).atTime(LocalTime.MAX);
        List<Clocking> dbData = clockingRepository.getMonthlyReport(startOfMonth, endOfMonth, userId);

        LocalDate dayDate = null;
        if (!dbData.isEmpty()) {
            dayDate = dbData.get(0).getClockInTime().toLocalDate();
        }
        DailyReport dailyReport = new DailyReport();
        MonthlyReportDto monthlyReport = new MonthlyReportDto();
        boolean isFirst = true;

        for (Clocking clocking : dbData) {

            LocalDate clockingDate = null;
            if (clocking.getStatus()) {
                clockingDate = clocking.getClockInTime().toLocalDate();
            } else {
                clockingDate = clocking.getClockOutTime().toLocalDate();
            }

            if (dayDate != null && !dayDate.equals(clockingDate) || isFirst) {

                if (!isFirst) {
                    monthlyReport.getDailyReports().add(dailyReport);
                }

                isFirst = false;
                dailyReport = new DailyReport();
                InOut inOut = new InOut();
                dayDate = clockingDate;
                dailyReport.setDate(dayDate);
                inOut.setIn(clocking.getClockInTime());
                dailyReport.getInOuts().add(inOut);

            } else {
                int size = dailyReport.getInOuts().size();
                InOut inOut = dailyReport.getInOuts().get(size - 1);

                if (inOut.getOut() != null) {
                    InOut newInOut = new InOut();
                    newInOut.setIn(clocking.getClockInTime());
                    dailyReport.getInOuts().add(newInOut);
                } else {
                    inOut.setOut(clocking.getClockOutTime());
                }
            }
        }
        monthlyReport.getDailyReports().add(dailyReport);

        addNotes(monthlyReport, userId);
        addTimeCount(monthlyReport);
        return monthlyReport;
    }

    private void addNotes(MonthlyReportDto monthlyReport, Long userId) {
        for (DailyReport dailyReport : monthlyReport.getDailyReports()) {
            noteRepository.findNoteByUserIdAndDate(userId, dailyReport.getDate()).ifPresent(note -> dailyReport.setNoteBody(note.getBody()));
        }
    }

    private void addTimeCount(MonthlyReportDto monthlyReport) {
        List<DailyReport> dailyReports = monthlyReport.getDailyReports();
        long monthTime = 0;
        for (DailyReport dailyReport : dailyReports) {
            long dayTime = 0;
            for (InOut inOut : dailyReport.getInOuts()) {
                if (inOut.getOut() == null || inOut.getIn() == null) {
                    continue;
                }
                LocalDateTime in = inOut.getIn();
                LocalDateTime out = inOut.getOut();
                long inOutDifference = ChronoUnit.MINUTES.between(in, out);
                dayTime += inOutDifference;
                inOut.setDifference(toHoursMinFormat(inOutDifference));
            }
            dailyReport.setDaySum(toHoursMinFormat(dayTime));
            monthTime += dayTime;
        }
        monthlyReport.setMonthSum(toHoursMinFormat(monthTime));
    }

    private String toHoursMinFormat(long minutesBetween) {
        long hours = minutesBetween / 60;
        long minutes = minutesBetween % 60;
        return hours + ":" + minutes;
    }

}
