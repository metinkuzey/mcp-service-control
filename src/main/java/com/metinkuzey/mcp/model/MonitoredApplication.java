package com.metinkuzey.mcp.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "monitored_applications")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MonitoredApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String url;

    @Column(name = "description")
    private String description;

    @Column(name = "status")
    private String status;

    @Column(name = "created_at", nullable = false, updatable = false)
    private java.time.Instant createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = java.time.Instant.now();
    }
}
