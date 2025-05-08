package pe.edu.upeu.sistematurismocapachica.dtos;

import lombok.Data;
import java.util.List;

@Data
public class PaqueteTuristicoDto {
    private Long idPaquete;
    private String nombre;
    private String descripcion;
    private Double precioTotal;
    private List<Long> actividadesIds; // Lista de IDs de Actividades
    private Long idDestino;
}
