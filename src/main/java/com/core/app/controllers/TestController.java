package com.core.app.controllers;

import com.core.app.entities.dto.Response;
import com.core.app.utils.Helper;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/v1/test")
public class TestController {

    @ApiOperation("Test endpoint")
    @GetMapping
    public ResponseEntity<Response> test() {
         return Helper.buildHttpResponse(HttpStatus.OK, false, "Hello, it's pet-care service", null ) ;
     }
}
