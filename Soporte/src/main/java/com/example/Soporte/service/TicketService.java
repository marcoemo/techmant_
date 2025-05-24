package com.example.Soporte.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Soporte.model.Ticket;
import com.example.Soporte.repository.TicketRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional

public class TicketService {
    @Autowired
    private TicketRepository TR;

    //método para obtener todos los tickets
    public List<Ticket> getTicket(){
        return TR.findAll();
    }
    //método para buscar por id
    public Ticket getTicket(Long id){
        return TR.findById(id)
        .orElseThrow(()-> new RuntimeException("ticket no encontrado"));
    }
    //método para crear tickets
    public Ticket saveTicket(Ticket ticket){
        return TR.save(ticket);
    }
}
