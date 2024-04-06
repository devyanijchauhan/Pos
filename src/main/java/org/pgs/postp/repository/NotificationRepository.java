package org.pgs.postp.repository;

import org.pgs.postp.model.NotificationModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<NotificationModel, Long> {
    // Define additional methods if needed
}
