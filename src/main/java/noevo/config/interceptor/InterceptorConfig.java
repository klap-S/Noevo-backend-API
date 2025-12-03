package noevo.config.interceptor;

//Jakarta imports
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

//Org imports
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class InterceptorConfig implements HandlerInterceptor {

    // Interceptor para verificar autenticación de usuario en cada solicitud y asi
    // saber quien hace la petición
    @Override
    public boolean preHandle(HttpServletRequest servletRequest,
            HttpServletResponse servletResponse,
            Object handler) throws Exception {

        HttpSession session = servletRequest.getSession(false);

        if (session == null || session.getAttribute("usuarioId") == null) {
            servletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            servletResponse.getWriter().write("Usuario no autenticado");
            return false;
        }

        servletRequest.setAttribute("usuarioId", session.getAttribute("usuarioId"));
        servletRequest.setAttribute("rol", session.getAttribute("rol"));

        return true;
    }
}
