package com.example.Soporte.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ticket {

    private Long IdTicket;

  
    private String DudaSug;


    private Long usuarioId;
}

/*
{
  "dudaSug": ""
  "usuarioId": 1 //este es para q se pueda buscar el Ticket 
}
 */