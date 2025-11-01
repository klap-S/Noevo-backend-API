package noevo.service.interfaces;

import java.util.List;
import noevo.model.Usuario;
import java.util.Optional;

public interface UsuarioService {

    //Basico
    List<Usuario> findAll();
    Optional<Usuario> findById(Long id);
    Usuario save (Usuario usuario);
    void deleteById(Long id);
    
}
