package com.gateway.api_gateway.core.http;

import java.util.Map;

public interface HttpServicesDelete {
    HttpResponse sendDelete(String url, Map<String, String> headers) throws Exception;
    HttpResponse sendDelete(String url) throws Exception;
}
