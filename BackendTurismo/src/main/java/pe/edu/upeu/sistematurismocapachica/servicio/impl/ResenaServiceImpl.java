package pe.edu.upeu.sistematurismocapachica.servicio.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upeu.sistematurismocapachica.modelo.Resena;
import pe.edu.upeu.sistematurismocapachica.repositorio.ResenaRepository;
import pe.edu.upeu.sistematurismocapachica.servicio.IResenaService;

import java.util.List;

@Service
public class ResenaServiceImpl implements IResenaService {

    @Autowired
    private ResenaRepository resenaRepository;

    @Override
    public Resena save(Resena resena) {
        return resenaRepository.save(resena);
    }

    @Override
    public Resena update(Resena resena) {
        return resenaRepository.save(resena);
    }

    @Override
    public void delete(Long id) {
        resenaRepository.deleteById(id);
    }

    @Override
    public Resena findById(Long id) {
        return resenaRepository.findById(id).orElse(null);
    }

    @Override
    public List<Resena> findAll() {
        return resenaRepository.findAll();
    }
}