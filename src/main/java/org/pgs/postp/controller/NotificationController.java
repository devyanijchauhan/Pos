package org.pgs.postp.controller;

import org.pgs.postp.dto.NotificationDTO;
import org.pgs.postp.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@PreAuthorize("hasAuthority('ADMIN') || hasAuthority('MANAGER') || hasAuthority('CASHIER')")
@RequestMapping("/notifications")
@CrossOrigin( origins = "http://Localhost:4200")
public class NotificationController {

    private final NotificationService notificationService;

    @Autowired
    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping
    public ResponseEntity<List<NotificationDTO>> getAllNotifications() {
        List<NotificationDTO> notificationDTOS = notificationService.getAllNotifications();
        return new ResponseEntity<>(notificationDTOS, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NotificationDTO> getNotificationById(@PathVariable("id") Long id) {
        NotificationDTO notificationDTO = notificationService.getNotificationById(id);
        return new ResponseEntity<>(notificationDTO, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<NotificationDTO> createNotification(@RequestBody NotificationDTO notificationDTO) {
        NotificationDTO createdNotification = notificationService.createNotification(notificationDTO);
        return new ResponseEntity<>(createdNotification, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<NotificationDTO> updateNotification(@PathVariable("id") Long id, @RequestBody NotificationDTO notificationDTO) {
        notificationDTO.setNotificationID(id);
        NotificationDTO updatedNotification = notificationService.updateNotification(id, notificationDTO);
        return new ResponseEntity<>(updatedNotification, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNotification(@PathVariable("id") Long id) {
        notificationService.deleteNotification(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
