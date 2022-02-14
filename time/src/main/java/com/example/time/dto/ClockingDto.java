package com.example.time.dto;

import com.example.time.model.Clocking;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ClockingDto {
    private LocalDateTime dateTime;
    private Boolean status;
    private String noteBody;
    private Long userId;

    public static ClockingDto toClockingDto(Clocking clocking, String noteBody) {
        ClockingDto clockingDto = new ClockingDto();

        clockingDto.setStatus(clocking.getStatus());

        if (clocking.getStatus()) {
            clockingDto.setDateTime(clocking.getClockInTime());
        } else {
            clockingDto.setDateTime(clocking.getClockOutTime());
        }

        clockingDto.setNoteBody(noteBody);

        return clockingDto;
    }
}
