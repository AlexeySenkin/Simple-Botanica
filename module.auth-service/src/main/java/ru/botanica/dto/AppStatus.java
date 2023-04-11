package ru.botanica.dto;

public class AppStatus {
    private int statusCode;
    private String message;

    public int getStatusCode() {
        return statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public AppStatus() {
    }

    public AppStatus(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }
}
