package com.manager.roommanagementservice.core;

public interface TokenValidator {
    void valid(String token) throws Exception;
}
