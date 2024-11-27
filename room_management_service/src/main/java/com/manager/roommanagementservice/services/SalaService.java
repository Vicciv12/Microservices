package com.manager.roommanagementservice.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.manager.roommanagementservice.core.RoomService;
import com.manager.roommanagementservice.core.repositorys.RoomRepository;
import com.manager.roommanagementservice.models.Sala;
import com.manager.roommanagementservice.models.dto.NovaSalaDto;
import com.manager.roommanagementservice.models.dto.UpdateSalaDto;

@Service
public class SalaService implements RoomService{

    @Autowired
    private RoomRepository roomRepository;

    @Override
    public void save(NovaSalaDto salaDto) throws Exception {
        char letra = salaDto.getBloco().charAt(0);
        errorIfConteis(letra, salaDto.getNum());

        Sala sala = new Sala();
        sala.setLetra(letra);
        sala.setNumber(salaDto.getNum());
        sala.setStatus("Disponivel".toUpperCase());
        try {
            roomRepository.save(sala);
        } catch (Exception e) {
            throw new Exception("erro ao cadastrar sala tente novamente");
        }
    }
    
    @Override
    public void save(UpdateSalaDto salaDto, String code) throws Exception {
        Sala sala = findByCodeOrError(code);
        if(salaDto.getBloco() != null && salaDto.getNum() != 0){
            if(sala.getLetra() != salaDto.getBloco().charAt(0) || sala.getNumber() != salaDto.getNum()){
                errorIfConteis(salaDto.getBloco().charAt(0), salaDto.getNum(), "Não pode atualizar para uma sala qua ja está cadastrada tento outras informçoes");
            }
        }else if(salaDto.getBloco() == null && salaDto.getNum() != 0){
            if(sala.getNumber() != salaDto.getNum()){
                errorIfConteis(sala.getLetra(), salaDto.getNum(), "Não pode atualizar para uma sala qua ja está cadastrada tento outras informçoes");
            }
        }else if(salaDto.getNum() == 0 && salaDto.getBloco() != null){
            if(sala.getLetra() != salaDto.getBloco().charAt(0)){
                errorIfConteis(salaDto.getBloco().charAt(0), sala.getNumber(), "Não pode atualizar para uma sala qua ja está cadastrada tento outras informçoes");
            }
        }
        
        verifyStatus(salaDto.getStatus());

        if(salaDto.getBloco() != null){
            sala.setLetra(salaDto.getBloco().charAt(0));
        }
        if(salaDto.getNum() != 0){
            sala.setNumber(salaDto.getNum());
        }
        if(salaDto.getStatus() != null){
            sala.setStatus(salaDto.getStatus().toUpperCase());
        }

        StringBuilder bulder = new StringBuilder();
        bulder.append(sala.getLetra());
        bulder.append(sala.getNumber());
        sala.setCode(bulder.toString());
        try {
            roomRepository.save(sala);
        } catch (Exception e) {
            throw new Exception("erro ao Atualizar sala tente novamente");
        }

    }

    @Override
    public void deleteRoom(String code) throws Exception {
        Sala sala = findByCodeOrError(code);
        roomRepository.delete(sala); 
    }

    @Override
    public List<Sala> listAll() {
        return roomRepository.findAll();
    }

    
    @Override
    public Sala findByCode(String code) throws Exception {
        return findByCodeOrError(code);
    }

    
    @Override
    public void changeStatus(String code) throws Exception{
        Sala sala = findByCodeOrError(code);
        if(sala.getStatus().equalsIgnoreCase("ocupado")){
            sala.setStatus("disponivel".toUpperCase());
        }else{
            sala.setStatus("ocupado".toUpperCase());
        }
        try {
            roomRepository.save(sala);
        } catch (Exception e) {
            throw new Exception("erro ao Atualizar sala tente novamente");
        }

    }

    private void errorIfConteis(char l, int number) throws Exception{
        Sala sala = roomRepository.findByLetraAndNumber(l, number);

        if(sala != null){
            throw new Exception("sala "+sala.getCode()+" ja cadastrada");
        }
    }

    private void errorIfConteis(char l, int number, String msg) throws Exception{
        Sala sala = roomRepository.findByLetraAndNumber(l, number);

        if(sala != null){
            throw new Exception("sala "+sala.getCode()+" ja cadastrada");
        }
    }

    private Sala findByCodeOrError(String code) throws Exception{
        Sala sala = roomRepository.findByCode(code);

        if(sala == null){
            throw new Exception("sala "+code+" não encontrada");
        }

        return sala;
    }

    private void verifyStatus(String status) throws Exception{
        if( !(status.equalsIgnoreCase(("disponivel")) || status.equalsIgnoreCase(("ocupado")) || status.equalsIgnoreCase(("manutencao")))){
            throw new Exception("status invalido escolha entre {disponivel, ocupado, manutencao}");
        }
    }

}
