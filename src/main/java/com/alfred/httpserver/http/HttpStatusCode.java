package com.alfred.httpserver.http;

public enum HttpStatusCode {
    /* --- CLIENT ERRORS --- */
    CLIENT_ERROR_400_BAD_REQUEST(400, "Bad Request"),
    CLIENT_ERROR_401_METHOD_NOT_ALLOWED(401, "Method not Allowed"),
    CLIENT_ERROR_414_BAD_URI(414, "URI Very Long"),

    /* --- SERVER ERRORS --- */
    SERVER_ERROR_500_INTERNAL_SERVER_ERROR(500, "Internal Server Error"),
    SERVER_ERROR_501_NOT_IMPLEMENTED(501, "No se puede");



    public final int STATUS_CODE;
    public final String Message;

    HttpStatusCode(int STATUS_CODE, String message) {
        this.STATUS_CODE = STATUS_CODE;
        Message = message;
    }


}
