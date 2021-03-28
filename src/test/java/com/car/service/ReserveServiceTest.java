package com.car.service;

import com.car.dto.response.BaseResponse;
import com.car.dto.response.ReserveResponse;
import com.car.entity.CarEntity;
import com.car.entity.ReserveEntity;
import com.car.exception.CustomizedException;
import com.car.repository.CarRepository;
import com.car.repository.ReserveRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.UUID;

import static org.mockito.Mockito.*;

public class ReserveServiceTest {

    @InjectMocks
    private ReserveService reserveService;

    @Mock
    private CarRepository carRepository;
    @Mock
    private ReserveRepository reserveRepository;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(reserveService, "carLockPeriod", 180000L);
    }

    @Test
    public void testReserveSuccess() {
        UUID carId = UUID.randomUUID();
        UUID existingReserveId = UUID.randomUUID();
        UUID newReserveId = UUID.randomUUID();

        CarEntity carEntity = new CarEntity();
        carEntity.setId(carId);
        carEntity.setModelId(1L);
        when(carRepository.findAll(any(Specification.class))).thenReturn(Collections.singletonList(carEntity));

        ReserveEntity existingReserveEntity = new ReserveEntity();
        existingReserveEntity.setId(existingReserveId);
        existingReserveEntity.setCarId(carId);
        existingReserveEntity.setStartDate(new Date(2021, Calendar.JANUARY, 1));
        existingReserveEntity.setEndDate(new Date(2021, Calendar.JANUARY, 2));
        when(reserveRepository.findAllByCarIdIn(anySet())).thenReturn(Collections.singletonList(existingReserveEntity));

        when(carRepository.lock(any(UUID.class), any(Date.class), any(UUID.class), any())).thenReturn(1);

        ReserveEntity newReserveEntity = new ReserveEntity();
        newReserveEntity.setId(newReserveId);
        newReserveEntity.setCarId(carId);
        newReserveEntity.setStartDate(new Date(2021, Calendar.MAY, 1));
        newReserveEntity.setEndDate(new Date(2021, Calendar.MAY, 2));
        when(reserveRepository.save(any(ReserveEntity.class))).thenReturn(newReserveEntity);

        BaseResponse<ReserveResponse> reserveResponseBaseResponse = reserveService.reserve(1L, LocalDate.of(2099, 5, 1), LocalDate.of(2099, 5, 2));

        Assert.assertEquals(newReserveId, reserveResponseBaseResponse.getData().getReserveId());
    }

    @Test(expected = CustomizedException.class)
    public void testFailedToAcquireLock() {
        UUID carId = UUID.randomUUID();
        UUID existingReserveId = UUID.randomUUID();
        UUID newReserveId = UUID.randomUUID();

        CarEntity carEntity = new CarEntity();
        carEntity.setId(carId);
        carEntity.setModelId(1L);
        when(carRepository.findAll(any(Specification.class))).thenReturn(Collections.singletonList(carEntity));

        ReserveEntity existingReserveEntity = new ReserveEntity();
        existingReserveEntity.setId(existingReserveId);
        existingReserveEntity.setCarId(carId);
        existingReserveEntity.setStartDate(new Date(2021, Calendar.JANUARY, 1));
        existingReserveEntity.setEndDate(new Date(2021, Calendar.JANUARY, 2));
        when(reserveRepository.findAllByCarIdIn(anySet())).thenReturn(Collections.singletonList(existingReserveEntity));

        when(carRepository.lock(any(UUID.class), any(Date.class), any(UUID.class), any())).thenReturn(-1);

        ReserveEntity newReserveEntity = new ReserveEntity();
        newReserveEntity.setId(newReserveId);
        newReserveEntity.setCarId(carId);
        newReserveEntity.setStartDate(new Date(2021, Calendar.MAY, 1));
        newReserveEntity.setEndDate(new Date(2021, Calendar.MAY, 2));
        when(reserveRepository.save(any(ReserveEntity.class))).thenReturn(newReserveEntity);

        BaseResponse<ReserveResponse> reserveResponseBaseResponse = reserveService.reserve(1L, LocalDate.of(2099, 5, 1), LocalDate.of(2099, 5, 2));
    }
}
