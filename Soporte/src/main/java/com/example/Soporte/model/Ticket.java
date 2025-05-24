package com.example.Soporte.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Tickets")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long IdTicket;

    @Column(name = "DudasReclamos", nullable = false)
    private String DudaSug;

}

/*
{
  "dudaSug": ""
}
 */