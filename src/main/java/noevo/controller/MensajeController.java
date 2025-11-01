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
import noevo.model.Mensaje;
import noevo.service.interfaces.MensajesService;

@RestController
@RequestMapping("/mensajes")
public class MensajeController {

    @Autowired
    private MensajesService mensajesService;

    // Obtener todo el mensaje
    @GetMapping
    public List<Mensaje> getAll() {
        return mensajesService.findAll();
    }

    // Obtener mensaje Id
    @GetMapping("/{id}")
    public Mensaje getById(@PathVariable Long id) {
        return mensajesService.findById(id).orElse(null);
    }

    // Crear una mensaje
    @PostMapping
    public Mensaje create(@RequestBody Mensaje mensaje) {
        return mensajesService.save(mensaje);
    }

    // Actualizar mensaje
    @PutMapping("/{id}")
    public Mensaje update(@PathVariable Long id, @RequestBody Mensaje mensaje) {
        mensaje.setId(id);
        return mensajesService.save(mensaje);
    }

    // Eliminar mensaje
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        mensajesService.deleteById(id);
    }

    // Obtener mensajes de una conversacion
    @GetMapping("/conversacion/{conversacionId}")
    public List<Mensaje> getByConversacion(@PathVariable Long conversacionId) {
        return mensajesService.findByConversacionId(conversacionId);
    }

}
