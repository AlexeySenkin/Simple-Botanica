package ru.botanica.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
// Скопировано из работы Алексея. На всякий случай переименовал, чтобы не создать проблем при компиляции
public class AppResponse {
    private int statusCode;
    private String message;

    public AppResponse() {
    }

    @Override
    public String toString() {
        return "AppResponse{" +
                "statusCode=" + statusCode +
                ", message='" + message + '\'' +
                '}';
    }
}
