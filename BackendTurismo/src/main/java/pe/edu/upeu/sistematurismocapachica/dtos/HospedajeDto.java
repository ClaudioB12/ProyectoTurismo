package pe.edu.upeu.sistematurismocapachica.dtos;

import lombok.Data;

@Data
public class HospedajeDto {
    private Long idHospedaje;
    private String nombre;
    private String descripcion;
    private Double precioPorNoche;
    private Long idDestino;
}
