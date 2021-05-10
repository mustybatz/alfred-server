package com.alfred.httpserver.http;

public class HttpRequest extends HttpMessage{

    private HttpMethod method;
    private String requestTarget;

    public HttpRequest() {
    }


    public HttpMethod getMethod() {
        return method;
    }

    void setMethod(String methodName) throws HttpParsingException {

        for( HttpMethod method : HttpMethod.values() ) {
            if(methodName.equals(method.name())) {
                this.method = method;
                return;
            }
        }

        throw new HttpParsingException(
                HttpStatusCode.SERVER_ERROR_501_NOT_IMPLEMENTED
        );


    }

    public void setRequestTarget(String requestTarget) {
        this.requestTarget = requestTarget;
    }

    public String getRequestTarget() {
        return requestTarget;
    }

    public String getHttpVer() {
        return "HTTP-1.1";
    }
}
