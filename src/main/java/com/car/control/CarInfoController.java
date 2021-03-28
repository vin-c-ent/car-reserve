package com.car.control;

import com.car.dto.response.BaseResponse;
import com.car.dto.response.ModelResponse;
import com.car.entity.ModelEntity;
import com.car.exception.CustomizedException;
import com.car.repository.ModelRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static com.car.exception.ModelExceptionInfoEnums.MODEL_EMPTY;

/**
 * @author Vincent
 * @date 2021/03/27
 */
@Slf4j
@RestController
@RequestMapping("/")
public class CarInfoController {

    @Autowired
    private ModelRepository modelRepository;

    @GetMapping("/brand/{brand_id}/models")
    public ResponseEntity<BaseResponse<List<ModelResponse>>> getModelListByBrand(@PathVariable("brand_id") String brandId) {
        List<ModelEntity> modelList = modelRepository.findAllByBrandId(Long.valueOf(brandId));
        log.info("for brand {}, the model list is {}", brandId, modelList);

        if (modelList == null || modelList.size() == 0) {
            throw new CustomizedException(MODEL_EMPTY);
        }

        return ResponseEntity.ok(
                BaseResponse.success(modelList.stream()
                        .map(modelEntity -> ModelResponse.builder()
                                .modelId(modelEntity.getId())
                                .modelName(modelEntity.getModelName())
                                .build())
                        .collect(Collectors.toList())
                )
        );
    }
}
