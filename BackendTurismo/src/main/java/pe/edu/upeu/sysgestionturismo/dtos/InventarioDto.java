package pe.edu.upeu.sysgestionturismo.dtos;

import lombok.Data;

@Data
public class InventarioDto {
    private Long idInventario;
    private String nombreItem;
    private Integer cantidadDisponible;
    private Long idDestino;
}
