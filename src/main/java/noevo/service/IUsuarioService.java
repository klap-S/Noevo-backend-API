package noevo.service;

import java.util.List;
import noevo.model.Usuario;

public interface IUsuarioService {

    List<Usuario> obtenerUsuarios(String nombre);
    
}
