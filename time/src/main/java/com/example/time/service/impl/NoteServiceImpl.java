package com.example.time.service.impl;

import com.example.time.dto.ClockingDto;
import com.example.time.model.Note;
import com.example.time.repository.NoteRepository;
import com.example.time.service.NoteService;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class NoteServiceImpl implements NoteService {
    private final NoteRepository noteRepository;

    public NoteServiceImpl(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    @Override
    public Optional<Note> findNoteByUserIdAndDate(Long userId, LocalDate localDate) {
        return noteRepository.findNoteByUserIdAndDate(userId, localDate);
    }

    @Override
    public String saveNote(ClockingDto clockingDto) {
        Note newNote = new Note();
        newNote.setUserId(clockingDto.getUserId());
        newNote.setBody(clockingDto.getNoteBody());
        newNote.setDate(clockingDto.getDateTime().toLocalDate());
        return noteRepository.save(newNote).getBody();
    }

}

