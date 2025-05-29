package com.inditex.coreplatform.infrastructure.rest.controllers.dtos;

import java.time.LocalDateTime;


public record ErrorPriceResponse(
    int status,
    String error,
    String message,
    String path,
    LocalDateTime timestamp
) {}
