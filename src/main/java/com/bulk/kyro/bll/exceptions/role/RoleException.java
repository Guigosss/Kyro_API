package com.bulk.kyro.bll.exceptions.role;

import com.bulk.kyro.bll.exceptions.KyroException;
import com.bulk.kyro.dl.enums.Section;
import org.springframework.http.HttpStatus;

public abstract class RoleException extends KyroException {

    public RoleException(HttpStatus status, Object body) {
        super(status, body, Section.ROLE.name());
    }
}
