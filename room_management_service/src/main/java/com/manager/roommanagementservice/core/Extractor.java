package com.manager.roommanagementservice.core;

import jakarta.servlet.http.HttpServletRequest;

public interface Extractor<T> {
    String extractToken(HttpServletRequest request);
    T extractDataByToken(HttpServletRequest request);
    T extractDataByToken(String token);
}
