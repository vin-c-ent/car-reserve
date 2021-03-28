package com.car.exception;

/**
 * @author Vincent
 * @date 2021/03/27
 */
public enum ModelExceptionInfoEnums implements IExceptionInfo {
    MODEL_EMPTY("201", "no model found for requested brand");

    private String errorCode;
    private String errorMessage;

    private ModelExceptionInfoEnums(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    @Override
    public String getErrorCode() {
        return this.errorCode;
    }

    @Override
    public String getErrorMessage() {
        return this.errorMessage;
    }
}
