package com.notification.notification_service.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.notification.notification_service.models.entitys.History;

@Repository
public interface HistoryRepository extends JpaRepository<History, Long>{
    
}
