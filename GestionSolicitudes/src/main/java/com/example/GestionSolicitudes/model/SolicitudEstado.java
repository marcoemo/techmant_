package com.example.GestionSolicitudes.model;

public enum SolicitudEstado {
    NUEVA, ASIGNADA, EN_DIAGNOSTICO, FINALIZADA, CANCELADA
}

// en postman (PUT) para cambiar los estados -> solicitudes/{id}/estado?estado=FINALIZADA - "nota: la id es sin los {}"