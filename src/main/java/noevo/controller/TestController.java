package noevo.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class TestController {

    @GetMapping("/app/test")
    public String hello() {
        return "Funciona correctamente el backend";
    }

    @GetMapping("/administrador")
    public String administrador() {
        return "Hola, estas en el administrador";
    }

}
