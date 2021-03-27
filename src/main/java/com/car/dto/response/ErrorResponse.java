package com.car.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Vincent
 * @date 2021/03/27
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
    String errorCode;
    String errorMessage;
}
