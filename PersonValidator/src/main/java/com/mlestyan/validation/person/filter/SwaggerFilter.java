package com.mlestyan.validation.person.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class SwaggerFilter implements Filter {
	private static final String SWAGGER_DOCUMENTATION_URL = "/swagger-ui.html";

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        
		if (request.getRequestURI().equals("") || request.getRequestURI().equals("/")) {
			response.sendRedirect(SWAGGER_DOCUMENTATION_URL);
			return;
		}
		chain.doFilter(servletRequest, servletResponse);
	}

}
