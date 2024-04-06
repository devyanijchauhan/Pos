package org.pgs.postp.dto;

public class NotificationDTO {

    private Long notificationID;
    private String message;


    public NotificationDTO() {
    }

    public NotificationDTO(Long notificationID, String message) {
        this.notificationID = notificationID;
        this.message = message;
    }


    public Long getNotificationID() {
        return notificationID;
    }

    public void setNotificationID(Long notificationID) {
        this.notificationID = notificationID;
    }
    public String getMessage() {return message;}

    public void setMessage(String message) {this.message = message;}

}
