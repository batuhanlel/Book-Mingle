package com.lel.bookmingle.config;

import com.lel.bookmingle.model.User;
import com.lel.bookmingle.service.JwtService;
import com.lel.bookmingle.service.UserService;
import com.lel.bookmingle.utility.context.Context;
import com.lel.bookmingle.utility.context.ContextProvider;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Objects;

@Component
@RequiredArgsConstructor
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ContextManagerFilter implements Filter {

    private final UserService userService;
    private final JwtService jwtService;
    private final ContextProvider contextProvider;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        Logger logger = LogManager.getLogger();
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        Context context = new Context();
        context.setClientIp(request.getRemoteAddr());
        String authorisationHeader = request.getHeader("Authorization");

        User user = null;
        if (Objects.nonNull(authorisationHeader) && authorisationHeader.startsWith("Bearer ")) {
            String jwt = authorisationHeader.substring(7);
            try {
                String username = jwtService.extractUsername(jwt);
                if (Objects.nonNull(username)) {
                    user = userService.findUserByEmail(username);
                    context.setUser(user);
                }
            } catch (Exception e) {
                context.setUser(new User());
            }
        }

        StringBuilder requestInfo = new StringBuilder();
        requestInfo.append("Remote Addr = ").append(request.getRemoteAddr())
                .append(", Request URI = ").append(request.getRequestURI())
                .append(", Authorization = ").append(authorisationHeader);
        if (Objects.nonNull(user)) {
            requestInfo.append(" User Id = ").append(user.getId())
                    .append(", Username = ").append(user.getUsername());
        }
        logger.info(requestInfo.toString());

        try {
            contextProvider.setContext(context);
            filterChain.doFilter(request, response);
        } finally {
            contextProvider.clearContext();
        }
    }
}
