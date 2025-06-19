package pe.edu.upeu.sistematurismocapachica.dtos;

import lombok.Data;
import pe.edu.upeu.sistematurismocapachica.modelo.Actividad;
import pe.edu.upeu.sistematurismocapachica.modelo.Destino;

import java.util.List;

@Data
public class PaqueteTuristicoDto {
    private Long idPaquete;
    private String nombre;
    private String descripcion;
    private Double precioTotal;

    private List<Long> actividadesIds;
    private Long idDestino;

    // Nuevos campos para respuesta
    private List<Actividad> actividades;
    private Destino destino;
}