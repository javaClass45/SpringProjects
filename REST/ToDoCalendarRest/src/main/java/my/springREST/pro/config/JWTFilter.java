package my.springREST.pro.config;

import com.auth0.jwt.exceptions.JWTVerificationException; //D:\JAVA\IdeaProjects\ToDoCalendarRest\java-jwt-4.1.0
import my.springREST.pro.security.JWTUtil;
import my.springREST.pro.services.PersonDetailService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;
    private final PersonDetailService personDetailService;

    public JWTFilter(JWTUtil jwtUtil, PersonDetailService personDetailService) {
        this.jwtUtil = jwtUtil;
        this.personDetailService = personDetailService;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && !authHeader.isBlank() && authHeader.startsWith("Bearer ")) {
            String jwt = authHeader.substring(7);

            if (jwt.isBlank()) {
                response.sendError(response.SC_BAD_REQUEST, "Bad JWT token in Bearer Header");

            } else {
                try {
                    String username = jwtUtil.validateTokenAndRetrieveClaim(jwt);
                    UserDetails userDetails = personDetailService.loadUserByUsername(username);
                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(userDetails,
                                    userDetails.getPassword(),
                                    userDetails.getAuthorities());
                    if (SecurityContextHolder.getContext().getAuthentication() == null) {
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                    }
                } catch (JWTVerificationException e) {
                    response.sendError(response.SC_BAD_REQUEST, "Bad JWT token");
                } catch (UsernameNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }

        filterChain.doFilter(request, response);
    }
}
