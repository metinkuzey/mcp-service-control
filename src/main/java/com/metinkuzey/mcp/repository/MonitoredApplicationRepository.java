package com.metinkuzey.mcp.repository;

import com.metinkuzey.mcp.model.MonitoredApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MonitoredApplicationRepository extends JpaRepository<MonitoredApplication, Long> {
}
