package com.car.config;

import com.car.exception.CustomizedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Vincent
 * @date 2021/03/27
 */
@Slf4j
@RestControllerAdvice
public class ControllerAdviceForException {

    @ExceptionHandler(CustomizedException.class)
    public ResponseEntity handleCustomizedException(HttpServletRequest request, CustomizedException e) {
        log.error("Handling Customized Exception: ", e);
        return e.getResponseEntity();
    }
}
