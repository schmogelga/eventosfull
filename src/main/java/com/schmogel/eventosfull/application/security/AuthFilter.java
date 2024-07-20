//package com.schmogel.eventosfull.application.security;
//
//import java.io.IOException;
//import java.time.LocalDateTime;
//import java.util.Optional;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.stereotype.Component;
//
//import com.schmogel.eventosfull.infrastructure.logs.LogInfo;
//import com.schmogel.eventosfull.infrastructure.logs.Logger;
//import jakarta.servlet.*;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.RequiredArgsConstructor;
//
//@Component
//@RequiredArgsConstructor
//public class AuthFilter implements Filter {
//
//    private final AuthManager authManager;
//    private final Logger logger;
//
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
//            throws IOException, ServletException {
//
//        HttpServletRequest servletRequest = (HttpServletRequest) request;
//        HttpServletResponse servletResponse = (HttpServletResponse) response;
//
//        String requestURI = servletRequest.getRequestURI();
//
//        if (requestURI.startsWith("/swagger-ui.html")
//                || requestURI.startsWith("/swagger-resources")
//                || requestURI.startsWith("/v3/api-docs")
//                || requestURI.startsWith("/swagger-ui/")) {
//            chain.doFilter(request, response);
//            return;
//        }
//
//        if (!authManager.validarRequisicao(
//                servletRequest.getHeader("authorization"), servletRequest.getHeader("client"))) {
//            servletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
//            servletResponse
//                    .getWriter()
//                    .write("Tentativa de acesso por cliente nao autorizado (API para API)");
//            return;
//        }
//        ;
//
//        chain.doFilter(request, response);
//
//        logger.saveLog(
//                new LogInfo(
//                        null,
//                        requestURI + "?" + Optional.ofNullable(servletRequest.getQueryString()).orElse(""),
//                        servletRequest.getMethod(),
//                        servletRequest.getHeader("username"),
//                        servletResponse.getStatus(),
//                        LocalDateTime.now()));
//    }
//}
