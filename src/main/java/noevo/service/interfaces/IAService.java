package noevo.service.interfaces;

import java.util.List;
import java.util.Optional;
import noevo.model.IA;

public interface IAService {

    //Basico
    List<IA> findAll();
    Optional<IA> findById(Long id);
    IA save (IA ia);
    void deleteById(Long id);
    
}
