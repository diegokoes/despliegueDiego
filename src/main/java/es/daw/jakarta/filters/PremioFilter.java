package es.daw.jakarta.filters;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import java.io.IOException;

@WebFilter("/productos")
public class PremioFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        // https://stackoverflow.com/questions/11468221/get-random-boolean-in-java
        boolean regaloSorpresa = Math.random() < 0.5;
        request.setAttribute("tienePremio", regaloSorpresa);
        chain.doFilter(request, response);
    }

}
