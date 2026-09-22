package com.bulk.kyro.bll.exceptions;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@EqualsAndHashCode(callSuper = false) @ToString
public abstract class KyroException extends RuntimeException {

    @Getter
    private String section;

    @Getter
    private final HttpStatus status;

    @Getter
    private final Object body;

    public KyroException(HttpStatus status, Object body, String section) {
        super();
        this.status = status;
        this.body = body;
        this.section = section;
    }
}
