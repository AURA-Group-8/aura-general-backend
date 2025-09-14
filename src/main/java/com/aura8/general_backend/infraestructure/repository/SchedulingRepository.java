package com.aura8.general_backend.infraestructure.repository;

import com.aura8.general_backend.infraestructure.entities.Scheduling;
import com.aura8.general_backend.enums.SchedulingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface SchedulingRepository extends JpaRepository<Scheduling, Integer> {
    List<Scheduling> findByStartDatetimeBetweenAndIsCanceledFalse(LocalDateTime startDate, LocalDateTime endDate);

    @Query("SELECT SUM(s.totalPrice) FROM Scheduling s WHERE s.startDatetime >= :startDate AND s.endDatetime <= :endDate AND s.isCanceled = false AND s.paymentStatus = 'PAGO'")
    Double getTotalFaturadoMes(LocalDateTime startDate, LocalDateTime endDate);

    @Query("SELECT count(*) FROM Scheduling s WHERE s.startDatetime >= :startDate AND s.endDatetime <= :endDate AND s.isCanceled = false AND s.status = 'FEITO'")
    Integer getTotalAtendimentosMes(LocalDateTime startDate, LocalDateTime endDate);

    @Query("SELECT count(*) FROM Scheduling s WHERE s.startDatetime >= :startDate AND s.endDatetime <= :endDate AND s.isCanceled = true")
    Integer getTotalAtendimentosCanceladosMes(LocalDateTime startDate, LocalDateTime endDate);

    @Query("SELECT s.users.username, count(*) as agendamentos FROM Scheduling s "
            + "WHERE s.isCanceled = false "
            + "AND s.status = 'FEITO'"
            + "GROUP BY s.users.username "
            + "ORDER BY agendamentos DESC "
            + "LIMIT 5"
    )
    List<String> getTopClientes();

    @Query("SELECT MIN(s.startDatetime) FROM Scheduling s WHERE s.isCanceled = false")
    Optional<LocalDateTime> findFirstDateScheduling();

    Integer countByUsersIdAndStatusAndIsCanceledFalse(Integer userId, SchedulingStatus status);

    Optional<Scheduling> findByIdAndIsDeletedFalse(Integer id);
}
