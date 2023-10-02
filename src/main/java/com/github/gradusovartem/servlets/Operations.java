package com.github.gradusovartem.servlets;

import com.github.gradusovartem.entities.Operation;
import com.github.gradusovartem.model.Model;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import com.fasterxml.jackson.databind.ObjectMapper;

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

        // ввод данных
        int id = Integer.parseInt(request.getParameter("id"));
        String comment = request.getParameter("comment");
        int oper_1 = Integer.parseInt(request.getParameter("oper_1"));
        int oper_2 = Integer.parseInt(request.getParameter("oper_2"));
        String operationS = request.getParameter("operation");
        int result = calculation(oper_1, oper_2, operationS);

        // создание operation
        Operation operation = new Operation(id, comment, oper_1, oper_2, operationS, result);

        // создание места
        Model model = Model.getInstance();
        model.add(id, comment, oper_1, oper_2, operation.dt_operation, operationS, result);

        // формат ответа
        response.setContentType("json/text");
        response.setCharacterEncoding("UTF-8");

        // инициализация printwriter'а
        PrintWriter pw = response.getWriter();

        // to json
        ObjectMapper objectMapper = new ObjectMapper();

        String jsonText = objectMapper.writeValueAsString(operation);

        pw.println(jsonText);
        /* pw.println(id);
        pw.println(comment);
        pw.println(oper_1);
        pw.println(oper_2);
        pw.println(operationS);
        pw.println(result); */
    }

    /**
     * method calculation() - метод, принимающий два операнда и операцию и возвращающий ответ
     * @param oper_1 - первый операнд
     * @param oper_2 - второй операнд
     * @param operation - операция
     * @return - возвращает результат
     */
    private int calculation(int oper_1, int oper_2, String operation) {
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
