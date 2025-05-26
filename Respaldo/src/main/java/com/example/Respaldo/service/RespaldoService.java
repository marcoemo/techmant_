package com.example.Respaldo.service;

import com.example.Respaldo.model.Respaldo;
import com.example.Respaldo.repository.RespaldoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.time.LocalDateTime;

@Service
public class RespaldoService {

    @Autowired
    private RespaldoRepository respaldoRepository;

    public Respaldo generarRespaldo() {
        String nombreArchivo = "respaldo_" + System.currentTimeMillis() + ".sql";
        LocalDateTime ahora = LocalDateTime.now();
        Respaldo respaldo = new Respaldo();

        try {
            // Crear carpeta respaldos si no existe 
            File carpeta = new File("respaldos");
            if (!carpeta.exists()) {
                carpeta.mkdirs();
            }

           
            String rutaCompleta = carpeta.getAbsolutePath() + File.separator + nombreArchivo;

            
            String comando = "C:\\laragon\\bin\\mysql\\mysql-8.4.3-winx64\\bin\\mysqldump.exe";

            ProcessBuilder pb = new ProcessBuilder(
                comando, "-u", "root", "db_techmant", "-r", rutaCompleta
            );

            pb.redirectErrorStream(true);
            Process proceso = pb.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(proceso.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(">> " + line);
            }

            int estado = proceso.waitFor();

            respaldo.setNombreArchivo(nombreArchivo);
            respaldo.setFechaRespaldo(ahora);
            respaldo.setExito(estado == 0);
            respaldo.setMensaje(estado == 0
                ? "Éxito. Guardado en carpeta local 'respaldos'."
                : "Falló el respaldo (código " + estado + ")");

        } catch (Exception e) {
            respaldo.setNombreArchivo(nombreArchivo);
            respaldo.setFechaRespaldo(ahora);
            respaldo.setExito(false);
            respaldo.setMensaje("Error: " + e.getMessage());
            e.printStackTrace();
        }

        return respaldoRepository.save(respaldo);
    }
}