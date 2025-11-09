package noevo.service.interfaces;

import java.util.List;
import java.util.Optional;
import noevo.model.entity.Usuario;

public interface UsuarioService {

    // Basico
    List<Usuario> findAll();

    Optional<Usuario> findById(Long id);

    Usuario save(Usuario usuario);

    void deleteById(Long id);

    // Personalizado
    Optional<Usuario> findByNombre(String nombre);

    Optional<Usuario> findByEmail(String email);

    boolean existsByNombre(String nombre);

    boolean existsByEmail(String email);

}
