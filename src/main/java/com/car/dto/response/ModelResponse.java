package com.car.dto.response;

import lombok.Builder;
import lombok.Data;

/**
 * @author Vincent
 * @date 2021/03/27
 */
@Data
@Builder
public class ModelResponse {
    private Long modelId;
    private String modelName;
}
