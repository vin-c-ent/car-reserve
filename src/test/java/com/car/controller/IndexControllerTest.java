package com.car.controller;

import com.car.control.IndexController;
import com.car.entity.BrandEntity;
import com.car.repository.BrandRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.HashMap;

import static org.mockito.Mockito.when;

public class IndexControllerTest {

    @InjectMocks
    private IndexController indexController;

    @Mock
    private BrandRepository brandRepository;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetIndex() {
        Long brandId = 1L;
        String brandName = "BMW";

        BrandEntity brandEntity = new BrandEntity();
        brandEntity.setId(brandId);
        brandEntity.setBrandName(brandName);
        when(brandRepository.findAll()).thenReturn(Collections.singletonList(brandEntity));

        String pageStr = indexController.getIndex(null, new HashMap<>());
        Assert.assertEquals("index", pageStr);
    }
}
