package com.bulk.kyro.bll.exceptions.user;

import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

public class UserAlreadyExistsException extends UserException {

    public UserAlreadyExistsException(HttpStatus status, Object body) {
        super(HttpStatus.CONFLICT, new HashMap<String, String>(
                Map.of("username", "Username already exists")
        ));
    }

    public UserAlreadyExistsException(Map<String, String> body) {
        super(HttpStatus.CONFLICT, body);
    }

    @Override
    public String toString(){
        return getBody().toString();
    }
}
