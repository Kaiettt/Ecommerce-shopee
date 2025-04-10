package com.ak.ecommerce_vender.config;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.ak.ecommerce_vender.domain.responce.RestResponce;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler{
private final ObjectMapper mapper;
    
    public CustomAccessDeniedHandler(ObjectMapper mapper) {
        this.mapper = mapper;
    }
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
            AccessDeniedException accessDeniedException) throws IOException, ServletException {
      response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType("application/json;charset=UTF-8");
        
        RestResponce<Object> res = new RestResponce<Object>();
        res.setStatusCode(HttpStatus.FORBIDDEN.value());
        res.setError(accessDeniedException.getMessage());
        res.setMessage("Bạn không có quyền truy cập tài nguyên này");
        
        mapper.writeValue(response.getWriter(), res);
    }
    
}
