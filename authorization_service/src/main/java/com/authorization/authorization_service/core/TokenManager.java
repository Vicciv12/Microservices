package com.authorization.authorization_service.core;

import com.auth0.jwt.algorithms.Algorithm;

public interface TokenManager<T> {
    String generateToken(T consumer);
    String generateToken(T consumer, Algorithm algorithm);
}
