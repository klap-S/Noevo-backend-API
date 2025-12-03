package noevo.controller;

//Jakarta imports
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
import noevo.service.implement.LogoutServiceImpl;

@RestController
@RequestMapping("/backend/api")
public class Login_LogoutController {

    @Autowired
    LoginServiceImpl loginServiceImpl;
    @Autowired
    LogoutServiceImpl logoutServiceImpl;

    // Login
    @PostMapping("/login")
    public UsuarioLoginResponseDTO login(@RequestBody @Valid UsuarioLoginRequestDTO loginDTO,
            HttpServletRequest servletRequest) {
        return loginServiceImpl.loginUser(loginDTO, servletRequest);
    }

    // Logout
    @PostMapping("/logout")
    public String logout(HttpServletRequest servletRequest, HttpServletResponse servletResponse) {
        logoutServiceImpl.logoutUser(servletRequest, servletResponse);
        return "Session cerrada correctamente";
    }
}
