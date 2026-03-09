package filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import mx.DC.entity.Usuario;

import java.io.IOException;
import java.util.Set;

@WebFilter("*.xhtml")
public class AuthFilter implements Filter {

    /** Páginas que solo puede ver un administrador. */
    private static final Set<String> PAGINAS_ADMIN = Set.of(
            "/registroAsignatura.xhtml",
            "/registroDocente.xhtml",
            "/asignacionAsignatura.xhtml",
            "/consultaAsignaciones.xhtml"
    );

    @Override
    public void init(FilterConfig cfg) {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest  req  = (HttpServletRequest)  request;
        HttpServletResponse res  = (HttpServletResponse) response;
        String path        = req.getServletPath();
        String contextPath = req.getContextPath();

        // login.xhtml siempre es accesible
        if ("/login.xhtml".equals(path)) {
            chain.doFilter(request, response);
            return;
        }

        // Leer usuario de la sesión
        HttpSession session = req.getSession(false);
        Usuario usuario = (session != null)
                ? (Usuario) session.getAttribute("usuarioLogueado")
                : null;

        // Sin sesión → login
        if (usuario == null) {
            res.sendRedirect(contextPath + "/login.xhtml");
            return;
        }

        String rol    = usuario.getRol();
        boolean esAdmin = "administrador".equalsIgnoreCase(rol)
                       || "admin".equalsIgnoreCase(rol);

        // Profesor intentando acceder a página de admin → perfil
        if (!esAdmin && PAGINAS_ADMIN.contains(path)) {
            res.sendRedirect(contextPath + "/perfil.xhtml");
            return;
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {}
}
