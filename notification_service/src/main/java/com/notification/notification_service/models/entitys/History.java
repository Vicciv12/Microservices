package com.notification.notification_service.models.entitys;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import lombok.Data;

@Data
@Entity
public class History {
   
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_history;
    
    @Column
    private String identifierUser;

    @Column
    private LocalDateTime time;

    @PrePersist
    private void prePersist(){
        time = LocalDateTime.now();
    }

}
