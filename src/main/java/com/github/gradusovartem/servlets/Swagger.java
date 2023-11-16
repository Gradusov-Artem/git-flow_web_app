package com.github.gradusovartem.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
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
        response.setContentType("application/x-yaml");
        response.setCharacterEncoding("UTF-8");

        /* Map<String, Object> */ String openAPIFileContent = readFileOpenAPI("src/main/resources/openapi.yaml");

        PrintWriter out = response.getWriter();

        // Выводим openAPIFileContent
        out.println(openAPIFileContent);
    }

    /**
     * method readFileOpenAPI читает содержимое файла openapi
     * @param filePath
     * @return
     */
    private String readFileOpenAPI(String filePath) throws IOException {
        String yaml = new String(Files.readAllBytes(Paths.get(filePath)), StandardCharsets.UTF_8);
        return yaml;
    }
}
