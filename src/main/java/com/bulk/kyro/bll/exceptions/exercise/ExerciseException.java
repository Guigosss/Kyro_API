package com.bulk.kyro.bll.exceptions.exercise;

import com.bulk.kyro.bll.exceptions.KyroException;
import com.bulk.kyro.dl.enums.Section;
import org.springframework.http.HttpStatus;

public abstract class ExerciseException extends KyroException {

    public ExerciseException(HttpStatus status, Object body) {
        super(status, body, Section.EXERCISE.name());
    }

    @Override
    public String toString() {
        return getBody().toString();
    }
}
