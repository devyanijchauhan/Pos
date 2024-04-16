package org.pgs.postp.dto;

public class ResetPasswordRequest {
    private String newPassword;

    // Default constructor for serialization/deserialization
    public ResetPasswordRequest() {}

    // Constructor with all fields
    public ResetPasswordRequest(String newPassword) {
        this.newPassword = newPassword;
    }

    // Getter for newPassword
    public String getNewPassword() {
        return newPassword;
    }

    // Setter for newPassword
    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}