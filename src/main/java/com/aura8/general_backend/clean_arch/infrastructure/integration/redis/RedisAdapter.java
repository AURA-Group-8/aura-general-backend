package com.aura8.general_backend.clean_arch.infrastructure.integration.redis;

import com.aura8.general_backend.clean_arch.core.gateway.CacheGateway;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

@Repository
public class RedisAdapter implements CacheGateway {
    private final StringRedisTemplate redis;
    private final ValueOperations<String, String> ops;

    public RedisAdapter(StringRedisTemplate redis) {
        this.redis = redis;
        this.ops = redis.opsForValue();
    }

    @Override
    public String get(String key) {
        try{
            return ops.get(key);
        } catch (Exception e){
            System.out.printf("Error getting key %s from Redis: %s%n", key, e.getMessage());
            return null;
        }
    }

    @Override
    public void set(String key, String value, long expirationInSeconds) {
        try {
            ops.set(key, value);
            redis.expire(key, java.time.Duration.ofSeconds(expirationInSeconds));
        }catch (Exception e){
            System.out.printf("Error setting key %s in Redis: %s%n", key, e.getMessage());
        }
    }

    @Override
    public void set(String key, String value) {
        try {
            ops.set(key, value);
        } catch (Exception e) {
            System.out.printf("Error setting key %s in Redis: %s%n", key, e.getMessage());
        }
    }

    @Override
    public void delete(String key) {
        try {
            redis.delete(key);
        } catch (Exception e){
            System.out.printf("Error deleting key %s from Redis: %s%n", key, e.getMessage());
        }
    }

    public ValueOperations<String, String> ops() {
        try {
            return redis.opsForValue();
        } catch (Exception e) {
            System.out.printf("Error accessing ValueOperations from Redis: %s%n", e.getMessage());
            return null;
        }
    }
}
