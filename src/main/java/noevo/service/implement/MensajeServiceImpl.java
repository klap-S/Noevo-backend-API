package noevo.service.implement;

import java.util.List;
import java.util.Optional;
import noevo.model.Mensaje;
import noevo.repository.MensajeRepository;
import noevo.service.interfaces.MensajesService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;;

@Service
public class MensajeServiceImpl implements MensajesService {

    @Autowired
    private MensajeRepository mensajeRepository;

    //Basico
    @Override
    public List<Mensaje> findAll() {
        return mensajeRepository.findAll();
    }

    @Override
    public Optional<Mensaje> findById(Long id) {
        return mensajeRepository.findById(id);
    }

    @Override
    public Mensaje save(Mensaje mensaje) {
        return mensajeRepository.save(mensaje);
    }

    @Override
    public void deleteById(Long id) {
        mensajeRepository.deleteById(id);
    }

    //Personalizado
    @Override
    public List<Mensaje> findByConversacionId(Long conversacionId) {
        return mensajeRepository.findByConversacionId(conversacionId);
    }
}
