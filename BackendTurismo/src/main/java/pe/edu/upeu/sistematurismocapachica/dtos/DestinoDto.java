package pe.edu.upeu.sistematurismocapachica.dtos;

import lombok.Data;

@Data
public class DestinoDto {
    private Long idDestino;
    private String nombre;
    private String descripcion;
    private String ubicacion;
}
