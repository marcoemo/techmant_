package com.example.Soporte.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Soporte.model.Ticket;

@Repository

public interface TicketRepository extends JpaRepository<Ticket,Long>{

}
