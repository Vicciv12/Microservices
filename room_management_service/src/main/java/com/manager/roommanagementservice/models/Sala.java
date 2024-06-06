package com.manager.roommanagementservice.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import lombok.Data;

@Data
@Entity
public class Sala {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private char letra;

    @Column
    private int number;

    @Column
    private String code;

    @Column
    private String status;


    @PrePersist
    private void insertCode(){
        StringBuilder codeBuilder = new StringBuilder();
        codeBuilder.append(letra);
        if(number < 10){
            codeBuilder.append("00"+number);  
        }else if(number >= 10 && number < 100){
            codeBuilder.append("0"+number);  
        }else{
            codeBuilder.append(+number);  
        }
        code = codeBuilder.toString().toUpperCase();
    }

}
