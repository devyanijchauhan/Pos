package org.pgs.postp.service.Impl;

import org.pgs.postp.dto.NotificationDTO;
import org.pgs.postp.mapper.NotificationMapper;
import org.pgs.postp.model.NotificationModel;
import org.pgs.postp.model.RoleModel;
import org.pgs.postp.model.UserModel;
import org.pgs.postp.repository.NotificationRepository;
import org.pgs.postp.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;

    private final NotificationMapper notificationMapper;

    @Autowired
    public NotificationServiceImpl(NotificationRepository notificationRepository, NotificationMapper notificationMapper) {
        this.notificationRepository = notificationRepository;
        this.notificationMapper = notificationMapper;
    }

    @Override
    public List<NotificationDTO> getAllNotifications() {
        List<NotificationModel> notificationModels = notificationRepository.findAll();
        return notificationModels.stream()
                .map(notificationMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public NotificationDTO getNotificationById(Long id) {
        NotificationModel notificationModel = notificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notification not found with id: " + id));
        return notificationMapper.toDTO(notificationModel);

    }

    @Override
    public NotificationDTO createNotification(NotificationDTO notificationDTO) {
        NotificationModel notificationModel = notificationMapper.toModel(notificationDTO);
        NotificationModel saveNotification = notificationRepository.save(notificationModel);
        return notificationMapper.toDTO(saveNotification);
    }

    @Override
    public NotificationDTO updateNotification(Long id, NotificationDTO notificationDTO) {
        NotificationModel existingNotification = notificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notification not found with id: " + id));
        if (notificationDTO.getMessage() != null) {
            existingNotification.setMessage(notificationDTO.getMessage());
        }
        NotificationModel updatedNotification = notificationRepository.save(existingNotification);
        return notificationMapper.toDTO(updatedNotification);
    }

    @Override
    public void deleteNotification(Long id) {
        if (!notificationRepository.existsById(id)) {
            throw new RuntimeException("Notification not found with id: " + id);
        }
        notificationRepository.deleteById(id);
    }
}
