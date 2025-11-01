package noevo.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import noevo.model.Conversacion;
import noevo.service.interfaces.ConversacionService;

@RestController
@RequestMapping("/conversaciones")
public class ConversacionController {

    @Autowired
    private ConversacionService conversacionesService;

    // Obtener toda la conversacion
    @GetMapping
    public List<Conversacion> getAll() {
        return conversacionesService.findAll();
    }

    // Obtener conversacion Id
    @GetMapping("/{id}")
    public Conversacion getById(@PathVariable Long id) {
        return conversacionesService.findById(id).orElse(null);
    }

    // Crear una conversacion
    @PostMapping
    public Conversacion create(@RequestBody Conversacion conversacion) {
        return conversacionesService.save(conversacion);
    }

    // Actualizar conversacion
    @PutMapping("/{id}")
    public Conversacion update(@PathVariable Long id, @RequestBody Conversacion conversacion) {
        conversacion.setId(id);
        return conversacionesService.save(conversacion);
    }

    // Eliminar conversacion
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        conversacionesService.deleteById(id);
    }

    //Obtener conversacion de un usuario
    @GetMapping("/usuario/{usuarioId}")
    public List<Conversacion> getByUsuario(@PathVariable Long usuarioId) {
        return conversacionesService.findByUsuarioId(usuarioId);
    }
    
}
