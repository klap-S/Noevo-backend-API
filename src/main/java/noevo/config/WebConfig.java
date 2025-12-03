package noevo.config;

//Org imports
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//Noevo imports
import noevo.config.interceptor.InterceptorConfig;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private InterceptorConfig interceptorConfig;

    // Configuración de interceptores para la aplicación para los endpoints del
    // backend
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(interceptorConfig)
                .addPathPatterns("/backend/api/**")
                .excludePathPatterns("/backend/api/login", "/backend/api/logout",
                        "/backend/api/usuario/create/invitado",
                        "/backend/api/usuario/createUser");
    }
}
