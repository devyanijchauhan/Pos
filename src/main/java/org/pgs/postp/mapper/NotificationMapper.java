package org.pgs.postp.mapper;

import org.pgs.postp.dto.NotificationDTO;
import org.pgs.postp.model.NotificationModel;
import org.springframework.stereotype.Component;

@Component
public class NotificationMapper {

    public NotificationDTO toDTO(NotificationModel model) {
        if (model == null) {
            return null;
        }
        NotificationDTO dto = new NotificationDTO();
        dto.setNotificationID(model.getNotificationID());
        dto.setMessage(model.getMessage());
        return dto;
    }

    public NotificationModel toModel(NotificationDTO dto) {
        if (dto == null) {
            return null;
        }
        NotificationModel model = new NotificationModel();
        model.setNotificationID(dto.getNotificationID());
        model.setMessage(dto.getMessage());
        return model;
    }

}
