package com.example.time.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class InOut {
    private LocalDateTime in;
    private LocalDateTime out;
    private String difference;
}