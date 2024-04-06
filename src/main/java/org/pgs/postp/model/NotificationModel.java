package org.pgs.postp.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Notifications")
public class NotificationModel {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NotifcationID")
    private Long notificationID;

    @Column(name = "Message", nullable = false)
    private String message;


    public NotificationModel() {
    }

    public NotificationModel(String message) {
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
