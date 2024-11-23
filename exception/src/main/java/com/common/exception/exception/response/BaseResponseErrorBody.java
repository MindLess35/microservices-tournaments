package com.common.exception.exception.response;

import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.time.Instant;

@Getter
@SuperBuilder
public abstract class BaseResponseErrorBody {

    @Builder.Default
    private final Instant timestamp = Instant.now();

    private final int status;

    private final String cause;

    private final String exception;

    private final String path;

}
