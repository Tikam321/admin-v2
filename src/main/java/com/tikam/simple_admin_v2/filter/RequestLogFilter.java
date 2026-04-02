package com.tikam.simple_admin_v2.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.apache.http.util.TextUtils.isEmpty;
import static org.hibernate.internal.util.collections.CollectionHelper.isNotEmpty;

@Slf4j
public class RequestLogFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String uuid = UUID.randomUUID().toString().substring(0,8);
        MDC.put("uuid", uuid);
        HttpServletRequest  httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;

        try {
            log.info("REQUEST [{}  {}]",httpRequest.getMethod(),httpRequest.getRequestURI());

            logRequestHeader(httpRequest);
            logRequestQuery(httpRequest);

            long startTime = System.currentTimeMillis();
            String contentType = httpRequest.getContentType();
            boolean isBodyLoggingContentType = Objects.equals(contentType, MediaType.APPLICATION_JSON_VALUE) || MediaType.TEXT_PLAIN.equals(contentType) ||
                    MediaType.APPLICATION_FORM_URLENCODED_VALUE.equals(contentType);
            if (isBodyLoggingContentType) {
                CustomHttpRequestWrapper requestWrapper = new CustomHttpRequestWrapper(httpRequest);
//                httpRequest = requestWrapper;
                logPayload(requestWrapper);
                filterChain.doFilter(requestWrapper, httpResponse);
            } else {
                filterChain.doFilter(httpRequest,httpResponse);
            }
            long elapsedTime = System.currentTimeMillis() - startTime;
            logResponse(httpRequest,httpResponse,elapsedTime);
        } finally {
            MDC.remove("uuid");
        }
    }

    private void logResponse(HttpServletRequest httpRequest, HttpServletResponse httpResponse, long elapsedTime) {
        log.info("RESPONSE : [{}, {}] - status: {} - Elapsed time: {}ms",httpRequest.getMethod(), httpRequest.getRequestURI()
                ,httpResponse.getStatus(), elapsedTime);
    }

    private void logRequestQuery(HttpServletRequest httpRequest) {
         logNotBlankString("query: {}", httpRequest.getQueryString());
    }

    private void logRequestHeader(HttpServletRequest httpRequest) {
        Map<String, String> headerMap = Collections.list(httpRequest.getHeaderNames()).stream()
                .collect(Collectors.toMap(headerName -> headerName, httpRequest::getHeader));
        if (isNotEmpty(headerMap)) {
            logNotBlankString("header: {}", headerMap.toString());
        }
    }

    private void logPayload(HttpServletRequest httpRequest) throws IOException {
        String payload = httpRequest.getInputStream().toString();
        logNotBlankString("payload {}",payload);
    }

    private void logNotBlankString(String format, String payload) {
        if (!isEmpty(payload)) {
            log.info(format, payload);
        }
    }


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }



    @Override
    public void destroy() {
        Filter.super.destroy();
        MDC.remove("uuid");
    }
}
