package noevo.controller;

//Jakarta imports
import jakarta.validation.Valid;

//Org imports
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

//Noevo imports
import noevo.model.dto.usuario.UsuarioLoginRequestDTO;
import noevo.model.dto.usuario.UsuarioLoginResponseDTO;
import noevo.service.implement.LoginServiceImpl;

@RestController
@RequestMapping("/backend/api/login")
public class LoginUsuarioController {

    @Autowired
    LoginServiceImpl loginServiceImpl;

    // Login
    @PostMapping
    public UsuarioLoginResponseDTO login(@RequestBody @Valid UsuarioLoginRequestDTO loginDTO) {
        return loginServiceImpl.loginUser(loginDTO);
    }
}
