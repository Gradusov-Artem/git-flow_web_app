package com.github.gradusovartem.servlets;

import com.github.gradusovartem.entities.Operation;
import com.github.gradusovartem.model.Model;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * class Operations привязан к url: /operations
 */
public class Operations extends HttpServlet {

    Map<Integer, Operation> modelOper = Model.getInstance().getModelOper();
    private int index = 0;
    /**
     * method doGet() вызывается при обращении к url: /operations
     * @param request - запрос передаваемый в метод
     * @param response - ответ отправляемый из метода
     * throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String id = request.getParameter("id");
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        ObjectMapper objectMapper = new ObjectMapper();

        if (id == null) {

            // Используем ObjectMapper для преобразования Map в JSON
            String json = objectMapper.writeValueAsString(modelOper);

            // Выводим JSON в ответ
            out.println(json);
        }
        else {
            Operation operationById = modelOper.get(Integer.parseInt(id));

            if (operationById != null) {
                // Используем ObjectMapper для преобразования operationById в JSON
                String json = objectMapper.writeValueAsString(operationById);

                // Выводим JSON в ответ
                out.println(json);
            }
            else {
                out.println("There is no such element");
            }
        }
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
            BufferedReader reader = request.getReader();
            StringBuilder jsonBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonBuilder.append(line);
            }

            String jsonString = jsonBuilder.toString();

            ObjectMapper objectMapper = new ObjectMapper();
            Operation data = objectMapper.readValue(jsonString, Operation.class);

            index += 1;
            int id = index;
            String comment = data.getComment();
            int oper_1 = data.getOper_1();
            int oper_2 = data.getOper_2();
            String operationS = data.getOperation();
            int result = calculation(oper_1, oper_2, operationS);

            // создание operation
            Operation operation = new Operation(id, comment, oper_1, oper_2, operationS, result);

            // создание места
            Model model = Model.getInstance();
            model.add(id, operation);

            // формат ответа
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            PrintWriter pw = response.getWriter();

            String jsonText = objectMapper.writeValueAsString(operation);

            pw.println(jsonText);
        }
        catch(Exception e) {
            response.getWriter().write("Error" + e.getMessage());
        }
    }

    /**
     * method doPut - вызывается при обращении к url /operations и обновляет комментарий к операции
     * @param request
     * @param response
     * @throws IOException
     */
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String id = request.getParameter("id");
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        ObjectMapper objectMapper = new ObjectMapper();

        if (id != null) {
            BufferedReader reader = request.getReader();
            StringBuilder jsonBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonBuilder.append(line);
            }
            String jsonString = jsonBuilder.toString();

            Operation data = objectMapper.readValue(jsonString, Operation.class);
            String comment = data.getComment();

            Operation operation = modelOper.get(Integer.parseInt(id));
            operation.setComment(comment);

            String jsonText = objectMapper.writeValueAsString(operation);

            out.println(jsonText);
        }
    }

    /**
     * method doDelete - вызывается при обращении к url /operations и удаляет операцию
     * @param request
     * @param response
     * @throws IOException
     */
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String id = request.getParameter("id");
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        ObjectMapper objectMapper = new ObjectMapper();

        if (id != null) {
            Operation operationById = modelOper.remove(Integer.parseInt(id));

            // Используем ObjectMapper для преобразования operationById в JSON
            String json = objectMapper.writeValueAsString(operationById);

            // Выводим JSON в ответ
            out.println(json);
        }
        else {
            out.println("You didn't choose the number of operation to delete.");
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
