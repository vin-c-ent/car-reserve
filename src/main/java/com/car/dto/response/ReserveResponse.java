package com.car.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

/**
 * @author Vincent
 * @date 2021/03/27
 */
@Data
@Builder
public class ReserveResponse {
    private UUID reserveId;
}
