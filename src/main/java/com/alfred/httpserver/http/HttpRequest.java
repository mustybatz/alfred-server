package com.alfred.httpserver.http;

public class HttpRequest extends HttpMessage{

    private HttpMethod method;
    private String requestTarget;
    private String httpVer;

    public HttpRequest() {
    }

    public String getHttpVer() {
        return httpVer;
    }

    public void setHttpVer(String httpVer) {
        this.httpVer = httpVer;
    }

    public HttpMethod getMethod() {
        return method;
    }

    void setMethod(HttpMethod method) {
        this.method = method;
    }

    public String getRequestTarget() {
        return requestTarget;
    }

    public void setRequestTarget(String requestTarget) {
        this.requestTarget = requestTarget;
    }
}
