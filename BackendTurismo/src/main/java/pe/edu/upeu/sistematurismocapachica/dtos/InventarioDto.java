package pe.edu.upeu.sistematurismocapachica.dtos;

import lombok.Data;

@Data
public class InventarioDto {
    private Long idInventario;
    private String nombreItem;
    private Integer cantidadDisponible;
    private Long idDestino;
}
