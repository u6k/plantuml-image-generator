
package me.u6k.plantuml_image_generator;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestControllerExceptionHandler {

    @Value("${info.app.version}")
    private String appVersion;

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleIllegalArgumentException(IllegalArgumentException e) {
        return this.buildResponseEntity(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleException(Exception e) {
        return this.buildResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<Map<String, String>> buildResponseEntity(Exception e, HttpStatus status) {
        Map<String, String> body = new HashMap<>();
        body.put("code", e.getClass().getName());
        body.put("message", e.getMessage());

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        headers.add("X-Api-Version", this.appVersion);

        ResponseEntity<Map<String, String>> resp = new ResponseEntity<>(body, headers, status);

        return resp;
    }

}
