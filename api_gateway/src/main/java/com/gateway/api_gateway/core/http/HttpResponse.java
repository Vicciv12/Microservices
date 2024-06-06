package com.gateway.api_gateway.core.http;

public abstract class HttpResponse {
    public abstract String getBody();
    public abstract int getStatusCode();
}
