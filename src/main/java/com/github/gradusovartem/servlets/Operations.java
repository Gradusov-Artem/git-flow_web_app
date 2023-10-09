package com.github.gradusovartem.servlets;

import com.github.gradusovartem.entities.Operation;
import com.github.gradusovartem.listeners.OperationListener;
import com.github.gradusovartem.model.Model;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

/**
 * class Operations привязан к url: /operations
 */
public class Operations extends HttpServlet {

    /**
     * method doGet() вызывается при обращении к url: /operations
     * @param request - запрос передаваемый в метод
     * @param response - ответ отправляемый из метода
     * throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter pw = response.getWriter();
        pw.println("Hello World!");
    }

    /**
     * method doPost() - вызывается при обращении к url: /operations и возвращает созданный объект
     * @param request - запрос передаваемый в метод
     * @param response - ответ отправляемый из метода
     * throws IOException
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try
        {
            String jsonString = (String) request.getAttribute("json");

            ObjectMapper objectMapper = new ObjectMapper();
            Operation data = objectMapper.readValue(jsonString, Operation.class);

            int id = data.getId();
            String comment = data.getComment();
            int oper_1 = data.getOper_1();
            int oper_2 = data.getOper_2();
            String operationS = data.getOperation();
            int result = calculation(oper_1, oper_2, operationS);

            // создание operation
            Operation operation = new Operation(id, comment, oper_1, oper_2, operationS, result);

            // создание места
            Model model = Model.getInstance();
            model.add(id, comment, oper_1, oper_2, operation.dt_operation, operationS, result);

            // формат ответа
            response.setContentType("json/text");
            response.setCharacterEncoding("UTF-8");

            PrintWriter pw = response.getWriter();

            // to json
            objectMapper = new ObjectMapper();

            String jsonText = objectMapper.writeValueAsString(operation);

            pw.println(jsonText);
        }
        catch(Exception e) {
            response.getWriter().write("Error" + e.getMessage());
        }
    }

    /**
     * method calculation() - метод, принимающий два операнда и операцию и возвращающий ответ
     * @param oper_1 - первый операнд
     * @param oper_2 - второй операнд
     * @param operation - операция
     * @return - возвращает результат
     */
    public static int calculation(int oper_1, int oper_2, String operation) {
        int result = 0;

        if (operation.equals("+")) {
            result = oper_1 + oper_2;
        }

        if (operation.equals("-")) {
            result = oper_1 - oper_2;
        }

        if (operation.equals("*")) {
            result = oper_1 * oper_2;
        }

        if (operation.equals("/")) {
            result = oper_1 / oper_2;
        }

        return result;
    }
}
