package com.github.gradusovartem.servlets;

import com.github.gradusovartem.entities.Operation;
import com.github.gradusovartem.model.Model;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Map;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * class Operations привязан к url: /operations
 */
public class Operations extends HttpServlet {

    private static int id = 0;
    private Model model = Model.getInstance();
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

        if (id == null) {

            ObjectMapper objectMapper = new ObjectMapper();

            // Используем ObjectMapper для преобразования Map в JSON
            String json = objectMapper.writeValueAsString(model.getValues());

            // Выводим JSON в ответ
            response.setStatus(200);
            out.println(json);
        }
        else {
            ObjectMapper objectMapper = new ObjectMapper();
            Operation operationById = model.getOperationById(Integer.parseInt(id));

            if (operationById != null) {
                // Используем ObjectMapper для преобразования operationById в JSON
                String json = objectMapper.writeValueAsString(operationById);

                // Выводим JSON в ответ
                response.setStatus(200);
                out.println(json);
            }
            else {
                response.setStatus(204);
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
            // HttpServletRequestWrapper wrappedRequest = new HttpServletRequestWrapper((HttpServletRequest) request);

            // Считываем данные из тела запроса
            BufferedReader reader = request.getReader();
            StringBuilder jsonBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonBuilder.append(line);
            }

            // Преобразуем json в String
            String jsonString = jsonBuilder.toString(); // request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));

            // Разделяем содержимое
            ObjectMapper objectMapper = new ObjectMapper();
            Operation data = objectMapper.readValue(jsonString, Operation.class);

            // Создаем содержимое operation
            id = getId();
            int result = calculation(data);
            data.setId(id);
            data.setResult(result);

            // Создание operation
            Operation operation = new Operation(data);

            // Создание места
            Model model = Model.getInstance();
            model.add(operation);

            // Формат ответа
            response.setContentType("application/json");

            PrintWriter pw = response.getWriter();

            String jsonText = objectMapper.writeValueAsString(operation);

            response.setStatus(200);
            pw.println(jsonText);
        }
        catch(Exception e) {
            response.getWriter().write("Error" + e.getMessage());
            response.setStatus(500);
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

        if (id != null) {
            if (model.getOperationById(Integer.parseInt(id)) != null) {
                response.setContentType("application/json");
                PrintWriter out = response.getWriter();
                ObjectMapper objectMapper = new ObjectMapper();

                BufferedReader reader = request.getReader();
                StringBuilder jsonBuilder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    jsonBuilder.append(line);
                }
                String jsonString = jsonBuilder.toString();

                Operation data = objectMapper.readValue(jsonString, Operation.class);
                String comment = data.getComment();

                Operation operation = model.getOperationById(Integer.parseInt(id));
                operation.setComment(comment);

                String jsonText = objectMapper.writeValueAsString(operation);

                response.setStatus(200);
                out.println(jsonText);
            }

            else {
                response.setStatus(204);
            }
        }
        else {
            response.setStatus(204);
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

        if (id != null) {
            if (model.getOperationById(Integer.parseInt(id)) != null) {
                response.setContentType("application/json");
                PrintWriter out = response.getWriter();
                ObjectMapper objectMapper = new ObjectMapper();

                Operation operationById = model.removeById(Integer.parseInt(id));

                // Используем ObjectMapper для преобразования operationById в JSON
                String json = objectMapper.writeValueAsString(operationById);

                // Выводим JSON в ответ
                response.setStatus(200);
                out.println(json);
            }
            else {
                response.setStatus(204);
            }
        }
        else {
            response.setStatus(204);
        }

    }

    /**
     * method calculation() - метод, принимающий два операнда и операцию и возвращающий ответ
     * @param oper_1 - первый операнд
     * @param oper_2 - второй операнд
     * @param operation - операция
     * @return - возвращает результат
     */
    private static int calculation(Operation data) {
        int result = 0;

        if (data.getOperation().equals("+")) {
            result = data.getOper_1() + data.getOper_2();
        }

        if (data.getOperation().equals("-")) {
            result = data.getOper_1() - data.getOper_2();
        }

        if (data.getOperation().equals("*")) {
            result = data.getOper_1() * data.getOper_2();
        }

        if (data.getOperation().equals("/")) {
            result = data.getOper_1() / data.getOper_2();
        }

        return result;
    }

    private int getId() {
        return id + 1;
    }
}
