package com.metinkuzey.mcp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Application", description = "Basic application health check endpoint")
@RestController
@RequestMapping("/api")
public class ApplicationController {

    @Operation(summary = "Get application status", description = "Returns a string indicating the application is running")
    @GetMapping("/status")
    public String status() {
        return "Application is running.";
    }
}
