package noevo.service.implement;

import noevo.model.entity.IA;
import noevo.repository.IARepository;
import noevo.service.interfaces.IAService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IAServiceImpl implements IAService {

    @Autowired
    private IARepository iaRepository;

    // Basico
    @Override
    public List<IA> findAll() {
        return iaRepository.findAll();
    }

    @Override
    public Optional<IA> findById(Long id) {
        return iaRepository.findById(id);
    }

    @Override
    public IA save(IA ia) {
        return iaRepository.save(ia);
    }

    @Override
    public void deleteById(Long id) {
        iaRepository.deleteById(id);
    }

    @Override
    public boolean existsByNombre(String nombre) {
        return iaRepository.existsByNombre(nombre);
    }

    @Override
    public Optional<IA> findByModelo(String modelo) {
        return iaRepository.findByModelo(modelo);
    }

    @Override
    public Optional<IA> findByNombre(String nombre) {
        return iaRepository.findByNombre(nombre);
    }

}
