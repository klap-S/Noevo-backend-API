package noevo.service.implement;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import noevo.model.Conversacion;
import noevo.repository.ConversacionRepository;
import noevo.service.interfaces.ConversacionService;

@Service
public class ConversacionServiceImpl implements ConversacionService {

    @Autowired
    private ConversacionRepository conversacionRepository;

    //Basico
    @Override
    public List<Conversacion> findAll() {
        return conversacionRepository.findAll();
    }

    @Override
    public Optional<Conversacion> findById(Long id) {
        return conversacionRepository.findById(id);
    }

    @Override
    public Conversacion save(Conversacion ia) {
        return conversacionRepository.save(ia);
    }

    @Override
    public void deleteById(Long id) {
        conversacionRepository.deleteById(id);
    }

    //Personalizada
    @Override
    public List<Conversacion> findByUsuarioId(Long usuarioId) {
        return conversacionRepository.findByUsuarioId(usuarioId);
    }

}
