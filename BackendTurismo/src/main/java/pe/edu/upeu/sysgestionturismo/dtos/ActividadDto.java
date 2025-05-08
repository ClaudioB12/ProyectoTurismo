package pe.edu.upeu.sysgestionturismo.dtos;

import lombok.Data;

@Data
public class ActividadDto {
    private Long idActividad;
    private String nombre;
    private String descripcion;
    private Double precio;
    private Long idDestino; // Relación con Destino
}
