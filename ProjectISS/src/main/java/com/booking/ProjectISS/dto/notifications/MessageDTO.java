package com.booking.ProjectISS.dto.notifications;

public class MessageDTO {
    String message;

    public MessageDTO(){}
    public MessageDTO(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
