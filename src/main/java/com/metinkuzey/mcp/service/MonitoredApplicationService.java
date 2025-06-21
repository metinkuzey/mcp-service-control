package com.metinkuzey.mcp.service;

import com.metinkuzey.mcp.model.MonitoredApplication;
import com.metinkuzey.mcp.repository.MonitoredApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MonitoredApplicationService {

    private final MonitoredApplicationRepository monitoredApplicationRepository;

    @Autowired
    public MonitoredApplicationService(MonitoredApplicationRepository monitoredApplicationRepository) {
        this.monitoredApplicationRepository = monitoredApplicationRepository;
    }

    public List<MonitoredApplication> getAllMonitoredApplications() {
        return monitoredApplicationRepository.findAll();
    }

    public Optional<MonitoredApplication> getMonitoredApplicationById(Long id) {
        return monitoredApplicationRepository.findById(id);
    }

    public MonitoredApplication createMonitoredApplication(MonitoredApplication application) {
        return monitoredApplicationRepository.save(application);
    }

    public MonitoredApplication updateMonitoredApplication(Long id, MonitoredApplication updatedApplication) {
        return monitoredApplicationRepository.findById(id)
                .map(existingApp -> {
                    existingApp.setName(updatedApplication.getName());
                    existingApp.setDescription(updatedApplication.getDescription());
                    existingApp.setUrl(updatedApplication.getUrl());
                    existingApp.setStatus(updatedApplication.getStatus());
                    return monitoredApplicationRepository.save(existingApp);
                })
                .orElseGet(() -> {
                    updatedApplication.setId(id);
                    return monitoredApplicationRepository.save(updatedApplication);
                });
    }

    public void deleteMonitoredApplication(Long id) {
        monitoredApplicationRepository.deleteById(id);
    }
}
