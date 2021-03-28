package com.car.controller;

import com.car.control.CarInfoController;
import com.car.dto.response.BaseResponse;
import com.car.dto.response.ModelResponse;
import com.car.entity.ModelEntity;
import com.car.exception.CustomizedException;
import com.car.repository.ModelRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.when;

public class CarInfoControllerTest {

    @InjectMocks
    private CarInfoController carInfoController;

    @Mock
    private ModelRepository modelRepository;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetModelListByBrand() {
        Long brandId = 1L;
        Long modelId = 1L;
        String modelName = "730";

        ModelEntity modelEntity = new ModelEntity();
        modelEntity.setId(modelId);
        modelEntity.setBrandId(brandId);
        modelEntity.setModelName(modelName);
        when(modelRepository.findAllByBrandId(anyLong())).thenReturn(Collections.singletonList(modelEntity));

        ResponseEntity<BaseResponse<List<ModelResponse>>> responseEntity = carInfoController.getModelListByBrand(String.valueOf(brandId));

        ModelResponse modelResponse = responseEntity.getBody().getData().get(0);
        Assert.assertEquals(modelId, modelResponse.getModelId());
        Assert.assertEquals(modelName, modelResponse.getModelName());
    }

    @Test(expected = CustomizedException.class)
    public void testModelEmpty() {
        Long brandId = 1L;

        when(modelRepository.findAllByBrandId(anyLong())).thenReturn(new ArrayList<>());

        ResponseEntity<BaseResponse<List<ModelResponse>>> responseEntity = carInfoController.getModelListByBrand(String.valueOf(brandId));
    }

    @Test(expected = CustomizedException.class)
    public void testModelNull() {
        Long brandId = 1L;

        when(modelRepository.findAllByBrandId(anyLong())).thenReturn(null);

        ResponseEntity<BaseResponse<List<ModelResponse>>> responseEntity = carInfoController.getModelListByBrand(String.valueOf(brandId));
    }
}
