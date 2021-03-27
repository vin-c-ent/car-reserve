package com.car.exception;

/**
 * @author Vincent
 * @date 2021/03/27
 */
public enum ReserveExceptionInfoEnums implements IExceptionInfo {
    START_DATE_AFTER_END_DATE("301", "start date should be before end date"),
    START_DATE_BEFORE_NOW("302", "start date should be after now"),
    NO_CAR_IS_AVAILABLE("303", "no car is available in this period");

    private String errorCode;
    private String errorMessage;

    private ReserveExceptionInfoEnums(String errorCode, String errorMessage) {
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
