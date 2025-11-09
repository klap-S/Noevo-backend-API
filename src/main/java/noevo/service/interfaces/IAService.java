package noevo.service.interfaces;

import java.util.List;
import java.util.Optional;
import noevo.model.entity.IA;

public interface IAService {

    // Basico
    List<IA> findAll();

    Optional<IA> findById(Long id);

    IA save(IA ia);

    void deleteById(Long id);

    // Personalizado
    Optional<IA> findByNombre(String nombre);

    Optional<IA> findByModelo(String modelo);

    boolean existsByNombre(String nombre);

}
