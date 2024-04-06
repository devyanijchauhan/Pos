package org.pgs.postp.service;

import org.pgs.postp.dto.NotificationDTO;

import java.util.List;

public interface NotificationService {

    List<NotificationDTO> getAllNotifications();

    NotificationDTO getNotificationById(Long id);

    NotificationDTO createNotification(NotificationDTO notificationDTO);

    NotificationDTO updateNotification(Long id, NotificationDTO notificationDTO);

    void deleteNotification(Long id);

}
