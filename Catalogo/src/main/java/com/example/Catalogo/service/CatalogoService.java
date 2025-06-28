package com.example.Catalogo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Catalogo.model.Catalogo;
import com.example.Catalogo.repository.CatalogoRepository;

import jakarta.annotation.PostConstruct;

@Service
public class CatalogoService {

    @Autowired
    private final CatalogoRepository CR;



    public CatalogoService(CatalogoRepository CR){
            this.CR=CR;
    }
    @PostConstruct
    public void init() {
        // IDs y datos de ejemplo para inicializar el catálogo
        List<Catalogo> catalogosIniciales = List.of(
            crearCatalogoInicial("Reparación","Reparación de fallas de hardware y software en notebooks.", 20000),
            crearCatalogoInicial("Instalación de software","Instalación y configuración de programas y sistemas.",10000),
            crearCatalogoInicial("Mantenimiento preventivo de equipos","Revisión y limpieza para prevenir fallas técnicas.",15000),
            crearCatalogoInicial("Formateo y respaldo de datos","Respaldo de archivos y formateo completo del equipo.",19990),
            crearCatalogoInicial("Revisión técnica de hardware","Chequeo de componentes internos y funcionamiento.",23500),
            crearCatalogoInicial("Limpieza interna de equipos","Limpieza física interna para mejorar rendimiento.",15990),
            crearCatalogoInicial("Diagnóstico general","Evaluación completa del equipo y sus fallas.",30000),
            crearCatalogoInicial("Configuración de red local","Instalación y ajuste de redes domésticas u oficina.",50000),
            crearCatalogoInicial("Asesoría en compras de tecnología","Recomendaciones para adquirir tecnología adecuada.",10000)
        );

        crearCatalogosSiNoExisten(catalogosIniciales);
    }

    private Catalogo crearCatalogoInicial(String nombre, String descripcion, int precio) {
        Catalogo catalogo = new Catalogo();
        catalogo.setNombre(nombre);
        catalogo.setDescripcion(descripcion);
        catalogo.setPrecio(precio);
        return catalogo;
    }

    private void crearCatalogosSiNoExisten(List<Catalogo> catalogos) {
        int creados = 0;
        for (Catalogo catalogo : catalogos) {
            if (CR.findAll().stream().noneMatch(c -> c.getNombre().equalsIgnoreCase(catalogo.getNombre()))) {
                CR.save(catalogo);
                creados++;
                System.out.println("Catálogo creado: " + catalogo.getNombre());
            }
        }
        System.out.println("Total catálogos creados: " + creados + "/" + catalogos.size());
    }

    
    public List<Catalogo> obtenerCatalogo(){
        return CR.findAll();
    }
    // Endpoint para obtener un catálogo por su ID, necesario para que controller funcione
    public Catalogo obtenerPorId(Long id){
        return CR.findById(id).orElse(null);
    }

    //eliminar un servicio del catálogo por id  -- administrador
    public boolean eliminarCatalogo(Long id){
        if(CR.existsById(id)){
            CR.deleteById(id); 
            return true;
        }
        return false;
    }

    //agregar un nuevo servicio al catálogo -- administrador
    public Catalogo crearCatalogo(Catalogo catalogo){
        return CR.save(catalogo);
    }

    //actualizar un servicio del catálogo por id -- administrador
    public Catalogo actualizarCatalogo(Long id, Catalogo nuevoCatalogo) {
        Catalogo actual = obtenerPorId(id);
        if (actual != null) {
            actual.setNombre(nuevoCatalogo.getNombre());
            actual.setDescripcion(nuevoCatalogo.getDescripcion());
            actual.setPrecio(nuevoCatalogo.getPrecio());
            return CR.save(actual);
        }
        return null;
    }
}
