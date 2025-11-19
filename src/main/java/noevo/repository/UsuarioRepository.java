package noevo.repository;

//Java imports
import java.util.Optional;

//Org imports
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//Noevo imports
import noevo.model.entity.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // Buscar usuario por el nombre
    Optional<Usuario> findByUserName(String userName);

    // Buscar email
    Optional<Usuario> findByEmail(String email);

    // Verificar si existe el nombre del usuario
    boolean existsByUserName(String userName);

    // Verificar si existe el email del usuario
    boolean existsByEmail(String email);
}
