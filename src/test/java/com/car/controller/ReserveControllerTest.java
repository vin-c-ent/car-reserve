package com.car.controller;

import com.car.control.ReserveController;
import com.car.dto.request.ReserveRequest;
import com.car.dto.response.BaseResponse;
import com.car.dto.response.ReserveResponse;
import com.car.exception.CustomizedException;
import com.car.service.ReserveService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.UUID;

import static org.mockito.Mockito.*;

public class ReserveControllerTest {

    @InjectMocks
    private ReserveController reserveController;

    @Mock
    private ReserveService reserveService;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testReserveSuccess() {
        String startDate = "2099-01-01";
        String endDate = "2099-01-02";
        UUID reserveEntityId = UUID.randomUUID();

        BaseResponse<ReserveResponse> baseResponse = BaseResponse.success(ReserveResponse.builder().reserveId(reserveEntityId).build());
        when(reserveService.reserve(anyLong(), any(LocalDate.class), any(LocalDate.class))).thenReturn(baseResponse);

        ReserveRequest reserveRequest = new ReserveRequest();
        reserveRequest.setModelId(1L);
        reserveRequest.setStartDate(startDate);
        reserveRequest.setEndDate(endDate);
        ResponseEntity<BaseResponse<ReserveResponse>> responseEntity = reserveController.reserve(reserveRequest);

        Assert.assertEquals(reserveEntityId, responseEntity.getBody().getData().getReserveId());
    }

    @Test(expected = CustomizedException.class)
    public void testStartDateBeforeToday() {
        String startDate = "2020-01-01";
        String endDate = "2099-01-02";
        UUID reserveEntityId = UUID.randomUUID();

        BaseResponse<ReserveResponse> baseResponse = BaseResponse.success(ReserveResponse.builder().reserveId(reserveEntityId).build());
        when(reserveService.reserve(anyLong(), any(LocalDate.class), any(LocalDate.class))).thenReturn(baseResponse);

        ReserveRequest reserveRequest = new ReserveRequest();
        reserveRequest.setModelId(1L);
        reserveRequest.setStartDate(startDate);
        reserveRequest.setEndDate(endDate);
        ResponseEntity<BaseResponse<ReserveResponse>> responseEntity = reserveController.reserve(reserveRequest);
    }

    @Test(expected = CustomizedException.class)
    public void testStartDateAfterEndDate() {
        String startDate = "2020-01-01";
        String endDate = "2009-01-02";
        UUID reserveEntityId = UUID.randomUUID();

        BaseResponse<ReserveResponse> baseResponse = BaseResponse.success(ReserveResponse.builder().reserveId(reserveEntityId).build());
        when(reserveService.reserve(anyLong(), any(LocalDate.class), any(LocalDate.class))).thenReturn(baseResponse);

        ReserveRequest reserveRequest = new ReserveRequest();
        reserveRequest.setModelId(1L);
        reserveRequest.setStartDate(startDate);
        reserveRequest.setEndDate(endDate);
        ResponseEntity<BaseResponse<ReserveResponse>> responseEntity = reserveController.reserve(reserveRequest);
    }
}
