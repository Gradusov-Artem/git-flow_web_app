package com.github.gradusovartem.servlets;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.gradusovartem.listeners.OperationListener;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import org.json.JSONObject;


/**
 * class Statistics - это сервлет, который отвечает за вывод статистики обращений к веб-приложению
 */
public class Statistics extends HttpServlet {

    /**
     * method doGet - вызывается при обращении к url: /statistics
     * @param request
     * @param response
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int requestCount = OperationListener.getRequestCount();

        response.setContentType("application/json");
        // response.setCharacterEncoding("UTF-8");

        String str = "{ \"total_count\": " + requestCount + " }";

        PrintWriter pw = response.getWriter();
        pw.println(str);
    }
}
