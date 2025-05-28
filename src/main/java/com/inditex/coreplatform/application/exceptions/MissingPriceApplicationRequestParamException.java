package com.inditex.coreplatform.application.exceptions;

public class MissingPriceApplicationRequestParamException extends RuntimeException {
    public MissingPriceApplicationRequestParamException(String paramName) {
        super("Missing required request parameter: " + paramName);
    }
}
