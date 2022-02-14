package com.example.time.repository;

import com.example.time.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {
    @Query(value = "SELECT * FROM notes n WHERE n.user_id=:userId and n.date=:date", nativeQuery = true)
    Optional<Note> findNoteByUserIdAndDate(@Param("userId") Long userId, @Param("date") LocalDate date);
}
