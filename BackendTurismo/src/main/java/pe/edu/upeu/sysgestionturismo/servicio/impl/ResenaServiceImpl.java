package pe.edu.upeu.sysgestionturismo.servicio.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upeu.sysgestionturismo.modelo.Resena;
import pe.edu.upeu.sysgestionturismo.repositorio.ResenaRepository;
import pe.edu.upeu.sysgestionturismo.servicio.IResenaService;

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