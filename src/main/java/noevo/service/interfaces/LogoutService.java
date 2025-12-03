package noevo.service.interfaces;

//Jakarta imports
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface LogoutService {

    // Deslogueo de usuarios
    void logoutUser(HttpServletRequest servletRequest, HttpServletResponse servletResponse);

}
