package noevo.service;

import noevo.model.Usuario;
import noevo.repository.UsuarioRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServiceImpl implements IUsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public List<Usuario> obtenerUsuarios(String nombre) {
        return usuarioRepository.findByNombre(nombre);
    }

}
