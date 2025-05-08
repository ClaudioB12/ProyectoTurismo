package pe.edu.upeu.sistematurismocapachica.mappers;

import pe.edu.upeu.sistematurismocapachica.dtos.InventarioDto;
import pe.edu.upeu.sistematurismocapachica.modelo.Destino;
import pe.edu.upeu.sistematurismocapachica.modelo.Inventario;

public class InventarioMapper {
    public static InventarioDto toDto(Inventario inventario) {
        InventarioDto dto = new InventarioDto();
        dto.setIdInventario(inventario.getIdInventario());
        dto.setNombreItem(inventario.getNombreItem());
        dto.setCantidadDisponible(inventario.getCantidadDisponible());
        dto.setIdDestino(inventario.getDestino() != null ? inventario.getDestino().getIdDestino() : null);
        return dto;
    }

    public static Inventario toEntity(InventarioDto dto) {
        Inventario inventario = new Inventario();
        inventario.setIdInventario(dto.getIdInventario());
        inventario.setNombreItem(dto.getNombreItem());
        inventario.setCantidadDisponible(dto.getCantidadDisponible());

        if (dto.getIdDestino() != null) {
            Destino destino = new Destino();
            destino.setIdDestino(dto.getIdDestino());
            inventario.setDestino(destino);
        }
        return inventario;
    }
}
