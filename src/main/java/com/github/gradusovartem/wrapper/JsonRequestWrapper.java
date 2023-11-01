package com.github.gradusovartem.wrapper;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringReader;

/**
 * class JsonRequestWrapper возвращает json данные в request
 */
public class JsonRequestWrapper extends HttpServletRequestWrapper {
    private String json;

    /**
     * constructor JsonRequestWrapper
     * @param request
     * @param json
     */
    public JsonRequestWrapper(HttpServletRequest request, String json) {
        super(request);
        this.json = json;
    }

    /**
     * метод getReader позволяет считывать данные из тела запроса
     * @return
     * @throws IOException
     */
    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new StringReader(json.toString()));
    }
}

