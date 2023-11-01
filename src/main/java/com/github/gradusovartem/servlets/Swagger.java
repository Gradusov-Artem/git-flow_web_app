package com.github.gradusovartem.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.yaml.snakeyaml.Yaml;

/**
 * class Swagger привязан к url /openapi, возвращает yaml файл
 */
public class Swagger extends HttpServlet {

    /**
     * method doGet возвращает yaml файл
     * @param request
     * @param response
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Установка типа содержимого как JSON
        response.setContentType("application/json");

        // Чтение файла OpenAPI (замените "path/to/openapi.json" на путь к вашему файлу)
        Map<String, Object> openAPIFileContent = readFileOpenAPI("src/main/resources/openapi.yaml");

        PrintWriter out = response.getWriter();
        ObjectMapper objectMapper = new ObjectMapper();

        // Используем ObjectMapper для преобразования Map в JSON
        String json = objectMapper.writeValueAsString(openAPIFileContent);

        // Выводим JSON в ответ
        out.println(json);
        // response.getWriter().write(openAPIFileContent);
    }

    /**
     * method readFileOpenAPI читает содержимое файла openapi
     * @param filePath
     * @return
     */
    private Map<String, Object> readFileOpenAPI(String filePath) {
        Yaml yaml = new Yaml();
        Map<String, Object> data = null;

        try (InputStream input = new FileInputStream(filePath)) {
            // Читаем YAML и преобразуем его в Map
            data = yaml.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }
}
