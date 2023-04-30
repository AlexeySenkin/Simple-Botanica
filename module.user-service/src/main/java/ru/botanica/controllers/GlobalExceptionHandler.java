package ru.botanica.controllers;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.PropertyValueException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.botanica.dto.AppStatus;
import ru.botanica.exceptions.ServerHandleException;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {


    /**
     * Отлавливает ошибки, связанные с работой сервера (обработка данных,
     * передача данных, запись данных, удаление данных)
     *
     * @param exception Ошибка
     * @return Сообщение об ошибке
     */
    @ExceptionHandler
    public ResponseEntity<?> catchServerHandleException(ServerHandleException exception) {
        log.error(exception.getMessage(), exception);
        return new ResponseEntity<>(new AppStatus(HttpStatus.UNPROCESSABLE_ENTITY.value(),
                exception.getMessage()), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    /**
     * Отлавливает ошибки, связанные с неподдерживаемыми форматами файлов
     *
     * @param exception Ошибка
     * @return Сообщение об ошибке
     */
    @ExceptionHandler
    public ResponseEntity<?> catchHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException exception) {
        log.error(exception.getMessage(), exception);
        return new ResponseEntity<>(new AppStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value(),
                "Неподдерживаемый формат файлов"), HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    /**
     * Отлавливает ошибки, связанные с невозможными значениями для полей
     *
     * @param exception Ошибка
     * @return Сообщение об ошибке
     */
    @ExceptionHandler
    public ResponseEntity<?> catchPropertyValueException(PropertyValueException exception) {
        log.error(exception.getMessage(), exception);
        return new ResponseEntity<>(new AppStatus(HttpStatus.BAD_REQUEST.value(),
                "Указанные значения или их часть не могут быть null"), HttpStatus.BAD_REQUEST);
    }

    /**
     * Отлавливает ошибки, связанные с невозможностью сохранить значение в БД
     *
     * @param exception Ошибка
     * @return Сообщение об ошибке
     */
    @ExceptionHandler
    public ResponseEntity<?> catchDataIntegrityViolationException(DataIntegrityViolationException exception) {
        log.error(exception.getMessage(), exception);
        return new ResponseEntity<>(new AppStatus(HttpStatus.BAD_REQUEST.value(),
                "Указанные значения или их часть не могут быть null"), HttpStatus.BAD_REQUEST);
    }

    /**
     * Отлавливает ошибки, связанные с null-значениями для обязательных полей, классов или параметров
     *
     * @param exception Ошибка
     * @return Сообщение об ошибке
     */
    @ExceptionHandler
    public ResponseEntity<?> catchNullPointerException(NullPointerException exception) {
        log.error(exception.getMessage(), exception);
        return new ResponseEntity<>(new AppStatus(HttpStatus.BAD_REQUEST.value(),
                "Среди обязательных параметров для эндпоинта был прислан null"), HttpStatus.BAD_REQUEST);
    }
}
