package com.metinkuzey.mcp.controller;

import com.metinkuzey.mcp.model.MonitoredApplication;
import com.metinkuzey.mcp.service.MonitoredApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

import java.util.List;
@Tag(name = "Monitored Applications", description = "Endpoints for managing monitored applications")
@RestController
@RequestMapping("/api/monitored-applications")
public class MonitoredApplicationController {

    private final MonitoredApplicationService monitoredApplicationService;

    @Autowired
    public MonitoredApplicationController(MonitoredApplicationService monitoredApplicationService) {
        this.monitoredApplicationService = monitoredApplicationService;
    }

    @Operation(summary = "Create a new monitored application", description = "Creates and returns a new monitored application.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully created monitored application"),
        @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping
    public ResponseEntity<MonitoredApplication> createMonitoredApplication(@RequestBody (description = "Monitored application to create") MonitoredApplication monitoredApplication) {
        MonitoredApplication created = monitoredApplicationService.createMonitoredApplication(monitoredApplication);
        return ResponseEntity.ok(created);
    }

    @Operation(summary = "Get all monitored applications", description = "Retrieves a list of all monitored applications.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved list")
    @GetMapping
    public ResponseEntity<List<MonitoredApplication>> getAllMonitoredApplications() {
        List<MonitoredApplication> applications = monitoredApplicationService.getAllMonitoredApplications();
        return ResponseEntity.ok(applications);
    }

    @Operation(summary = "Get monitored application by ID", description = "Retrieves a monitored application by its ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved application"),
        @ApiResponse(responseCode = "404", description = "Application not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<MonitoredApplication> getMonitoredApplicationById(@PathVariable Long id) {
        MonitoredApplication application = monitoredApplicationService
                .getMonitoredApplicationById(id)
                .orElseThrow(() -> new RuntimeException("Application not found with id: " + id));
        return ResponseEntity.ok(application);
    }

    @Operation(summary = "Update a monitored application by ID", description = "Updates the monitored application details for the given ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully updated application"),
        @ApiResponse(responseCode = "404", description = "Application not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<MonitoredApplication> updateMonitoredApplication(
            @PathVariable Long id,
            @RequestBody (description = "Updated monitored application details") MonitoredApplication monitoredApplication
    ) {
        MonitoredApplication updated = monitoredApplicationService.updateMonitoredApplication(id, monitoredApplication);
        return ResponseEntity.ok(updated);
    }

    @Operation(summary = "Delete a monitored application by ID", description = "Deletes the monitored application with the specified ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Successfully deleted application"),
        @ApiResponse(responseCode = "404", description = "Application not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMonitoredApplication(@PathVariable Long id) {
        monitoredApplicationService.deleteMonitoredApplication(id);
        return ResponseEntity.noContent().build();
    }
}
