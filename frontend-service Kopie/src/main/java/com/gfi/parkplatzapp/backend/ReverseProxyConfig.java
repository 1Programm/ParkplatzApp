package com.gfi.parkplatzapp.backend;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.HttpContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;

@Component
@Slf4j
public class ReverseProxyConfig implements Filter {

    private CloseableHttpClient httpClient;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Initialisierung des HTTP-Clients
        httpClient = HttpClients.createDefault();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        // Definieren Sie den Host und den Port des Angular-Frontends
        HttpHost targetHost = new HttpHost("localhost", 4200, "http");

        // Erstellen Sie eine URI-Anforderung unter Verwendung der Anfrage-URI
        String requestUri = request.getRequestURI();
        String targetUri = targetHost.toURI() + requestUri;
        HttpUriRequest proxyRequest = RequestBuilder.create(request.getMethod())
                .setUri(targetUri)
                .build();

        // Kopieren Sie die Header der ursprünglichen Anfrage
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            String headerValue = request.getHeader(headerName);
            proxyRequest.setHeader(headerName, headerValue);
        }

        // Führen Sie die Proxy-Anfrage aus
        HttpContext httpContext = null;
        try {
            HttpResponse proxyResponse = httpClient.execute(targetHost, proxyRequest, httpContext);

            if(AppConfig.LOG_PROXY_REQUESTS) log.info("Proxying path: [{}] --> [{} - {}]", request.getRequestURI(), proxyResponse.getStatusLine().getStatusCode(), proxyResponse.getStatusLine().getReasonPhrase());

            // Kopieren Sie den Statuscode und die Header der Proxy-Antwort
            response.setStatus(proxyResponse.getStatusLine().getStatusCode());
            for (Header header : proxyResponse.getAllHeaders()) {
                response.setHeader(header.getName(), header.getValue());
            }

            // Kopieren Sie den Inhalt der Proxy-Antwort
            HttpEntity entity = proxyResponse.getEntity();
            if (entity != null) {
                InputStream inputStream = entity.getContent();
                OutputStream outputStream = response.getOutputStream();
                StreamUtils.copy(inputStream, outputStream);
            }
        } catch (Exception e) {
            // Behandeln Sie Fehler während des Proxy-Vorgangs
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Proxy Error: " + e.getMessage());
        }
    }

}
