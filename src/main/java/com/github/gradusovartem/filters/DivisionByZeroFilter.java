package com.github.gradusovartem.filters;

import javax.servlet.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * class DivisionByZeroFilter - фильтр, который проверяет деление на 0
 */
public class DivisionByZeroFilter implements Filter {
    public void init(FilterConfig config) throws ServletException {

    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        try {
            int oper_2 = Integer.parseInt(request.getParameter("oper_2"));
            String operation = request.getParameter("operation");

            if (oper_2 == 0 && operation.equals("/")) {
                response.getWriter().write("Division by zero.");
                HttpServletResponse resp = (HttpServletResponse) response;
                resp.setStatus(400);
            }
            else {
                chain.doFilter(request, response);
            }
        } catch (NumberFormatException e) {
            response.getWriter().write("Incorrect values.");
        }
    }

    public void destroy() {

    }
}
