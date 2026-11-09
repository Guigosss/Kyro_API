package com.bulk.kyro.bll.exceptions.workout;

import com.bulk.kyro.bll.exceptions.KyroException;
import com.bulk.kyro.dl.enums.Section;
import org.springframework.http.HttpStatus;

public abstract class WorkoutException extends KyroException {

    public WorkoutException(HttpStatus status, Object body) {
        super(status, body, Section.WORKOUT.name());
    }

    @Override
    public String toString() {
        return getBody().toString();
    }
}