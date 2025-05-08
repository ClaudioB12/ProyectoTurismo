package pe.edu.upeu.sistematurismocapachica.mappers;

import pe.edu.upeu.sistematurismocapachica.dtos.ResenaDto;
import pe.edu.upeu.sistematurismocapachica.modelo.Cliente;
import pe.edu.upeu.sistematurismocapachica.modelo.Destino;
import pe.edu.upeu.sistematurismocapachica.modelo.Resena;

public class ResenaMapper {
    public static ResenaDto toDto(Resena resena) {
        ResenaDto dto = new ResenaDto();
        dto.setIdResena(resena.getIdResena());
        dto.setComentario(resena.getComentario());
        dto.setCalificacion(resena.getCalificacion());
        dto.setIdCliente(resena.getCliente() != null ? resena.getCliente().getIdCliente() : null);
        dto.setIdDestino(resena.getDestino() != null ? resena.getDestino().getIdDestino() : null);
        return dto;
    }

    public static Resena toEntity(ResenaDto dto) {
        Resena resena = new Resena();
        resena.setIdResena(dto.getIdResena());
        resena.setComentario(dto.getComentario());
        resena.setCalificacion(dto.getCalificacion());

        if (dto.getIdCliente() != null) {
            Cliente cliente = new Cliente();
            cliente.setIdCliente(dto.getIdCliente());
            resena.setCliente(cliente);
        }
        if (dto.getIdDestino() != null) {
            Destino destino = new Destino();
            destino.setIdDestino(dto.getIdDestino());
            resena.setDestino(destino);
        }
        return resena;
    }
}
