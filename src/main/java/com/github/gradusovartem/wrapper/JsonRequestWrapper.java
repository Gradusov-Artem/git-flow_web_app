package com.github.gradusovartem.wrapper;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;

/**
 * class JsonRequestWrapper возвращает json данные в request
 */
public class JsonRequestWrapper extends HttpServletRequestWrapper {
    private String body;

    /**
     * constructor JsonRequestWrapper
     * @param request
     * @param body
     */
    public JsonRequestWrapper(HttpServletRequest request, String body) {
        super(request);
        this.body = body;
    }

    /**
     * метод getReader позволяет считывать данные из тела запроса
     * @return
     * @throws IOException
     */
    /* @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new StringReader(json.toString()));
    } */

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        // Возвращаем ServletInputStream, используя сохраненное тело запроса
        final byte[] bytes = body.getBytes();
        return new ServletInputStream() {
            private int index = 0;

            @Override
            public int read() throws IOException {
                if (index >= bytes.length) {
                    return -1;
                }
                return bytes[index++];
            }
        };
    }
}

