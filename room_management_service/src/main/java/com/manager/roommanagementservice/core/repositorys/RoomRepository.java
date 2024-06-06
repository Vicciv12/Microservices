package com.manager.roommanagementservice.core.repositorys;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.manager.roommanagementservice.models.Sala;
import java.util.List;


@Repository
public interface RoomRepository extends JpaRepository<Sala, Long>{
    Sala findByCode(String code);
    List<Sala> findByLetra(char letra);
    List<Sala> findByNumber(int number);
    List<Sala> findByStatus(String status);
    Sala findByLetraAndNumber(char letra, int number);
}
