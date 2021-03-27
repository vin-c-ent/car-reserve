package com.car.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.car.constants.CommonConstants.FAIL;
import static com.car.constants.CommonConstants.SUCCESS;

/**
 * @author Vincent
 * @date 2021/03/27
 */
@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@NoArgsConstructor
@AllArgsConstructor
public class BaseResponse<T> {
    T data;
    String status;
    ErrorResponse error;

    public static <T> BaseResponse<T> success(T data) {
        return BaseResponse.<T>builder()
                .status(SUCCESS)
                .data(data)
                .build();
    }

    public static <T> BaseResponse<T> fail(ErrorResponse error) {
        return BaseResponse.<T>builder()
                .status(FAIL)
                .error(error)
                .build();
    }
}
