package com.yw.webflux.example.advice;

import com.yw.webflux.example.exception.StudentException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.support.WebExchangeBindException;

/**
 * @author yangwei
 */
@ControllerAdvice       // 处理器通知切面（连接点为处理器方法）
public class ParamValidAdvice {
    @ExceptionHandler
    public ResponseEntity<String> validateHandle(StudentException ex) {
        // 获取异常对象中的数据
        String message = ex.getMessage();
        String fn = ex.getField();
        String fv = ex.getValue();
        String msg = fn + " : " + fv + " : " + message;
        return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<String> xxx(WebExchangeBindException ex) {
        return new ResponseEntity<>(getExceptionMsg(ex), HttpStatus.BAD_REQUEST);
    }

    private String getExceptionMsg(WebExchangeBindException ex) {
        return ex.getFieldErrors()
                .stream()
                .map(e -> e.getField() + " : " + e.getDefaultMessage())
                .reduce("", (s1, s2) -> s1 + "\n" + s2);
    }
}