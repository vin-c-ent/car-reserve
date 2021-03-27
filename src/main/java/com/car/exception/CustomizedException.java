package com.car.exception;

import com.car.dto.response.BaseResponse;
import com.car.dto.response.ErrorResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * @author Vincent
 * @date 2021/03/27
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CustomizedException extends RuntimeException {
    private IExceptionInfo iExceptionInfo;
    private String detailErrorMessage;

    public CustomizedException(IExceptionInfo iExceptionInfo) {
        this.iExceptionInfo = iExceptionInfo;
        this.detailErrorMessage = iExceptionInfo.getErrorMessage();
    }

    public CustomizedException(IExceptionInfo iExceptionInfo, String detailErrorMessage) {
        this.iExceptionInfo = iExceptionInfo;
        this.detailErrorMessage = detailErrorMessage;
    }

    public ResponseEntity getResponseEntity() {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrorCode(this.iExceptionInfo.getErrorCode());
        errorResponse.setErrorMessage(this.detailErrorMessage);
        return ResponseEntity.badRequest().body(BaseResponse.fail(errorResponse));
    }

    public ResponseEntity getResponseEntity(HttpStatus httpStatus) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrorCode(this.iExceptionInfo.getErrorCode());
        errorResponse.setErrorMessage(this.detailErrorMessage);
        return ResponseEntity.status(httpStatus).body(BaseResponse.fail(errorResponse));
    }
}
