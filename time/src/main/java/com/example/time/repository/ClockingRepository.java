package com.example.time.repository;

import com.example.time.model.Clocking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ClockingRepository extends JpaRepository<Clocking, Long> {

    @Query(value = "SELECT * FROM clocking c WHERE c.user_id=:userId ORDER BY c.id DESC LIMIT 1", nativeQuery = true)
    Optional<Clocking> findLastClockingByUserId(@Param("userId") long userId);

    @Query(value = "SELECT * FROM clocking c WHERE user_id=:userId AND (c.clock_in_time BETWEEN :start AND :end) OR" +
            " (c.clock_out_time BETWEEN :start AND :end) ORDER BY c.id", nativeQuery = true)
    List<Clocking> getMonthlyReport(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end, @Param("userId") Long userId);

    @Query(value = "WITH select_ids AS (SELECT c.id, c.user_id, c.status\n" +
            "                    FROM clocking c\n" +
            "                    WHERE (c.clock_in_time BETWEEN :start AND :end)\n" +
            "                       OR (c.clock_out_time BETWEEN :start AND :end)\n" +
            "                    ORDER BY c.id DESC\n" +
            "                    LIMIT 1)\n" +
            "SELECT si.user_id\n" +
            "FROM select_ids si\n" +
            "WHERE si.status = true;",
            nativeQuery = true)
    List<Long> getUserIdsForClockingFix(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

}


