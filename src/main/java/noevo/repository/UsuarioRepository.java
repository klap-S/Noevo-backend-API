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

    Optional<Usuario> findByUserName(String userName);

    Optional<Usuario> findByEmail(String email);

    boolean existsByUserName(String userName);

    boolean existsByEmail(String email);
}
