package com.tikam.simple_admin_v2.filter;

//import io.micrometer.core.instrument.util.IOUtils;
import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import lombok.Getter;
import org.apache.commons.io.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;

@Getter
public class CustomHttpRequestWrapper extends HttpServletRequestWrapper {
    private final String bodyInStringFormat;
    private final Map<String,String[]> cachedParameterMap;

    public CustomHttpRequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        cachedParameterMap = request.getParameterMap();
        bodyInStringFormat = readInputStreamInStringFormat(request.getInputStream(),
                Charset.forName(request.getCharacterEncoding()));
    }

    private String readInputStreamInStringFormat(ServletInputStream inputStream, Charset charset) throws IOException {
        return IOUtils.toString(inputStream, charset);
    }

    public  ServletInputStream getInputStream() {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bodyInStringFormat.getBytes());
        return new ServletInputStream() {
            private boolean isFinished = false;




            @Override
            public boolean isFinished() {
                return isFinished;
            }

            @Override
            public boolean isReady() {
                return true;
            }

            @Override
            public void setReadListener(ReadListener readListener) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public int read() throws IOException {
                int data = byteArrayInputStream.read();
                if (data != -1) {
                    isFinished = true;
                }
                return data;
            }
        };
    }

    public Map<String,String[]> getParameterMap() {
        return this.cachedParameterMap;
    }




}
