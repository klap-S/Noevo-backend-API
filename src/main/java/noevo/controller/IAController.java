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
import noevo.model.IA;
import noevo.service.interfaces.IAService;

@RestController
@RequestMapping("/ia")
public class IAController {

    @Autowired
    private IAService iaService;

    // Obtener toda la ia
    @GetMapping
    public List<IA> getAll() {
        return iaService.findAll();
    }

    // Obtener ia Id
    @GetMapping("/{id}")
    public IA getById(@PathVariable Long id) {
        return iaService.findById(id).orElse(null);
    }

    // Crear una IA
    @PostMapping
    public IA create(@RequestBody IA ia) {
        return iaService.save(ia);
    }

    // Actualizar ia
    @PutMapping("/{id}")
    public IA update(@PathVariable Long id, @RequestBody IA ia) {
        ia.setId(id);
        return iaService.save(ia);
    }

    // Eliminar ia
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        iaService.deleteById(id);
    }

}
