package noevo.service.implement;

//Jakarta imports
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Cookie;

//Org imports
import org.springframework.stereotype.Service;

//Noevo imports
import noevo.service.interfaces.LogoutService;

@Service
public class LogoutServiceImpl implements LogoutService {

    // Deslogueo de usuarios
    @Override
    public void logoutUser(HttpServletRequest servletRequest, HttpServletResponse servletResponse) {
        HttpSession session = servletRequest.getSession(false);
        if (session != null) {
            session.invalidate();

        }

        Cookie cookieSession = new Cookie("JSESSIONID", null);
        cookieSession.setMaxAge(0);
        cookieSession.setPath("/");
        servletResponse.addCookie(cookieSession);
    }
}
