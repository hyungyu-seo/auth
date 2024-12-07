package com.example.demo.controller.exception;

import com.example.demo.application.exception.MemberException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;


@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(HttpServletRequest request, Exception ex) {
        String uri = request.getRequestURI();
        // Swagger 관련 경로를 제외
        if (uri.startsWith("/api-docs") || uri.startsWith("/swagger-ui/") || uri.startsWith("/webjars/") || uri.startsWith("/member-controller")) {
            return null; // 예외 처리를 하지 않음
        }
        // 일반 예외 처리 로직
        return new ResponseEntity<>("서버 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
    }

//    @ExceptionHandler(Exception.class)
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    public ErrorResponse handleException(Exception ex) {
//        return new ErrorResponse("Internal Server Error", 404);
//    }



    @ExceptionHandler(MemberException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleMemberNotFoundException(MemberException ex) {
        return new ErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND.value());
    }


    public record ErrorResponse(String message, int statusCode) {

    }
}



