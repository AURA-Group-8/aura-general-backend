package com.aura8.general_backend.clean_arch.core.gateway;

public interface CacheGateway {
    String get(String key);
    void set(String key, String value, long expirationInSeconds);
    void set(String key, String value);
    void delete(String key);
}
