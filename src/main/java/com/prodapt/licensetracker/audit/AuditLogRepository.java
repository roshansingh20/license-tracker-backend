package com.prodapt.licensetracker.audit;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditLogRepository extends JpaRepository<com.prodapt.licensetracker.audit.AuditLog, Long> {
}
