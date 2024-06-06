package com.gateway.api_gateway.core.http;

import java.util.Map;

public interface HttpServices {
    HttpResponse sendGet(String url, Map<String, String> headers) throws Exception;
    HttpResponse sendGet(String url) throws Exception;
    HttpResponse sendPost(String url, String jsonBody, Map<String, String> headers) throws Exception;
    HttpResponse sendPost(String url, String jsonBody) throws Exception;
    HttpResponse sendPut(String url, String jsonBody, Map<String, String> headers) throws Exception;
    HttpResponse sendPut(String url, String jsonBody) throws Exception;
}
