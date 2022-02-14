package com.example.time.model;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "notes")
public class Note implements Serializable {
    @Id
    private LocalDate date;
    @NotNull
    private Long userId;
    private String body;
}
