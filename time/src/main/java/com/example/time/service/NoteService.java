package com.example.time.service;

import com.example.time.dto.ClockingDto;
import com.example.time.model.Note;

import java.time.LocalDate;
import java.util.Optional;

public interface NoteService {
    Optional<Note> findNoteByUserIdAndDate(Long userId, LocalDate localDate);

    String saveNote(ClockingDto clockingDto);
}
