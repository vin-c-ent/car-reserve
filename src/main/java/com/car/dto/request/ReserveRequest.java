package com.car.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author Vincent
 * @date 2021/03/27
 */
@Data
public class ReserveRequest {
    @NotNull
    @JsonProperty("model_id")
    @ApiModelProperty(example = "1")
    private Long modelId;
    @NotNull
    @JsonProperty("start_date")
    @ApiModelProperty(example = "2021-03-27")
    private String startDate;
    @NotNull
    @JsonProperty("end_date")
    @ApiModelProperty(example = "2021-03-28")
    private String endDate;
}
