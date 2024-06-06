package com.authorization.authorization_service.core;

public interface AuthService {
    String authOk(String user, String password) throws Exception;
}
