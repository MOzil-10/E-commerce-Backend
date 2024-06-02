package EcommerceBackend.Ecommerce.Config;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * SimpleCorsFilter is a servlet filter for handling Cross-Origin Resource Sharing (CORS) requests.
 * This filter sets the necessary CORS headers to allow requests from specified origins.
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class SimpleCorsFilter implements Filter {

    @Value("${app.client.url}")
    private String clientAppUrl;

    /**
     * Default constructor for SimpleCorsFilter.
     */
    public SimpleCorsFilter() {}

    /**
     * Adds CORS headers to the response and handles preflight (OPTIONS) requests.
     *
     * @param req the ServletRequest object contains the client's request
     * @param res the ServletResponse object contains the filter's response
     * @param chain the FilterChain for invoking the next filter or resource
     * @throws IOException if an I/O related error has occurred during processing
     * @throws ServletException if the processing fails for any other reason
     */
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) res;
        HttpServletRequest request = (HttpServletRequest) req;

        // Set CORS headers
        String originHeader = request.getHeader("origin");
        response.setHeader("Access-Control-Allow-Origin", originHeader);
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "*");

        // Handle preflight (OPTIONS) request
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            chain.doFilter(req, res);
        }
    }
}
