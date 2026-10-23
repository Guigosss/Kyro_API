package com.bulk.kyro.bll.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class RateLimitException extends KyroException {

    private final int remainingTokens;
    private final long retryAfterSeconds;

    public RateLimitException(String message, int remainingTokens, long retryAfterSeconds) {
        super(HttpStatus.TOO_MANY_REQUESTS, message, "Rate limit exceeded");
        this.remainingTokens = remainingTokens;
        this.retryAfterSeconds = retryAfterSeconds;
    }

}