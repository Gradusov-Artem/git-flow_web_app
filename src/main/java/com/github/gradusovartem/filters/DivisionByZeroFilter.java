package com.github.gradusovartem.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.gradusovartem.entities.Operation;
import com.google.gson.Gson;

import javax.servlet.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

/**
 * class DivisionByZeroFilter - фильтр, который проверяет деление на 0
 */
public class DivisionByZeroFilter implements Filter {

    /**
     * method init - вызывается при вызове фильтра
     * @param config
     * @throws ServletException
     */
    public void init(FilterConfig config) throws ServletException {

    }

    /**
     * method doFilter вызывается при попадании запроса в фильтр
     * @param request
     * @param response
     * @param chain
     * @throws IOException
     * @throws ServletException
     */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String httpMethod = httpRequest.getMethod();

        if ("POST".equals(httpMethod)) {
            try {
                BufferedReader reader = httpRequest.getReader();
                StringBuilder jsonBuilder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    jsonBuilder.append(line);
                }

                String jsonString = jsonBuilder.toString();
                request.setAttribute("json", jsonString);
                // Преобразование JSON строки в объект Java
                ObjectMapper objectMapper = new ObjectMapper();
                Operation data = objectMapper.readValue(jsonString, Operation.class);

                int oper_2 = data.getOper_2();
                String operation = data.getOperation();

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

        else {
            chain.doFilter(request, response);
        }
    }

    /**
     * method destroy вызывается при завершении работы фильтра
     */
    public void destroy() {

    }
}
