package com.bulk.kyro.bll.services;

import com.bulk.kyro.bll.exceptions.RateLimitException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RateLimitService {

    private final StringRedisTemplate redisTemplate;

    public void checkRateLimit(String identifier, String endpoint, int maxRequests, int windowSeconds) {
        String key = generateKey(identifier, endpoint);
        BucketState state = getBucketState(key, maxRequests, windowSeconds);

        if (state.tokens() <= 0) {
            long now = System.currentTimeMillis();
            long retryAfter = Math.max(1, (windowSeconds * 1000L - (now - state.lastRefillTime())) / 1000L);
            throw new RateLimitException("Rate limit exceeded for " + identifier + " on " + endpoint, 0, retryAfter);
        }

        //- Consume one token
        saveBucketState(key, state.tokens() - 1, state.lastRefillTime(), windowSeconds);
    }

    public int getRemainingTokens(String identifier, String endpoint, int maxRequests, int windowSeconds) {
        String key = generateKey(identifier, endpoint);
        BucketState state = getBucketState(key, maxRequests, windowSeconds);

        return Math.max(0, state.tokens());
    }

    private BucketState getBucketState(String key, int maxRequests, int windowSeconds) {
        long now = System.currentTimeMillis();
        String tokenData = redisTemplate.opsForValue().get(key);

        int currentTokens = maxRequests;
        long lastRefillTime = now;

        if (tokenData != null) {
            String[] parts = tokenData.split(":");
            currentTokens = Integer.parseInt(parts[0]);
            lastRefillTime = Long.parseLong(parts[1]);
        }

        //- Refill tokens based on time elapsed
        long timeSinceLastRefill = now - lastRefillTime;
        long tokensToAdd = (timeSinceLastRefill / (windowSeconds * 1000L)) * maxRequests;

        if (tokensToAdd > 0) {
            currentTokens = Math.min(maxRequests, (int) (currentTokens + tokensToAdd));
            lastRefillTime = now;
        }

        return new BucketState(currentTokens, lastRefillTime);
    }

    private void saveBucketState(String key, int tokens, long lastRefillTime, int windowSeconds) {
        //- Store updated state
        String newTokenData = tokens + ":" + lastRefillTime;
        redisTemplate.opsForValue().set(key, newTokenData);
        redisTemplate.expire(key, java.time.Duration.ofSeconds(windowSeconds));
    }

    private String generateKey(String identifier, String endpoint) {
        return "rate_limit:" + identifier + ":" + endpoint;
    }

    private record BucketState(int tokens, long lastRefillTime) {
    }
}
