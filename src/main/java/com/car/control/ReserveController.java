package com.car.control;

import com.car.dto.request.ReserveRequest;
import com.car.exception.CustomizedException;
import com.car.service.ReserveService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.car.constants.CommonConstants.YYYYMMDD;
import static com.car.exception.ReserveExceptionInfoEnums.START_DATE_AFTER_END_DATE;
import static com.car.exception.ReserveExceptionInfoEnums.START_DATE_BEFORE_NOW;

/**
 * @author Vincent
 * @date 2021/03/27
 */
@Slf4j
@RestController
@RequestMapping("/")
public class ReserveController {

    @Autowired
    private ReserveService reserveService;

    @PostMapping(value = "/reserve")
    public ResponseEntity reserve(@Valid @RequestBody ReserveRequest reserveRequest) {
        Long modelId = reserveRequest.getModelId();
        LocalDate startDate = LocalDate.parse(reserveRequest.getStartDate(), DateTimeFormatter.ofPattern(YYYYMMDD));
        LocalDate endDate = LocalDate.parse(reserveRequest.getEndDate(), DateTimeFormatter.ofPattern(YYYYMMDD));

        this.validateDate(startDate, endDate);

        return ResponseEntity.ok(reserveService.reserve(modelId, startDate, endDate));
    }

    private void validateDate(LocalDate startDate, LocalDate endDate) {
        // start date need to before end date
        if (startDate.isEqual(endDate) || startDate.isAfter(endDate)) {
            throw new CustomizedException(START_DATE_AFTER_END_DATE);
        }
        // book date can`t exceed current date
        if (startDate.isBefore(LocalDate.now())) {
            throw new CustomizedException(START_DATE_BEFORE_NOW);
        }
    }
}
