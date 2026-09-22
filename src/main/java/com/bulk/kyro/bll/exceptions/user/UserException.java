package com.bulk.kyro.bll.exceptions.user;

import com.bulk.kyro.bll.exceptions.KyroException;
import com.bulk.kyro.dl.enums.Section;
import org.springframework.http.HttpStatus;

public abstract class UserException extends KyroException {

    public UserException(HttpStatus status, Object body) {
        super(status, body, Section.USER.name());
    }
}
